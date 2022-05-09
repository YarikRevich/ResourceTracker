package com.resourcetracker.config;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

import org.javatuples.Pair;

import com.resourcetracker.cloud.Provider.Providers;
import com.resourcetracker.tools.utils.*;

import com.resourcetracker.tools.exceptions.ConfigException;
import com.resourcetracker.tools.exceptions.ValidationException;

import com.resourcetracker.tools.parsers.ReportFrequencyParser;

import com.resourcetracker.tools.utils.validation.EmailValidation;
import com.resourcetracker.tools.utils.validation.ReportFrequencyValidation;

import java.time.LocalDate;
import com.resourcetracker.entities.Entity;

import org.json.JSONObject;

import org.javatuples.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.apache.commons.io.IOUtils;

// import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.resourcetracker.models.ConfigModel;

/**
 * Parses YAML config file
 *
 * @author YarikRevich
 *
 */
public final class Config {
	final static Logger logger = LogManager.getLogger(Loop.class);

	private static TreeMap<String, Object> obj;

	private InputStream configFile;
	private ConfigModel parsedConfigFile;

	/**
	 * Opens YAML configuration file
	 */
	public Config() {
		var os = System.getProperty("os.name");
		String configFilePath;
		if (os.contains("Windows")) {
			configFilePath = Paths.get("/usr/local/etc/resourcetracker.yaml").toString();
		} else if (os.contains("Linux") || os.contains("Mac OS X")) {
			configFilePath = Paths.get("/usr/local/etc/resourcetracker.yaml").toString();
		}

		try {
			configFile = new FileInputStream(new File(configFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses opened YAML configuration file
	 */
	public void parse() {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		parsedConfigFile = mapper.readValue(configFile, ConfigModel.class);
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

	public static String getReportEmail() throws Exception {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("mailing");
		String res = (String) cloud.get("email");

		new EmailValidation(res, "Email is not valid");

		return res;
	}

	/**
	 * @return Delay for report sending mechanism
	 */
	public static int getReportFrequency() throws ValidationException {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("settings");
		String res = (String) cloud.get("report_frequency");

		new ReportFrequencyValidation(res, "Report frequency is not valid");

		String number = res.substring(0, res.length() - 1);
		if (res.endsWith("s")) {
			return number * Frequency.secondInMilliseconds;
		} else if (res.endsWith("m")) {
			return number * Frequency.minuteInMilliseconds;
		} else if (res.endsWith("h")) {
			return number * Frequency.hourInMilliseconds;
		} else if (res.endsWith("d")) {
			return number * Frequency.dayInMilliseconds;
		} else if (res.endsWith("w")) {
			return number * Frequency.weekInMilliseconds;
		}
		return 0;
	}

	/**
	 * looks for cloud provider set in YAML config file
	 *
	 * @return cloud provider set in YAML config file
	 */
	public static Providers getCloudProvider() throws ConfigException {
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
		throw new ConfigException("Provider is not available");
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
	private static TreeMap<String, Entity> getAddresses() throws ConfigException {
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
			throw new ConfigException("'addresses' can't be converted to array or map");
		}

		return result;
	}

	public static String formatContext() throws ConfigException {
		JSONObject jo = new JSONObject();
		jo.put("addresses", Config.getAddresses());
		jo.put("report_email", Config.getReportEmail());
		jo.put("report_frequency", Config.getReportFrequency());
		return jo.toString();
	}
}
