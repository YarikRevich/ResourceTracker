package com.resourcetracker.listenerpoll;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.log4j.Logger;

public class ListenerPoll {
	private Queue<InetAddress> poll = new LinkedList<InetAddress>();
	private Queue<AddressWithTag> pollWithTags = new LinkedList<AddressWithTag>();
	
	final static Logger logger = Logger.getLogger(ListenerPoll.class);

	private final int timeout = 5000;

	private boolean isReachable(InetAddress publicAddress) {
		try {
			System.out.println(publicAddress);
			return publicAddress.isReachable(this.timeout);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	};

	public void add(ArrayList<InetAddress> addresses) {
		for (InetAddress address : addresses) {
			poll.add(address);
		}
	}

	public void add(Map<String, InetAddress> addresses) {
		addresses.forEach((tag, address) -> {
			pollWithTags.add(new AddressWithTag(tag, address));
		});
	}

	synchronized public void listen() {
		synchronized (this) {
			ListenerPoll.logger.info("Scanning standard poll with tags...");
			this.poll.forEach(address -> {
				new Thread(() -> {
					boolean isReachable = this.isReachable(address);
					if (isReachable) {
						ListenerPoll.logger.info(String.format("%s: ok", address.toString()));
					}else {
						ListenerPoll.logger.info(String.format("%s: bad", address.toString()));
					}
				}).start();
				;
			});
		}

		if (!this.pollWithTags.isEmpty()) {
			ListenerPoll.logger.info("Scanning poll with tags...");
			synchronized (this) {
				this.pollWithTags.forEach(addressWithTag -> {
					new Thread(() -> {
						boolean isReachable = this.isReachable(addressWithTag.address());
						if (isReachable) {
							ListenerPoll.logger.info(String.format("%s: ok", addressWithTag.tag()));
						} else {
							ListenerPoll.logger.info(String.format("%s: bad", addressWithTag.tag()));
						}
					}).start();
					;
				});
			}
		}
	};
}
