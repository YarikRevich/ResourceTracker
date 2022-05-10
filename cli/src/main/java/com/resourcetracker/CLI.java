package com.resourcetracker;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CLI {

	/**
	 * Runs application
	 *
	 * @param args command line arguments
	 *
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(CLI.class, args);
	}
}
