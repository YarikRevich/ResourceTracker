package com.resourcetracker.config;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.io.*;

import com.resourcetracker.cloud.Provider.Providers;
import com.resourcetracker.tools.utils.*;
import com.resourcetracker.tools.exceptions.ConfigError;

import java.time.LocalDate;
import com.resourcetracker.entities.Entity;

import org.json.JSONObject;

import org.javatuples.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.apache.commons.io.IOUtils;

import org.yaml.snakeyaml.Yaml;

/**
 * Parses YAML config file
 * 
 * @author YarikRevich
 *
 */
public final class Config {
	private static TreeMap<String, Object> obj;

	/**
	 * 
	 * 
	 * @exception throws exception if YAML config is empty
	 */
	private Object getInputStreamOfLocalConfigFile() {
		File file = new File(ConfigPath.getConfigPath());
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Yaml yaml = new Yaml();
		return yaml.load(inputStream);
	}

	public Config() {
		Yaml yaml = new Yaml();
		yaml.load(this.getInputStreamOfLocalConfigFile());
		try {
			Config.obj = new ConvertMapToTreeMap<Object>(config).getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Constructor used for testing purpose
	 * @param contains YAML config file
	 */
	public Config(String src) {
		Yaml yaml = new Yaml();
		yaml.load(IOUtils.toInputStream(src));
		try {
			Config.obj = new ConvertMapToTreeMap<Object>(config).getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Validates YAML config file
	 * 
	 * @return if YAML config file passes validation
	 * 
	 */
	public static boolean isValid() throws Exception {
		Config.getAddresses();
		Providers provider = Config.getCloudProvider();
		switch (provider) {
			case AWS:
				Config.getProfile();
			case GCP:
				Config.getProject();
				Config.getRegion();
				Config.getZone();
			case AZ:
		}
		Config.getCredentials();
		Config.getReportEmail();
		Config.getReportFrequency();

		return true;
	};

	public static String getReportEmail() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("mailing");
		return (String) cloud.get("email");
	}

	public static LocalDate getReportFrequency() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("settings");
		// (String) cloud.get("report_frequency");
		return null;
	}

	/**
	 * looks for cloud provider set in YAML config file
	 * 
	 * @return cloud provider set in YAML config file
	 */
	public static Providers getCloudProvider() throws ConfigError {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
		String cloudProvider = (String) cloud.get("provider");

		switch (cloudProvider) {
			case "aws":
				return Providers.AWS;
			case "gcp":
				return Providers.GCP;
			case "az":
				return Providers.AZ;
		}
		throw new ConfigError("Provider is not available");
	}

	public static String getProfile() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
		return (String) cloud.get("profile");
	}

	public static String getRegion() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
		return (String) cloud.get("region");
	}

	public static String getProject() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
		return (String) cloud.get("project");
	}

	public static String getZone() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
		return (String) cloud.get("zone");
	}

	private static String getCredentials() throws Exception {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
		String credentials = (String) cloud.get("credentials");

		FileInputStream fis = new FileInputStream(credentials);
		return IOUtils.toString(fis, "UTF-8");
	}

	/**
	 * 
	 * @return ordered map of addresses with tags or not
	 */
	private static TreeMap<String, Entity> getAddresses() throws ConfigError {
		TreeMap<String, Entity> result = new TreeMap<String, Entity>();

		ArrayList<ArrayList<String>> rawAddressesArray = null;
		TreeMap<String, ArrayList<String>> rawAddressesMap = null;
		try {
			rawAddressesArray = new ConvertObjectToArray<ArrayList<String>>(Config.obj.get("addresses")).getValue();
		} catch (Exception e) {
			try {
				rawAddressesMap = new ConvertMapToTreeMap<ArrayList<String>>(Config.obj.get("addresses")).getValue();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		int index = 0;
		if (rawAddressesArray != null) {
			for (var value : rawAddressesArray) {
				result.put(Integer.toString(index), new Entity(index, value));
				index++;
			}
		} else if (rawAddressesMap != null) {
			for (var value : rawAddressesMap.entrySet()) {
				result.put(value.getKey(), new Entity(index, value.getValue()));
				index++;
			}
		} else {
			throw new ConfigError("'addresses' can't be converted to array or map");
		}

		return result;
	}

	public static String formatContext() throws ConfigError {
		JSONObject jo = new JSONObject();
		jo.put("addresses", Config.getAddresses());
		jo.put("report_email", Config.getReportEmail());
		return jo.toString();
	}
}
