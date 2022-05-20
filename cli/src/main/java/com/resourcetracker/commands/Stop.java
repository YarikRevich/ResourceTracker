package com.resourcetracker.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import org.springframework.stereotype.Component;

@Component
@Command(name = "stop")
public class Stop implements Runnable{
	@Option(names = {"-p", "--project"}, description = "project name to start")
	private String project;

	public void run() {

	}
}
