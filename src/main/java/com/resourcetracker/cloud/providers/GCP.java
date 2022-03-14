/**
 * 
 */
package com.resourcetracker.cloud.providers;

import java.net.InetAddress;

import com.resourcetracker.cloud.Provider;
import com.resourcetracker.config.parsable.Parsable;

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
