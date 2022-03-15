package com.resourcetracker.config.parsable;

import org.javatuples.*;
import java.util.Queue;
import java.util.Map;
import java.util.ArrayList;
import java.net.InetAddress;

public interface Parsable {	
	public ArrayList<String> getCloudProviderRawPublicAddresses();
	public Map<String, String> getCloudProviderRawPublicAddressesWithTags();
	public ArrayList<String> getLocalRawPublicAddresses();
	public Map<String, String> getLocalRawPublicAddressesWithTags();
	public Pair<String, String> getCloudProviderCredentials() throws Exception;
}
