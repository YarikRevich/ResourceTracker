package com.resourcetracker;

import com.resourcetracker.DAO.DAO;
import com.resourcetracker.controller.ExternalCommunicationController;
import com.resourcetracker.service.context.ContextService;
import com.resourcetracker.service.context.entity.ContextEntity;
import com.resourcetracker.service.requestrunner.RequestRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.*;

@Component
public class App implements CommandLineRunner {
	@Autowired
	ExternalCommunicationController externalCommunicationController;

	@Autowired
	ContextService contextService;

	@Autowired
	DAO dao;

	/**
	 * Parses upcoming context and iterating over all
	 * available requests runs each one in its own thread
	 * making performance better
	 * @param args incoming main method arguments
	 */
	@Override
	public synchronized void run(String... args) {
		ContextEntity parsedContext = contextService.getParsedContext();

		parsedContext.requests.forEach((final ContextEntity.Request request) -> {
			new Thread(new RequestRunner(request)).run();
		});
	}
}
