package com.resourcetracker.service.client.observer;

import com.resourcetracker.dto.CommandExecutorOutputDto;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.service.client.command.HealthCheckClientCommandService;
import com.resourcetracker.service.client.command.ReadinessCheckClientCommandService;
import com.resourcetracker.service.event.payload.ConnectionStatusEvent;
import com.resourcetracker.service.hand.config.command.OpenConfigEditorCommandService;
import com.resourcetracker.service.hand.executor.CommandExecutorService;
import com.resourcetracker.service.scheduler.SchedulerHelper;
import jakarta.annotation.PostConstruct;
import java.util.Objects;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/** Provides resource observables to manage state of the application. */
@Component
public class ResourceObserver {
  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private PropertiesEntity properties;

  @Autowired private CommandExecutorService commandExecutorService;

  @Autowired private HealthCheckClientCommandService healthCommandService;

  @Autowired private ReadinessCheckClientCommandService readinessCommandService;

  /** Sends healthcheck requests to API Server and updates connection status. */
  @PostConstruct
  private void handleHealthCommand() {
    SchedulerHelper.scheduleTask(
        () -> {
          ConnectionStatusEvent connectionStatusEvent;

          try {
            HealthCheckResult result = healthCommandService.process(null);

            connectionStatusEvent =
                switch (result.getStatus()) {
                  case UP -> new ConnectionStatusEvent(true);
                  case DOWN -> new ConnectionStatusEvent(false);
                };
          } catch (ApiServerException e) {
            connectionStatusEvent = new ConnectionStatusEvent(false);
          }

          applicationEventPublisher.publishEvent(connectionStatusEvent);
        },
        properties.getProcessHealthcheckPeriod());
  }

//  @SneakyThrows
//  @PostConstruct
//  private void handleReadinessCommand() {
//    OpenConfigEditorCommandService initCommandService =
//        new OpenConfigEditorCommandService(properties);
//
//    CommandExecutorOutputDto initCommandOutput = null;
//
//    try {
//      initCommandOutput = commandExecutorService.executeCommand(initCommandService);
//    } catch (CommandExecutorException e) {
//      System.out.println(e.getMessage());
//    }
//
//    String initCommandErrorOutput = initCommandOutput.getErrorOutput();
//
//    if (Objects.nonNull(initCommandErrorOutput) && !initCommandErrorOutput.isEmpty()) {
//      System.out.println(initCommandErrorOutput);
//    }

//    SchedulerHelper.scheduleTask(
//        () -> {
          //                    DeploymentStatusEvent deploymentStatusEvent;
          //
          //                    try {
          //                        HealthCheckResult result =
          // readinessCommandService.process(null);
          //
          //                        deploymentStatusEvent =
          //                                switch (result.getStatus()) {
          //                                    case UP -> new DeploymentStatusEvent(true);
          //                                    case DOWN -> new DeploymentStatusEvent(false);
          //                                };
          //                    } catch (ApiServerException e) {
          //                        deploymentStatusEvent = new DeploymentStatusEvent(false);
          //                    }
          //
          //                    applicationEventPublisher.publishEvent(deploymentStatusEvent);
//        },
//        properties.getProcessReadinessPeriod());
//  }
}
