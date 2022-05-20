package com.resourcetracker.config;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.Map;

import com.resourcetracker.cloud.Provider.Providers;
import com.resourcetracker.tools.utils.*;

import com.resourcetracker.tools.exceptions.ConfigException;
import com.resourcetracker.tools.exceptions.ValidationException;

import com.resourcetracker.tools.parsers.ReportFrequencyParser;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.resourcetracker.config.entity.ConfigEntity;

/**
 * Parses YAML config file
 *
 * @author YarikRevich
 */
public final class Config {
	final static Logger logger = LogManager.getLogger(Config.class);

	private static TreeMap<String, Object> obj;

	private InputStream configFile;
	public ConfigEntity parsedConfigFile;

	/**
	 * Opens YAML configuration file
	 */
	public Config() {
		try {
			configFile = new FileInputStream(new File(this.getConfigFilePath()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		logger.info("config file is read");
	}

	private String getConfigFilePath(){
		String os = System.getProperty("os.name");
		String configFilePath;
		if (os.contains("Windows")) {
			configFilePath = Paths.get("/usr/local/etc/resourcetracker.yaml").toString();
		} else if (os.contains("Linux") || os.contains("Mac OS X")) {
			configFilePath = Paths.get("/usr/local/etc/resourcetracker.yaml").toString();
		}
		return configFilePath;
	}

	/**
	 * Parses opened YAML configuration file
	 */
	public void parse() throws ConfigException {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		parsedConfigFile = mapper.readValue(configFile, ConfigEntity.class);
		logger.info("config file was parsed");
	}

	public ConfigEntity getParsedConfigFile(){
		return this.parsedConfigFile;
	}

	// /**
	//  * Validates YAML config file
	//  *
	//  * @return if YAML config file passes validation
	//  *
	//  */
	// public static boolean isValid() throws Exception {
	// 	Config.getAddresses();
	// 	Providers provider = Config.getCloudProvider();
	// 	switch (provider) {
	// 		case AWS:
	// 			Config.getProfile();
	// 		case GCP:
	// 			Config.getProject();
	// 			Config.getRegion();
	// 			Config.getZone();
	// 		case AZ:
	// 	}
	// 	Config.getCredentials();
	// 	Config.getReportEmail();
	// 	Config.getReportFrequency();

	// 	return true;
	// };

	// public static String getReportEmail() throws Exception {
	// 	@SuppressWarnings("unchecked")
	// 	TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("mailing");
	// 	String res = (String) cloud.get("email");

	// 	new EmailValidation(res, "Email is not valid");

	// 	return res;
	// }

	/**
	 * looks for cloud provider set in YAML config file
	 *
	 * @return cloud provider set in YAML config file
	 */
	// public static Providers getCloudProvider() throws ConfigException {
	// 	@SuppressWarnings("unchecked")
	// 	TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
	// 	String cloudProvider = (String) cloud.get("provider");

	// 	switch (cloudProvider) {
	// 		case "aws":
	// 			return Providers.AWS;
	// 		case "gcp":
	// 			return Providers.GCP;
	// 		case "az":
	// 			return Providers.AZ;
	// 	}
	// 	throw new ConfigException("Provider is not available");
	// }
	// private static String getCredentials() throws Exception {
	// 	@SuppressWarnings("unchecked")
	// 	TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
	// 	String credentials = (String) cloud.get("credentials");

	// 	FileInputStream fis = new FileInputStream(credentials);
	// 	return IOUtils.toString(fis, "UTF-8");
	// }

	// /**
	//  *
	//  * @return ordered map of addresses with tags or not
	//  */
	// private static TreeMap<String, Entity> getAddresses() throws ConfigException {
	// 	TreeMap<String, Entity> result = new TreeMap<String, Entity>();

	// 	ArrayList<ArrayList<String>> rawAddressesArray = null;
	// 	TreeMap<String, ArrayList<String>> rawAddressesMap = null;
	// 	try {
	// 		rawAddressesArray = new ConvertObjectToArray<ArrayList<String>>(Config.obj.get("addresses")).getValue();
	// 	} catch (Exception e) {
	// 		try {
	// 			rawAddressesMap = new ConvertMapToTreeMap<ArrayList<String>>(Config.obj.get("addresses")).getValue();
	// 		} catch (Exception e1) {
	// 			e1.printStackTrace();
	// 		}
	// 	}

	// 	int index = 0;
	// 	if (rawAddressesArray != null) {
	// 		for (var value : rawAddressesArray) {
	// 			result.put(Integer.toString(index), new Entity(index, value));
	// 			index++;
	// 		}
	// 	} else if (rawAddressesMap != null) {
	// 		for (var value : rawAddressesMap.entrySet()) {
	// 			result.put(value.getKey(), new Entity(index, value.getValue()));
	// 			index++;
	// 		}
	// 	} else {
	// 		throw new ConfigException("'addresses' can't be converted to array or map");
	// 	}

	// 	return result;
	// }

	// public static String formatContext() throws ConfigException {
		// JSONObject jo = new JSONObject();
		// jo.put("addresses", Config.getAddresses());
		// jo.put("report_email", Config.getReportEmail());
		// jo.put("report_frequency", Config.getReportFrequency());
		// return jo.toString();
	// }
}
