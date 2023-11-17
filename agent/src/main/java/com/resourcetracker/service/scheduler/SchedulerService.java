package com.resourcetracker.service.scheduler;

import com.resourcetracker.entity.CommandExecutorOutputEntity;
import com.resourcetracker.entity.KafkaLogsTopicEntity;
import com.resourcetracker.entity.ScriptExecCommandInputEntity;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.exception.SchedulerException;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.executor.CommandExecutorService;
import com.resourcetracker.service.kafka.KafkaService;
import com.resourcetracker.service.machine.MachineService;
import com.resourcetracker.service.scheduler.command.ScriptExecCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import process.SProcessExecutor;
import process.exceptions.NonMatchingOSException;
import process.exceptions.SProcessNotYetStartedException;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * SchedulerService provides access to schedule incoming
 * tasks to be executed with the given conditions.
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

    @Autowired
    private ScriptExecCommandService scriptExecCommandService;

    private final ScheduledExecutorService scheduledExecutorService;
    private final ExecutorService executorService;

    /**
     * Default constructor, which initializes scheduler thread executor
     * and virtual thread executor.
     */
    public SchedulerService() {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    /**
     * Starts executor listening process.
     */
    public void start() {
        configService.getConfig().getRequests().forEach(request ->
                scheduledExecutorService.scheduleAtFixedRate(
                    () -> executorService.execute(() -> exec(request.getScript())),
                    0,
                    configService.getCronExpressionInMilliseconds(request.getFrequency()),
                    TimeUnit.MILLISECONDS
        ));
    }

    /**
     * Executes given script and sends result as message
     * to Kafka cluster.
     * @param input script to be executed
     */
    private void exec(String input) {
        scriptExecCommandService.setInput(ScriptExecCommandInputEntity.of(input));

        CommandExecutorOutputEntity scriptExecCommandOutput;

        try {
            scriptExecCommandOutput = commandExecutorService.executeCommand(scriptExecCommandService);
        } catch (CommandExecutorException e) {
            logger.fatal(e.getMessage());
            return;
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
