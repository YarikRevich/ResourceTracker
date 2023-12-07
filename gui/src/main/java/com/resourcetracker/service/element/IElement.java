package com.resourcetracker.service.element;

/**
 *
 * @param <T>
 */
public interface IElement<T> extends IElementBase {
    T getContent();
}
