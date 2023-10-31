package com.resourcetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class CLI{
  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(App.class);
    System.exit(SpringApplication.exit(application.run(args)));
  }
}
