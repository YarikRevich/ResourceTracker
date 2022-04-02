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
	public void start();
	public void stop();

	public enum Providers {
		NONE,
		AWS,
		GCP,
		AZ
	}
}
