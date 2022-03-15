package com.resourcetracker.listenerpoll;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ListenerPoll {
	private Queue<String> poll = new LinkedList<String>();
	private Queue<AddressWithTag> pollWithTags = new LinkedList<AddressWithTag>();
	
	final static Logger logger = LogManager.getLogger(ListenerPoll.class);

	private final int timeout = 5000;

	private boolean isReachable(String publicAddress) {
		try {
			InetAddress createdAddress = InetAddress.getByName(publicAddress);
			if (createdAddress == null) {
				return false;
			}
			return true;
		} catch (IOException e) {
			//STUB
		}
		return false;
	};

	public void add(ArrayList<String> addresses) {
		for (String address : addresses) {
			poll.add(address);
		}
	}

	public void add(Map<String, String> addresses) {
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
