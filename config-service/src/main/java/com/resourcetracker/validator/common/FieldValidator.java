package com.resourcetracker.validator.common;

import java.util.List;

import com.resourcetracker.entity.ConfigEntity;

public interface FieldValidator {
	public void validate(List<ConfigEntity> parsedConfigFile);
}
