package com.resourcetrackersdk.config.parsable;

import org.javatuples.Pair;
import java.util.TreeMap;
import com.resourcetrackersdk.listenerpoll.Address;
import com.resourcetrackersdk.tools.exceptions.ConfigError;

/**
 * Describes behavior of config the external api of config
 * @author YarikRevich
 */
public interface Parsable {	
	public TreeMap<String, Address> getAddresses() throws ConfigError;

	public Pair<String, String> getCloudProviderCredentials() throws Exception;
	public boolean getDemon();
	public boolean getSaveToBucket();
}
