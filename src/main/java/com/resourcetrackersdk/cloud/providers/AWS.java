/**
 * 
 */
package com.resourcetrackersdk.cloud.providers;

import java.net.InetAddress;
import org.javatuples.*;

import com.resourcetrackersdk.cloud.Provider;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

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
