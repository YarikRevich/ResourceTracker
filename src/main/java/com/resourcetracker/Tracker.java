package com.resourcetracker;

import com.resourcetracker.config.*;

import org.apache.log4j.*;
import com.resourcetracker.listenerpoll.ListenerPoll;
import com.resourcetracker.cloud.Provider;
import com.resourcetracker.cloud.Providers;
import com.resourcetracker.cloud.providers.*;

import org.javatuples.*;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Queue;

public class Tracker {
	public static void main(String[] args) throws Throwable {
		BasicConfigurator.configure();
		
		Config reader = new Config();
		Parser parser = new Parser(reader);
		parser.validate();
		
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
		listenerPoll.listen();
	};
}
