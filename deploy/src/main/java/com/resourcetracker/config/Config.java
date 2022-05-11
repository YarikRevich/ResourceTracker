package com.resourcetracker.config;



public class Config {

	public Config(){

	}

	public parse(){
		ObjectMapper mapper = new ObjectMapper()
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
	}
}
