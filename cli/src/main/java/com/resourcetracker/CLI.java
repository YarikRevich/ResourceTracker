package com.resourcetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CLI{
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(App.class);
		System.exit(SpringApplication.exit(application.run(args)));
	}

//	public void initiateShutdown(int returnCode){
//		SpringApplication.exit(appContext, () -> returnCode);
//		System.exit(returnCode);
//	}
}
