package com.resourcetracker;

import com.resourcetracker.service.event.state.LocalState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GUI {
    public static void main(String[] args) {
        App app = new App();
        app.launch();
    }
}
