package com.resourcetracker;

import com.resourcetracker.config.*;

import com.resourcetracker.listenerpoll.ListenerPoll;
import com.resourcetracker.cloud.Provider;
import com.resourcetracker.cloud.providers.*;

public class Tracker {
	@SuppressWarnings("null")
	public static void main(String[] args) throws Throwable {
		Parser parser = new Parser();

		ListenerPoll listenerPoll = new ListenerPoll();

		if (parser.isCloud()) {
			listenerPoll.add(parser.getCloudProviderRawPublicAddresses());
			listenerPoll.add(parser.getCloudProviderRawPublicAddressesWithTags());

			Provider cloudProvider = null;
			switch (parser.getCloudProvider()) {
			case AWS:
				cloudProvider = new AWS();
			case GCP:
				cloudProvider = new GCP();
			case AZ:
				cloudProvider = new AZ();
			case NONE:
				throw new Exception("'NONE' not available for initalisation");
			}
			cloudProvider.init(parser.getCloudProviderCredentials());
		}

		listenerPoll.add(parser.getLocalRawPublicAddresses());
		listenerPoll.add(parser.getLocalRawPublicAddressesWithTags());
		
		if 
		listenerPoll.listen();
	};
}
