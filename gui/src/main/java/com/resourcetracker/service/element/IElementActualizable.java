package com.resourcetracker.service.element;

/**
 * @param <T>
 */
public interface IElementActualizable<T> extends IElementResizable<T> {
  /** Represents handler for background updates, which will be running in the background. */
  void handleBackgroundUpdates();
}
