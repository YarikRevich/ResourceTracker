package com.resourcetracker.validator;

import com.resourcetracker.ShutdownService;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.validator.common.FieldValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExampleFieldValidator implements FieldValidator {
	final static Logger logger = LogManager.getLogger(ExampleFieldValidator.class);

	@Autowired
	ShutdownService shutdownService;

	public void validate(List<ConfigEntity> parsedConfigFile){
		for (ConfigEntity configEntity : parsedConfigFile) {
			if (configEntity.isExample()){
				shutdownService.initiateShutdown(1);
			}
		}
	}
}
