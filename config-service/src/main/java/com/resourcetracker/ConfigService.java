package com.resourcetracker;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import com.resourcetracker.entity.ConfigEntity;

import com.resourcetracker.services.RequestSortService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * Service for processing configuration file
 *
 * @author YarikRevich
 */
@Service
@Import({RequestSortService.class})
public class ConfigService {
	private static final Logger logger = LogManager.getLogger(ConfigService.class);

	@Autowired
	RequestSortService requestSortService;

	private InputStream configFile;
	private List<ConfigEntity> parsedConfigFile;

	/**
	 * Opens YAML configuration file
	 */
	public ConfigService() {
		try {
			configFile = new FileInputStream(new File(Constants.CONFIG_FILE_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses opened configuration file
	 */

	public void parse(){
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
			.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		ObjectReader reader = mapper.reader().forType(new TypeReference<ConfigEntity>(){});
		try {
			parsedConfigFile = reader.<ConfigEntity>readValues(configFile).readAll();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		requestSortService.sort(parsedConfigFile);
	}

	public List<ConfigEntity> getParsedConfigFile() {
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
