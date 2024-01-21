package com.resourcetracker.service.event.payload;

import com.resourcetracker.model.TopicLogsResult;
import com.resourcetracker.model.TopicLogsUnit;
import com.resourcetracker.service.event.common.EventType;
import com.resourcetracker.service.event.common.IEvent;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * Represents swap file open window event used for state management.
 */
public class SwapFileOpenWindowEvent extends ApplicationEvent implements IEvent {
    private final List<TopicLogsUnit> content;

    public SwapFileOpenWindowEvent(List<TopicLogsUnit> content) {
        super(content);

        this.content = content;
    }

    /**
     * @see IEvent
     */
    public EventType getEventType() {
        return EventType.SWAP_FILE_OPEN_WINDOW_EVENT;
    }
}
