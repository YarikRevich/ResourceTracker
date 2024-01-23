package com.resourcetracker.service.scheduler;

import com.resourcetracker.converter.CronExpressionConverter;
import com.resourcetracker.dto.CommandExecutorOutputDto;
import com.resourcetracker.dto.KafkaLogsTopicDto;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.exception.CronExpressionException;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.kafka.KafkaService;
import com.resourcetracker.service.machine.MachineService;
import com.resourcetracker.service.scheduler.command.ExecCommandService;
import com.resourcetracker.service.scheduler.executor.CommandExecutorService;
import com.resourcetracker.service.waiter.WaiterHelper;
import jakarta.annotation.PreDestroy;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Exposes opportunity to schedule incoming requests. */
@Service
public class SchedulerService {
  @Autowired private ConfigService configService;

  @Autowired private KafkaService kafkaService;

  @Autowired private MachineService machineService;

  @Autowired private CommandExecutorService commandExecutorService;

  private final ScheduledExecutorService scheduledExecutorService =
      Executors.newSingleThreadScheduledExecutor();

  private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

  /**
   * Starts executor listening process, which receives incoming requests and executes them with the
   * help of local command executor service.
   */
  public void start() {
    configService.getConfig().getRequests().parallelStream()
        .forEach(
            request -> {
              long period;
              try {
                period = CronExpressionConverter.convert(request.getFrequency());
              } catch (CronExpressionException e) {
                throw new RuntimeException(e);
              }

              scheduledExecutorService.scheduleAtFixedRate(
                      register(request.getName(), request.getScript()),
                  0,
                  period,
                  TimeUnit.MILLISECONDS);
            });

    WaiterHelper.waitForExit();
  }

    /**
     * Registers the execution of the given request.
     *
     * @param name name of the request to be processed.
     * @param input script to be executed.
     * @return execution callback.
     */
  private Runnable register(String name, String input) {
      return () -> {
          CountDownLatch latch = new CountDownLatch(1);

          executorService.execute(
                  () -> {
                      try {
                          exec(name, input);
                      } catch (CommandExecutorException e) {

                          throw new RuntimeException(e);
                      }

                      latch.countDown();
                  });

          try {
              latch.await();
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
      };
  }

  /**
   * Executes given script and sends result as message to Kafka cluster.
   *
   * @param name name of the request to be processed.
   * @param input script to be executed.
   */
  private void exec(String name, String input) throws CommandExecutorException {
    ExecCommandService execCommandService = new ExecCommandService(input);

    CommandExecutorOutputDto scriptExecCommandOutput =
        commandExecutorService.executeCommand(execCommandService);

    kafkaService.send(
        KafkaLogsTopicDto.of(
            UUID.randomUUID(),
            name,
            scriptExecCommandOutput.getNormalOutput(),
            scriptExecCommandOutput.getErrorOutput(),
            machineService.getHostName(),
            machineService.getHostAddress(),
            Timestamp.from(Instant.now())));
  }

  @PreDestroy
  private void close() {
    scheduledExecutorService.shutdown();

    try {
      if (!scheduledExecutorService.awaitTermination(1000, TimeUnit.HOURS)) {
        scheduledExecutorService.shutdownNow();
      }
    } catch (InterruptedException e) {
      scheduledExecutorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }
}
