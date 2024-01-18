package com.resourcetracker;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.config.common.ValidConfigService;
import com.resourcetracker.service.kafka.KafkaService;
import com.resourcetracker.service.machine.MachineService;
import com.resourcetracker.service.scheduler.SchedulerService;
import com.resourcetracker.service.scheduler.executor.CommandExecutorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import({
  SchedulerService.class,
  ConfigService.class,
  ValidConfigService.class,
  KafkaService.class,
  MachineService.class,
  CommandExecutorService.class,
  PropertiesEntity.class
})
public class App implements ApplicationRunner {
  private static final Logger logger = LogManager.getLogger(App.class);

  @Autowired private ValidConfigService validConfigService;

  @Autowired private SchedulerService schedulerService;

  @Override
  public void run(ApplicationArguments args) {
    try {
      validConfigService.validate();
    } catch (Exception e) {
      logger.fatal(e.getMessage());
      return;
    }

    schedulerService.start();
  }
}
