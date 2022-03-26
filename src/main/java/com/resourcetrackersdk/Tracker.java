package com.resourcetrackersdk;

import com.resourcetrackersdk.loop.Loop;

public class Tracker {
	public static void main(String[] args) throws Throwable {
		Loop.setContext(args);
		Loop.prerun();
		Loop.run();
	};
}
