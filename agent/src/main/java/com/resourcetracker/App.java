package com.resourcetracker;

import com.resourcetracker.service.scheduler.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class App implements ApplicationRunner {
//  @Autowired
//  SchedulerService schedulerService;

  @Override
  public void run(ApplicationArguments args) {
//    schedulerService.start();
  }
}
