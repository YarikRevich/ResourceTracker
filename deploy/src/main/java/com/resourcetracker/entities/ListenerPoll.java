package com.resourcetracker.listenerpoll;

import java.io.IOException;
import java.net.*;
import java.util.TreeMap;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

import com.resourcetracker.config.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ListenerPoll {
	private Queue<Address> poll = new LinkedList<Address>();
	// private Queue<AddressWithTag> pollWithTags = new LinkedList<AddressWithTag>();

	final static Config config = Config.getInstance();
	

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
}
