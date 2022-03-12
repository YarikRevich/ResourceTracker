/**
 * 
 */
package com.resourcetracker.cloud.providers;

import java.net.InetAddress;

import com.resourcetracker.cloud.Provider;

/**
 * @author YarikRevich
 *
 */
public class GCP implements Provider {

	@Override
	public boolean isResourceOnline(InetAddress publicAddress) {
		return false;
	}

}
