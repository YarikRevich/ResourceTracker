package com.resourcetracker.service.event.integrated;

import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Represents a wrapper for application readiness event.
 */
@Getter
public class StageReadyEvent extends ApplicationEvent {
    private final Stage stage;

    public StageReadyEvent(Stage stage) {
        super(stage);
        this.stage = stage;
    }
}
