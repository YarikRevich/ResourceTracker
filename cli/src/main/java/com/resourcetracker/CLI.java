package com.resourcetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.resourcetracker.commands.Base;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

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
        exitCode = new CommandLine(base, factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(CLI.class, args)));
	}
}
