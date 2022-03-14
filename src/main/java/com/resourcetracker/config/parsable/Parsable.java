package com.resourcetracker.config.parsable;

import org.javatuples.*;
import java.util.Queue;
import java.util.Map;
import java.util.ArrayList;
import java.net.InetAddress;

public interface Parsable {	
	public ArrayList<InetAddress> getCloudProviderRawPublicAddresses();
	public Map<String, InetAddress> getCloudProviderRawPublicAddressesWithTags();
	public ArrayList<InetAddress> getLocalRawPublicAddresses();
	public Map<String, InetAddress> getLocalRawPublicAddressesWithTags();
	public Pair<String, String> getCloudProviderCredentials() throws Exception;
}
