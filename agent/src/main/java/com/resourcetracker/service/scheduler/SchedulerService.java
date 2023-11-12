package com.resourcetracker.service.scheduler;

import com.resourcetracker.entity.KafkaLogsTopicEntity;
import com.resourcetracker.exception.SchedulerException;
import com.resourcetracker.service.config.ConfigService;
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

@Service
public class SchedulerService {
    private static final Logger logger = LogManager.getLogger(SchedulerService.class);

    @Autowired
    private ConfigService configService;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private MachineService machineService;

    private final ScheduledExecutorService scheduledExecutorService;
    private final ExecutorService executorService;
    private final SProcessExecutor processExecutor;

    public SchedulerService() {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
        this.processExecutor = SProcessExecutor.getCommandExecutor();
    }

    public void start() {
        configService.getConfig().getRequests().forEach(request -> {
            scheduledExecutorService.scheduleAtFixedRate(
                    () -> executorService.execute(() -> exec(request.getScript())),
                    0,
                    configService.getCronExpressionInMilliseconds(request.getFrequency()),
                    TimeUnit.MILLISECONDS
            );
        });
    }

    private void exec(String input) {
        ScriptExecCommandService scriptExecCommand = new ScriptExecCommandService(input);
        try {
            processExecutor.executeCommand(scriptExecCommand);
        } catch (IOException | NonMatchingOSException e) {
            logger.fatal(e.getMessage());
        }

        try {
            if (!scriptExecCommand.waitForOutput()){
                logger.fatal(new SchedulerException().getMessage());
            }
        } catch (IOException e){
            logger.fatal(e.getMessage());
        }

        String normalOutput = null;
        try {
            normalOutput = scriptExecCommand.getNormalOutput();
        } catch (SProcessNotYetStartedException e) {
            logger.fatal(e.getMessage());
        }

        String errorOutput = null;
        try {
            errorOutput = scriptExecCommand.getErrorOutput();
        } catch (SProcessNotYetStartedException e) {
            logger.fatal(e.getMessage());
        }

        kafkaService.send(KafkaLogsTopicEntity.of(
                UUID.randomUUID(),
                normalOutput,
                errorOutput,
                machineService.getHostName(),
                machineService.getHostAddress(),
                Timestamp.from(Instant.now())));
    }
}
