package com.resourcetracker.cloud;

import java.net.*;
import org.javatuples.*;

/**
 * Interface for different cloud providers
 *
 * @author YarikRevich
 *
 * @since 10 March 2022
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
