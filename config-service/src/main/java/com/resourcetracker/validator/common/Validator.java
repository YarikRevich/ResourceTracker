package com.resourcetracker.validator.common;

import java.util.List;

import com.resourcetracker.entity.ConfigEntity;

public class Validator {
	public static void validateAll(List<ConfigEntity> parsedConfigFile, FieldValidator... fieldValidators){
		for (FieldValidator validator : fieldValidators){
			validator.validate(parsedConfigFile);
		}
	};
}
