package com.resourcetracker;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.resourcetracker.service.formatter.OutputBuilder;
import com.resourcetracker.service.formatter.OutputFormatter;

@Component
public class App implements CommandLineRunner {
//	@Autowired
//	StatusProducer statusProducer;
//
//	@Autowired
//	LogsProducer logsProducer;
//
//	@Autowired
//	CommandService commandService;

  @Override
  public void run(String... args) {
//		var parsedContext = contextService.getParsedContext();
//
//		parsedContext.requests.forEach((final ConfigEntity.Request request) -> {
//			new Thread(new RequestRunner(request)).run();
//		});
  }
}