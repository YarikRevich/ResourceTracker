package com.resourcetracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class Agent implements CommandLineRunner {
  @Autowired
  private ApplicationContext appContext;

  /**
   * Runs application
   * @param args command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(Agent.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    String[] beans = appContext.getBeanDefinitionNames();
    Arrays.sort(beans);
    for (String bean : beans) {
      System.out.println(bean);
    }

  }
}
