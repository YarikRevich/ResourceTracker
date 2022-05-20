package com.resourcetracker.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import org.springframework.stereotype.Component;

import com.resourcetracker.config.Config;

@Component
@Command(name = "validate")
public class Validate implements Runnable {

	@Override
	public void run() {
		Config config = new Config();
		try {
			config.parse();
		} catch (ConfigException e) {
			System.out.println("Configuration file is not valid");
			e.printStackTrace();
		} finally {
			System.out.println("Configuration file is valid");
		}
	}
}
