package com.resourcetracker;

import com.resourcetracker.converter.ConfigConverter;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.runner.Runner;
import com.resourcetracker.service.scheduler.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resourcetracker.service.formatter.OutputBuilder;
import com.resourcetracker.service.formatter.OutputFormatter;

@Component
public class App {
  @Autowired
  SchedulerService schedulerService;
//	@Autowired
//	StatusProducer statusProducer;
//
//	@Autowired
//	LogsProducer logsProducer;
//
//	@Autowired
//	CommandService commandService;
  public static void main(String[] args) {
//      configConverter.fromJson();
//		var parsedContext = contextService.getParsedContext();
//
//		parsedContext.requests.forEach((final ConfigEntity.Request request) -> {
//			new Thread(new RequestRunner(request)).run();
//		});
  }

  public void run() {

  }
}
