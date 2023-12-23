package com.resourcetracker;

import com.resourcetracker.service.client.command.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CLI {
  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(App.class);
    System.exit(SpringApplication.exit(application.run(args)));
  }
}
