/**
 * 
 */
package com.resourcetracker.cloud.providers;

import java.net.InetAddress;
import org.javatuples.*;

import com.resourcetracker.cloud.Provider;
import com.resourcetracker.config.parsable.Parsable;

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

	@Override
	public void init(Pair<String, String> credentials) {
		
		// TODO Auto-generated method stub
	
	}

}
