package com.resourcetracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.services.RequestSortService;
import com.resourcetracker.validator.ExampleFieldValidator;
import com.resourcetracker.validator.ProviderFieldValidator;
import com.resourcetracker.validator.common.Validator;

/**
 * Service for processing configuration file
 *
 * @author YarikRevich
 */
@Service
@Import({RequestSortService.class, ExampleFieldValidator.class})
public class ConfigService {
	private static final Logger logger = LogManager.getLogger(ConfigService.class);

	@Autowired
	RequestSortService requestSortService;

	@Autowired
	ExampleFieldValidator exampleFieldValidator;

	@Autowired
	ProviderFieldValidator providerFieldValidator;

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
	 * Converts json source to InputStream
	 * @param jsonSrc
	 */
	public ConfigService(String jsonSrc){
		configFile = IOUtils.toInputStream(jsonSrc, "UTF-8");
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

		Validator.validateAll(parsedConfigFile, exampleFieldValidator, providerFieldValidator);

		requestSortService.sort(parsedConfigFile);
	}

	public List<ConfigEntity> getParsedConfigFile() {
		return this.parsedConfigFile;
	}
}
