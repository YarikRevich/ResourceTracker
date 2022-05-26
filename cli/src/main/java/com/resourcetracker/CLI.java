package com.resourcetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.resourcetracker.commands.Base;

import com.resourcetracker.Constants;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CLI implements CommandLineRunner, ExitCodeGenerator{

	private IFactory factory;
    private Base base;
    private int exitCode;

    // constructor injection
    public CLI(IFactory factory, Base mailCommand) {
        this.factory = factory;
        this.base = base;
    }

    @Override
    public void run(String... args) {
       	CommandLine cmd = new CommandLine(base, factory);
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
