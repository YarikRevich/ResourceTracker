package com.resourcetracker.validator;

import com.resourcetracker.ShutdownService;
import com.resourcetracker.entity.ConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExampleFieldValidator {
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
