package com.resourcetracker.service.element;

/**
 * Exposes basic UI element functions used for its management.
 *
 * @param <T> base element type.
 */
public interface IElement<T> {
  /**
   * @return
   */
  T getContent();
}
