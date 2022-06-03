package com.resourcetracker;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
public class Deploy {
	/**
	 * Runs application
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
