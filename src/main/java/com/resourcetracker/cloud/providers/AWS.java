/**
 * 
 */
package com.resourcetracker.cloud.providers;

import java.net.InetAddress;
import org.javatuples.*;

import com.resourcetracker.cloud.Provider;

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
		AwsBasicCredentials.
		// TODO Auto-generated method stub
	
	}

}
