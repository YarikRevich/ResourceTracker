package com.resourcetracker.config.parsable;

import org.javatuples.*;
import java.util.TreeMap;
import java.util.ArrayList;
import com.resourcetracker.listenerpoll.*;

public interface Parsable {	
	public ArrayList<String> getCloudProviderRawPublicAddresses();
	public TreeMap<String, Address> getCloudProviderRawPublicAddressesWithTags();
	public ArrayList<String> getLocalRawPublicAddresses();
	public TreeMap<String, Address> getLocalRawPublicAddressesWithTags();
	public Pair<String, String> getCloudProviderCredentials() throws Exception;
	public boolean isDemon();
}
