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
		var config = new Config();
		config.parse();

		if (config.isValid()) {

		} else {
			System.out.println("");
		}
	}
}
