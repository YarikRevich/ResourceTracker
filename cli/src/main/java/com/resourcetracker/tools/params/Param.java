package com.resourcetracker.tools.params;

public interface Param<T> {
    public void setValue(T value);

    public T getValue();
}
