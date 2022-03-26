package com.resourcetrackersdk.listenerpoll;

import java.io.IOException;
import java.net.*;
import java.util.TreeMap;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

import com.resourcetrackersdk.config.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ListenerPoll {
	private Queue<Address> poll = new LinkedList<Address>();
	private Queue<AddressWithTag> pollWithTags = new LinkedList<AddressWithTag>();

	final static Config config = Config.getInstance();
	final static Logger logger = LogManager.getLogger(ListenerPoll.class);

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
		for (int i = 0; i < addresses.size(); i++) {
			poll.add(new Address(i, addresses.get(i)));
		}
	}

	public void add(TreeMap<String, Address> addresses) {
		addresses.forEach((key, value) -> {
			pollWithTags.add(new AddressWithTag(value.index(), key, value.address()));
		});
	}

	synchronized public void listen() {
		ArrayList<ListenerResult> infoResult = new ArrayList<ListenerResult>();
		synchronized (this) {
			ListenerPoll.logger.info("Scanning standard poll with tags...");
			this.poll.forEach(address -> {
				new Thread(() -> {
					boolean isReachable = this.isReachable(address.address());
					if (isReachable) {
						infoResult.add(new ListenerResult(address.index(), String.format("%s: ok", address.toString())));
					}else {
						infoResult.add(new ListenerResult(address.index(), String.format("%s: bad", address.toString())));
					}
				}).start();
			});
		}

		if (!this.pollWithTags.isEmpty()) {
			ListenerPoll.logger.info("Scanning poll with tags...");
			synchronized (this) {
				this.pollWithTags.forEach(addressWithTag -> {
					new Thread(() -> {
						boolean isReachable = this.isReachable(addressWithTag.address());
						if (isReachable) {
							infoResult.add(new ListenerResult(addressWithTag.index(), String.format("%s(%s): ok", addressWithTag.tag(), addressWithTag.address().toString())));
						} else {
							infoResult.add(new ListenerResult(addressWithTag.index(), String.format("%s(%s): bad", addressWithTag.tag(), addressWithTag.address().toString())));
						}
					}).start();
				});
			}
		}
		Collections.sort(infoResult, new ListenerResultSort());
		
		for (ListenerResult res : infoResult){
			ListenerPoll.logger.info(res.message());
		}
	};
}
