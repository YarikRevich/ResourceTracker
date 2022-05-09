package com.resourcetracker.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import org.springframework.stereotype.Component;

@Component
@Command(name = "start")
public class Start implements Runnable{
	@Option(names = {"-p", "--project"}, description = "project name to start", required = true)
	private String project;

	@Option(names = {"-r", "--request"}, description = "request to start if mode is direct")
	private String request;

	public void run() {

	}
}
