package com.resourcetracker.utils;

/**
 * @author YarikRevich
 */
public class Utils {
	public boolean isOnline(String url) {
		try {
			InetAddress createdAddress = InetAddress.getByName(publicAddress);
			if (createdAddress == null) {
				return false;
			}
			return true;
		} catch (IOException e) {
		}
		return false;
	};
}
