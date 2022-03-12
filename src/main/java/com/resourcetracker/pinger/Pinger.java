package com.resourcetracker.pinger;

import java.io.IOException;
import java.net.*;

public class Pinger {
	private final int timeout = 5000;
	
	public boolean isReachable(InetAddress publicAddress){
		try {
			return publicAddress.isReachable(this.timeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	};
}
