package com.resourcetracker.service.element;

/**
 *
 * @param <T>
 */
public interface IElementActualizable<T> extends IElement<T> {
    /**
     * Represents handler for background updates, which will
     * be running in the background.
     */
    void handleBackgroundUpdates();
}
