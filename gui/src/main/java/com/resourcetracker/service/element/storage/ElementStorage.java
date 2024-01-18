package com.resourcetracker.service.element.storage;

import com.resourcetracker.service.element.IElementActualizable;
import com.resourcetracker.service.element.IElementResizable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

/** Represents global element storage. */
public class ElementStorage {
  private static final Map<UUID, Object> storage = new HashMap<>();

  /** Dedicated storage for actualizable elements. */
  @Getter private static final List<IElementActualizable> actualizables = new ArrayList<>();

  /** Dedicated storage for resizable elements. */
  @Getter private static final List<IElementResizable> resizables = new ArrayList<>();

  /**
   * Saves element in the global element storage.
   *
   * @param id ID of the element to be saved.
   * @param element element to be saved.
   */
  public static void setElement(UUID id, Object element) {
    storage.putIfAbsent(id, element);
  }

  /**
   * Retrieves element in the global element storage.
   *
   * @param id ID of the element to be retrieved.
   * @return retrieved element.
   * @param <T> type of the element to be retrieved.
   */
  @SuppressWarnings("unchecked")
  public static <T> T getElement(UUID id) {
    return (T) storage.get(id);
  }

  /**
   * Saves actualizable element in the actualizable element storage.
   *
   * @param actualizable actualizable element to be saved.
   */
  public static void setActualizable(IElementActualizable actualizable) {
    actualizables.add(actualizable);
  }

  /**
   * Saves resizable element in the resizable element storage.
   *
   * @param resizable resizable element to be saved.
   */
  public static void setResizable(IElementResizable resizable) {
    resizables.add(resizable);
  }
}
