package com.resourcetracker.validator;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resourcetracker.Constants;
import com.resourcetracker.ShutdownService;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.validator.common.FieldValidator;

public class ProviderFieldValidator implements FieldValidator {
	final static Logger logger = LogManager.getLogger(ProviderFieldValidator.class);

	@Autowired
	ShutdownService shutdownService;

	public void validate(List<ConfigEntity> parsedConfigFile) {
		for (ConfigEntity configEntity : parsedConfigFile) {
			String provider = configEntity.getCloud().getProvider().name();

			if (Arrays.asList(Constants.availableProviders).contains(provider)) {
				continue;
			} else if (Arrays.asList(Constants.providersInDevelopment).contains(provider)) {
				logger.fatal(String.format("'%s' provider is in development!", provider));
				shutdownService.initiateShutdown(1);
			} else {
				logger.fatal(String.format("'%s' provider is not available!", provider));
				shutdownService.initiateShutdown(1);
			}
		}
	}
}
