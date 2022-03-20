package com.resourcetracker;

import com.resourcetracker.listenerpoll.ListenerPoll;

public class Tracker {
	public static void main(String[] args) throws Throwable {
		ListenerPoll listenerPoll = new ListenerPoll();
		listenerPoll.listen();
		// if (parser.isCloud()) {
		// 	listenerPoll.add(parser.getCloudProviderRawPublicAddresses());
		// 	listenerPoll.add(parser.getCloudProviderRawPublicAddressesWithTags());

		// listenerPoll.add(parser.getLocalRawPublicAddresses());
		// listenerPoll.add(parser.getLocalRawPublicAddressesWithTags());
		
		// if 
		// listenerPoll.listen();
	};
}
