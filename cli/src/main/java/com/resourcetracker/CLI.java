package com.resourcetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.resourcetracker.Constants;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import org.springframework.stereotype.Component;
import java.util.concurrent.Callable;

import com.resourcetracker.command.TopCommand;

@SpringBootApplication
public class CLI implements CommandLineRunner, ExitCodeGenerator{
    private int exitCode;

    @Override
    public void run(String... args) {
		CommandLine cmd = new CommandLine(new TopCommand());
		cmd.setUnmatchedOptionsAllowedAsOptionParameters(true);
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
