package com.resourcetracker.cloud.providers;

import java.net.InetAddress;

import com.resourcetracker.cloud.Provider;
import com.resourcetracker.config.parsable.Parsable;

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
