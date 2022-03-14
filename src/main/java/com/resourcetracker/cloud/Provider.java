package com.resourcetracker.cloud;

import java.net.*;

import com.resourcetracker.config.parsable.Parsable;
import org.javatuples.*;


/**
 * Interface for different cloud providers
 * 
 * @author YarikRevich
 *
 */
public interface Provider {
	
	public void init(Pair<String, String> credentials);
	public boolean isResourceOnline(InetAddress publicAddress);
}
