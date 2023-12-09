package com.resourcetracker;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GUI {
  public static void main(String[] args) {
    App app = new App();
    app.launch();
  }
}
