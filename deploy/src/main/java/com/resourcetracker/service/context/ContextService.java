package com.resourcetracker.service.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.resourcetracker.Constants;
import com.resourcetracker.entity.ConfigEntity;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;


@Service
public class ContextService {
	private ConfigEntity parsedContext;

	public ContextService(){
		InputStream context = IOUtils.toInputStream(System.getenv(Constants.CONTEXT_ENV_VARIABLE_NAME), "UTF-8");
		ObjectMapper mapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		try {
			parsedContext = mapper.readValue(context, ConfigEntity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ConfigEntity getParsedContext(){
		return parsedContext;
	}
}
