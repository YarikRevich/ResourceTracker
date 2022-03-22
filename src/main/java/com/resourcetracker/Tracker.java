package com.resourcetracker;

import com.resourcetracker.loop.Loop;

public class Tracker {
	public static void main(String[] args) throws Throwable {
		Loop.prerun();
		Loop.run();
		

		// if (parser.isCloud()) {
		// 	listenerPoll.add(parser.getCloudProviderRawPublicAddresses());
		// 	listenerPoll.add(parser.getCloudProviderRawPublicAddressesWithTags());

		// listenerPoll.add(parser.getLocalRawPublicAddresses());
		// listenerPoll.add(parser.getLocalRawPublicAddressesWithTags());
		
		// if 
		// listenerPoll.listen();
	};
}
