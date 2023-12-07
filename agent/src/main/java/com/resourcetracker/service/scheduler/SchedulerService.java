package com.resourcetracker.service.scheduler;

import com.resourcetracker.converter.CronExpressionConverter;
import com.resourcetracker.dto.CommandExecutorOutputDto;
import com.resourcetracker.entity.KafkaLogsTopicEntity;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.exception.CronExpressionException;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.scheduler.executor.CommandExecutorService;
import com.resourcetracker.service.kafka.KafkaService;
import com.resourcetracker.service.machine.MachineService;
import com.resourcetracker.service.scheduler.command.ExecCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Exposes opportunity to schedule incoming requests.
 */
@Service
public class SchedulerService {
    private static final Logger logger = LogManager.getLogger(SchedulerService.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private MachineService machineService;

    @Autowired
    private CommandExecutorService commandExecutorService;

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * Starts executor listening process, which receives incoming requests
     * and executes them with the help of local command executor service.
     */
    public void start() {
        configService.getConfig().getRequests().forEach(request -> {
            Long period = 0L;
            try {
                CronExpressionConverter.convert(request.getFrequency());
            } catch (CronExpressionException e){
                logger.fatal(e.getMessage());
            }

            scheduledExecutorService.scheduleAtFixedRate(
                    () -> executorService.execute(() -> exec(request.getScript())),
                    0,
                    period,
                    TimeUnit.MILLISECONDS
            );
        });
    }

    /**
     * Executes given script and sends result as message to Kafka cluster.
     * @param input script to be executed.
     */
    private void exec(String input) {
        ExecCommandService execCommandService = new ExecCommandService(input);

        CommandExecutorOutputDto scriptExecCommandOutput = null;
        try {
            scriptExecCommandOutput = commandExecutorService.executeCommand(execCommandService);
        } catch (CommandExecutorException e) {
            logger.fatal(e.getMessage());
        }

        kafkaService.send(KafkaLogsTopicEntity.of(
                UUID.randomUUID(),
                scriptExecCommandOutput.getNormalOutput(),
                scriptExecCommandOutput.getErrorOutput(),
                machineService.getHostName(),
                machineService.getHostAddress(),
                Timestamp.from(Instant.now())));
    }
}
