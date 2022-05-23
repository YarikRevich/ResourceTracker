package com.resourcetracker.commands;

import picocli.CommandLine.Command;

import com.resourcetracker.ConfigService;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Autowired;

@Component
@Command(name = "validate")
public class Validate implements Runnable {

	@Autowired
	ConfigService configService;

	@Override
	public void run() {
	}
}
