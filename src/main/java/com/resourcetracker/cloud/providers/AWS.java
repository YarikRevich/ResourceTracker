/**
 * 
 */
package com.resourcetracker.cloud.providers;

import java.net.InetAddress;

import com.resourcetracker.cloud.Provider;

/**
 * AWS implementation of Provider
 * 
 * @author YarikRevich
 *
 */
public class AWS implements Provider {

	@Override
	public boolean isResourceOnline(InetAddress publicAddress) {
		return false;
	}

}
