package com.resourcetracker.cloud;

import java.net.*;

import org.javatuples.*;


/**
 * Interface for different cloud providers
 * 
 * @author YarikRevich
 *
 * @see
 */
public interface Provider {
	public void init(Pair<String, String> credentials);
	public boolean isResourceOnline(InetAddress publicAddress);

	public enum Providers {
		NONE,
		AWS,
		GCP,
		AZ
	}
}
