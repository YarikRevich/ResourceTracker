package com.resourcetracker.config.parsable;

import org.javatuples.*;
import java.util.Queue;
import java.net.InetAddress;

public interface Parsable {	
	public InetAddress[] getCloudProviderRawPublicAddresses();
	public InetAddress[] getLocalRawPublicAddresses();
	public Pair<String, String> getCloudRawProviderCredentials() throws Exception;
}
