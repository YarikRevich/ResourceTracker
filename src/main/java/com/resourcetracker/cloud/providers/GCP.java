/**
 * 
 */
package com.resourcetracker.cloud.providers;

import java.net.InetAddress;

import com.resourcetracker.cloud.Provider;

import org.javatuples.*;

/**
 * @author YarikRevich
 *
 */
public class GCP implements Provider {
	@Override
	public boolean isResourceOnline(InetAddress publicAddress) {
		return false;
	}

	@Override
	public void init(Pair<String, String> credentials) {
	}
}
