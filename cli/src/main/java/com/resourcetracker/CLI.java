package com.resourcetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.resourcetracker.Constants;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import org.springframework.stereotype.Component;
import java.util.concurrent.Callable;

import com.resourcetracker.command.TopCommand;

@SpringBootApplication()
@Import({ConfigService.class, StateService.class, TerraformService.class})
public class CLI implements CommandLineRunner, ExitCodeGenerator{
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

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(CLI.class);
		application.addListeners(new ApplicationPidFileWriter(Constants.PID_FILE_PATH));
		System.exit(SpringApplication.exit(application.run(args)));
	}
}
