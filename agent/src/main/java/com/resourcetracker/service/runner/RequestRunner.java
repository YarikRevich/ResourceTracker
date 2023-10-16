package com.resourcetracker.service.runner;

import java.util.concurrent.TimeUnit;

/**
 * Runner for requests set in context
 */
public class RequestRunner {
//    ConfigEntity.Request request;
//
//    public RequestRunner(ConfigEntity.Request request) {
//        this.request = request;
//    }
//
//    @Override
//    public synchronized void run() {
//        if (!this.request.getEmail().isEmpty()) {
//            new Thread(new ReportRunner(
//                    this.request.getEmail(), Integer.parseInt(this.request.getFrequency()))).run();
//        }
//        ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
//        exec.scheduleAtFixedRate(() -> {
//            procService.setCommandsWithEval(this.request.getData());
//            try {
//                procService.start();
//            } catch (ProcException e) {
//                throw new RuntimeException(e);
//            }
//
//            OutputFormatter outputFormatter = new OutputFormatter();
//            String formattedOutput = outputFormatter.formatStatusMessage(
//                    new OutputBuilder(), new OutputFormatter.StatusMessageData(
//                            request.getTag(), procService.getStdout()));
//            kafkaTemplate.send(Constants.KAFKA_STATUS_TOPIC, formattedOutput);
//        }, 0, Integer.parseInt(this.request.getFrequency()), TimeUnit.MILLISECONDS);
//    }
}