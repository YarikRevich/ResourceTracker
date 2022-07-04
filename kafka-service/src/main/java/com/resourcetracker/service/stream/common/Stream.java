package com.resourcetracker.service.stream.common;

import java.util.Properties;

public interface Stream {
	public void init(Properties props);
	public void run();
}
