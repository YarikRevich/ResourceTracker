package com.resourcetrackersdk.cloud.providers;

import java.net.InetAddress;

import com.resourcetrackersdk.cloud.Provider;
import com.resourcetrackersdk.config.parsable.Parsable;

import org.javatuples.*;

public class AZ implements Provider {

	@Override
	public boolean isResourceOnline(InetAddress publicAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(Pair<String, String> credentials) {
		// TODO Auto-generated method stub
		
	}

}
