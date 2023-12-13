package com.resourcetracker.service.element.observer;


import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElementActualizable;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.LocalState;
import com.resourcetracker.service.scheduler.SchedulerHelper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Responsible for background updates handling and their propagation
 * to the related elements.
 */
@Component
public class ElementObserver {

    @Autowired
    private PropertiesEntity properties;

    /**
     * Handles background updates and propagates them
     * to proper actualizable elements.
     */
    @PostConstruct
    private void handleBackgroundUpdates() {
        SchedulerHelper.scheduleTask(() ->
            ElementStorage.getActualizables()
                    .forEach(IElementActualizable::handleBackgroundUpdates),
                properties.getProcessBackgroundPeriod());
    }

    /**
     * Handles window width resize updates and propagates them
     * to proper resizable elements.
     */
    @PostConstruct
    private void handlePrefWidthUpdates() {
        SchedulerHelper.scheduleTask(() -> {
                    if (LocalState.isWindowWidthChanged()) {
                        ElementStorage.getResizables()
                                .forEach(IElementResizable::handlePrefWidth);

                        LocalState.synchronizeWindowWidth();
                    }
                },
                properties.getProcessWindowWidthPeriod());
    }

    /**
     * Handles window height resize updates and propagates them
     * to proper resizable elements.
     */
    @PostConstruct
    private void handlePrefHeightUpdates() {
        SchedulerHelper.scheduleTask(() -> {
                    if (LocalState.isWindowHeightChanged()) {
                        ElementStorage.getResizables()
                                .forEach(IElementResizable::handlePrefHeight);

                        LocalState.synchronizeWindowHeight();
                    }
                },
                properties.getProcessWindowHeightPeriod());
    }
}
