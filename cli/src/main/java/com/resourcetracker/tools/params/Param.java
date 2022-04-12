package com.resourcetracker.tools.params;

import java.util.ArrayList;

/**
 *
 *
 * @author YarikRevich
 * @since 10 April 2022
 */
public interface Param<T> {
	public void setValue(T value);

	public T getValue();

	public void setPositionalArg(String posArg);

	public ArrayList<String> getPositionalArgs();

	public boolean isWithPositionalArgs();

	public BooleanParam withPositionalArgs();
}
