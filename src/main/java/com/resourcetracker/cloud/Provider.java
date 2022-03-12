package com.resourcetracker.cloud;

import java.net.*;


/**
 * Interface for different cloud providers
 * 
 * @author YarikRevich
 *
 */
public interface Provider {
	public boolean isResourceOnline(InetAddress publicAddress);
}
