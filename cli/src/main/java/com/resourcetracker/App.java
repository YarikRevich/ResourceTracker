package com.resourcetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import com.resourcetracker.command.TopCommand;
import com.resourcetracker.service.KafkaConsumerWrapper;

import picocli.CommandLine;

@Component
@Configuration
@Import({
	TopCommand.class,
	ConfigService.class,
	StateService.class,
	TerraformService.class,
	KafkaConsumerWrapper.class})
public class App implements CommandLineRunner, ExitCodeGenerator {
	int exitCode;
	@Autowired
	TopCommand topCommand;

	@Override
	public void run(String... args) {
		CommandLine cmd = new CommandLine(topCommand);
		exitCode = cmd.execute(args);
	}
	@Override
	public int getExitCode() {
		return exitCode;
	}
}
