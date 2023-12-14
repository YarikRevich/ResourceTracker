package com.resourcetracker.service.client.observer;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.service.client.command.HealthCommandService;
import com.resourcetracker.service.event.state.payload.ConnectionStatusEvent;
import com.resourcetracker.service.scheduler.SchedulerHelper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/** Provides resource observables to manage state of the application. */
@Component
public class ResourceObserver {
  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Autowired private PropertiesEntity properties;

  @Autowired private HealthCommandService healthCommandService;

  /** Sends healthcheck requests to API Server and updates connection status. */
  @PostConstruct
  private void handleHealthCommand() {
        SchedulerHelper.scheduleTask(
            () -> {
                ConnectionStatusEvent connectionStatusEvent;

                try {
                    HealthCheckResult result = healthCommandService.process();

                    connectionStatusEvent = switch (result.getStatus()) {
                        case UP -> new ConnectionStatusEvent(true);
                        case DOWN -> new ConnectionStatusEvent(false);
                    };
                } catch (ApiServerNotAvailableException e){
                    connectionStatusEvent = new ConnectionStatusEvent(false);
                }

                applicationEventPublisher.publishEvent(connectionStatusEvent);
            },
            properties.getProcessHealthcheckPeriod());
  }
}
