package com.resourcetracker.listenerpoll;

import java.io.IOException;
import java.net.*;
import java.util.Queue;

import org.apache.log4j.Logger;

public class ListenerPoll {
	private Queue<InetAddress> poll; 
	
	final static Logger logger = Logger.getLogger(ListenerPoll.class);
	
	private final int timeout = 5000;
	
	private boolean isReachable(InetAddress publicAddress){
		try {
			return publicAddress.isReachable(this.timeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	};
	
	public void add(InetAddress... addresses) {
		for (InetAddress address : addresses) {
			poll.add(address);
		}
	}
	
	synchronized public void listen() {
		this.poll.forEach(address -> {
			new Thread(() -> {
				boolean isReachable = this.isReachable(address);
				if (isReachable) {
					 
				}
			}).start();;
		});
	};
}
