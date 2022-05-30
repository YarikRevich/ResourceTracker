package com.resourcetracker;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.nio.file.Paths;

// import com.resourcetracker.cloud.Provider.Providers;
// import com.resourcetracker.tools.utils.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.resourcetracker.exception.ConfigException;
import com.resourcetracker.exception.ValidationException;

// import com.resourcetracker.tools.parsers.ReportFrequencyParser;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import com.resourcetracker.entity.ConfigEntity;



import com.resourcetracker.Constants;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.stereotype.Service;

/**
 * Service for processing configuration file
 *
 * @author YarikRevich
 */
@Service
public final class ConfigService {
	private static final Logger logger = LogManager.getLogger(ConfigService.class);

	private InputStream configFile;
	private ConfigEntity parsedConfigFile = null;

	/**
	 * Opens YAML configuration file
	 */
	public ConfigService() {
		try {
			configFile = new FileInputStream(new File(Constants.CONFIG_FILE_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		try {
			parsedConfigFile = mapper.readValue(configFile, ConfigEntity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(configFile.toString());
	}

	public ConfigEntity getParsedConfigFile() {
		return this.parsedConfigFile;
	}

	// /**
	// * Validates YAML config file
	// *
	// * @return if YAML config file passes validation
	// *
	// */
	// public static boolean isValid() throws Exception {
	// Config.getAddresses();
	// Providers provider = Config.getCloudProvider();
	// switch (provider) {
	// case AWS:
	// Config.getProfile();
	// case GCP:
	// Config.getProject();
	// Config.getRegion();
	// Config.getZone();
	// case AZ:
	// }
	// Config.getCredentials();
	// Config.getReportEmail();
	// Config.getReportFrequency();

	// return true;
	// };

	// public static String getReportEmail() throws Exception {
	// @SuppressWarnings("unchecked")
	// TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("mailing");
	// String res = (String) cloud.get("email");

	// new EmailValidation(res, "Email is not valid");

	// return res;
	// }

	/**
	 * looks for cloud provider set in YAML config file
	 *
	 * @return cloud provider set in YAML config file
	 */
	// public static Providers getCloudProvider() throws ConfigException {
	// @SuppressWarnings("unchecked")
	// TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
	// String cloudProvider = (String) cloud.get("provider");

	// switch (cloudProvider) {
	// case "aws":
	// return Providers.AWS;
	// case "gcp":
	// return Providers.GCP;
	// case "az":
	// return Providers.AZ;
	// }
	// throw new ConfigException("Provider is not available");
	// }
	// private static String getCredentials() throws Exception {
	// @SuppressWarnings("unchecked")
	// TreeMap<String, Object> cloud = (TreeMap<String, Object>) obj.get("cloud");
	// String credentials = (String) cloud.get("credentials");

	// FileInputStream fis = new FileInputStream(credentials);
	// return IOUtils.toString(fis, "UTF-8");
	// }

	// /**
	// *
	// * @return ordered map of addresses with tags or not
	// */

	// int index = 0;
	// if (rawAddressesArray != null) {
	// for (var value : rawAddressesArray) {
	// result.put(Integer.toString(index), new Entity(index, value));
	// index++;
	// }
	// } else if (rawAddressesMap != null) {
	// for (var value : rawAddressesMap.entrySet()) {
	// result.put(value.getKey(), new Entity(index, value.getValue()));
	// index++;
	// }
	// } else {
	// throw new ConfigException("'addresses' can't be converted to array or map");
	// }

	// return result;
	// }
}
