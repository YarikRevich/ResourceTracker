package com.resourcetracker.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import org.springframework.stereotype.Component;
import java.util.concurrent.Callable;

@Component
@Command(
	// subcommands = {
	// 	Start.class,
	// 	Stop.class,
	// 	Status.class,
	// 	Validate.class
	// },
	mixinStandardHelpOptions = true,
	description = "Cloud-based remote resource tracker",
	version = "1.0"
)
public class Base implements Callable<Integer>{
	// @Override
	// public void run(){
	// 	// CommandLine.usage(this, System.out);
	// }

	public Integer call() throws Exception{
		return 0;
	}
}
