package com.resourcetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class Agent {
    public static void main(String[] args) {
      SpringApplication application = new SpringApplication(App.class);
      System.exit(SpringApplication.exit(application.run(args)));
    }
}
