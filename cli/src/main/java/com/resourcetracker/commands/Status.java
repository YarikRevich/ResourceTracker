package com.resourcetracker.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Autowired;

@Component
@Command(name = "status")
public class Status implements Runnable{
	@Option(names = {"-p", "--project"}, description = "project name to start")
	private String project;

	@Autowired
	ConfigService configService;

	public void run() {

	}
}
