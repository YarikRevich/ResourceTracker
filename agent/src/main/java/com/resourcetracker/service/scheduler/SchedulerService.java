package com.resourcetracker.service.scheduler;

import com.resourcetracker.converter.CronExpressionConverter;
import com.resourcetracker.dto.CommandExecutorOutputDto;
import com.resourcetracker.entity.KafkaLogsTopicEntity;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
                  () ->
                      executorService.execute(
                          () -> {
                            try {
                              exec(request.getScript());
                            } catch (CommandExecutorException e) {

                              throw new RuntimeException(e);
                            }
                          }),
                  0,
                  period,
                  TimeUnit.MILLISECONDS);
            });

    WaiterHelper.waitForExit();
  }

  /**
   * Executes given script and sends result as message to Kafka cluster.
   *
   * @param input script to be executed.
   */
  private void exec(String input) throws CommandExecutorException {
    ExecCommandService execCommandService = new ExecCommandService(input);

    CommandExecutorOutputDto scriptExecCommandOutput =
        commandExecutorService.executeCommand(execCommandService);

    kafkaService.send(
        KafkaLogsTopicEntity.of(
            UUID.randomUUID(),
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
