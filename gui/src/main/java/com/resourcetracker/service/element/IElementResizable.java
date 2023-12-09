package com.resourcetracker.service.element;

/** */
public interface IElementResizable<T> extends IElement<T> {
  /**
   * Propagates actualized window width.
   *
   * @param value window width value.
   */
  void prefWidth(Double value);

  /**
   * Propagates actualized window height.
   *
   * @param value window height value.
   */
  void prefHeight(Double value);
}
