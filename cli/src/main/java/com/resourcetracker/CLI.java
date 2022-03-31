package com.resourcetracker;

import com.resourcetracker.loop.Loop;

public class CLI {
	public static void main(String[] args) throws Exception {
		Loop.setContext(args);
		Loop.prerun();
		Loop.run();
	};
}
