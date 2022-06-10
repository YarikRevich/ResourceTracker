package com.resourcetracker.tools.frequency;

/**
 * Contains aliases for frequency ticker
 *
 * @author YarikRevich
 */
public class Frequency {
	public static final int secondInMilliseconds = 1_000;
	public static final int minuteInMilliseconds = secondInMilliseconds * 60;
	public static final int hourInMilliseconds = minuteInMilliseconds * 60;
	public static final int dayInMilliseconds = hourInMilliseconds * 24;
	public static final int weekInMilliseconds = dayInMilliseconds * 7;
}
