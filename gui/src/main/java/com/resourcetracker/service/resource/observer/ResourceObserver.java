package com.resourcetracker.service.resource.observer;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.event.state.payload.ConnectionStatusEvent;
import com.resourcetracker.service.resource.command.HealthCommandService;
import com.resourcetracker.service.scheduler.SchedulerHelper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ResourceObserver {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private PropertiesEntity properties;

    @Autowired
    private HealthCommandService healthCommandService;

    @PostConstruct
    private void handleHealthCommand() {
        SchedulerHelper.scheduleTask(() -> {
            applicationEventPublisher.publishEvent(new ConnectionStatusEvent(true));
        }, properties.getProcessHealthcheckPeriod());
    }
}
