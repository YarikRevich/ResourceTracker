package com.resourcetracker;

import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.boot.CommandLineRunner;

// import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.*;
// import static java.util.concurrent.TimeUnit;

@Component
public class App implements CommandLineRunner {

	@Override
	public void run(String... args) {
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

		exec.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {

			}
		}, 0, 500, MILLISECONDS);
	}

}
