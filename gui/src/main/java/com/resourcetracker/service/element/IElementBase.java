package com.resourcetracker.service.element;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface IElementBase {
    Map<UUID, Object> storage = new HashMap<>();

    default void setElement(UUID id, Object element) {
        storage.putIfAbsent(id, element);
    }

    @SuppressWarnings("unchecked")
    default <T> T getElement(UUID id) {
        return (T)storage.get(id);
    }
}
