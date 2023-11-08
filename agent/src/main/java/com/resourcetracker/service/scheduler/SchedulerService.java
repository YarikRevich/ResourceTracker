package com.resourcetracker.service.scheduler;

import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.kafka.producer.KafkaService;
import com.resourcetracker.service.scheduler.command.ScriptExecCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import process.SProcess;
import process.SProcessExecutor;
import process.exceptions.NonMatchingOSException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class SchedulerService {
    @Autowired
    private ConfigService configService;

    @Autowired
    private KafkaService kafkaService;

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

    public void exec(String input) {
        ScriptExecCommandService scriptExecCommand = new ScriptExecCommandService(input);
        try {
            processExecutor.executeCommand(scriptExecCommand);
        } catch (IOException | NonMatchingOSException e) {
            throw new RuntimeException(e);
        }

        kafkaService.send()
//        kafkaTemplate.send(Constants.KAFKA_STATUS_TOPIC, formattedOutput);
    }
}
