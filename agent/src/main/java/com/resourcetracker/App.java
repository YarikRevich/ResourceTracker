package com.resourcetracker;

import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.kafka.KafkaService;
import com.resourcetracker.service.machine.MachineService;
import com.resourcetracker.service.scheduler.SchedulerService;
import com.resourcetracker.service.scheduler.executor.CommandExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import({
  SchedulerService.class,
  ConfigService.class,
  KafkaService.class,
  MachineService.class,
  CommandExecutorService.class
})
public class App implements ApplicationRunner {
  //    @Autowired
  //    FatalAppender fatalAppender;

  @Autowired SchedulerService schedulerService;

  @Override
  public void run(ApplicationArguments args) {
    schedulerService.start();
  }
}
