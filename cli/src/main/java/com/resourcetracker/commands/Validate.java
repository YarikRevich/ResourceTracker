package com.resourcetracker.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import org.springframework.stereotype.Component;

@Component
@Command(name = "validate")
public class Validate implements Runnable {

	@Override
	public void run() {

	}
}
