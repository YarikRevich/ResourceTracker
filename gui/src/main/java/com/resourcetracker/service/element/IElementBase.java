package com.resourcetracker.service.element;

import com.resourcetracker.service.element.storage.ElementStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface IElementBase {
    Map<UUID, Object> storage = new HashMap<>();

    default void setElement(UUID id, Object element) {
        storage.putIfAbsent(id, element);
    }

    default Object getElement(UUID id) {
        return storage.get(id);
    }
}
