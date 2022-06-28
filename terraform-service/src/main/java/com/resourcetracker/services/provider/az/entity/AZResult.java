package com.resourcetracker.services.provider.az.entity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class AZResult implements Serializable {
	public static AZResult fromJson(String src){
		ObjectMapper mapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(src, new TypeReference<>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
