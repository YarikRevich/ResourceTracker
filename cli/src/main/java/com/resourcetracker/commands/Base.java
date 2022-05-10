package com.resourcetracker.commands;

import org.springframework.stereotype.Component;

@Component
@Command(
	subcommands = {
		Start.class,
		Stop.class,
		Status.class,
		Validate.class,
	},
	mixinStandardHelpOptions = true,
	description = "Cloud-based remote resource tracker",
	version = "1.0"
)
public class Base implements Runnable{
	@Override
	public void run(){
		CommandLine.usage(this, System.out);
	}
}
