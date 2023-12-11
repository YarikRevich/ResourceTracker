package com.resourcetracker.service.event.state.payload;

import javafx.geometry.Rectangle2D;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class WindowWidthUpdateEvent extends ApplicationEvent {
    private final Double width;

    public WindowWidthUpdateEvent(Double width) {
        super(width);

        this.width = width;
    }
}