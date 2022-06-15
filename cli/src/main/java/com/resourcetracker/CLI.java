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

@SpringBootApplication
public class CLI{
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(App.class);
		System.exit(SpringApplication.exit(application.run(args)));
	}
}
