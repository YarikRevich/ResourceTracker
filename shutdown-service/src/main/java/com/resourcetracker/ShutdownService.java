package com.resourcetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ShutdownService {
	@Autowired
	ApplicationContext appContext;

	public void initiateShutdown(int returnCode){
		SpringApplication.exit(appContext, () -> returnCode);
		System.exit(returnCode);
	}
}

