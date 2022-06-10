package com.resourcetracker;

import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ProcException;
import com.resourcetracker.service.context.ContextService;
import com.resourcetracker.service.formatter.OutputBuilder;
import com.resourcetracker.service.formatter.OutputFormatter;
import com.resourcetracker.service.reporter.Reporter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
public class App implements CommandLineRunner {
	@Autowired
	ProcService procService;

	@Autowired
	ContextService contextService;

	@Autowired
	Reporter reporter;

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * Configuration for kafka instance
	 */
	@Configuration
	class KafkaConfiguration{
		@Bean
		public NewTopic topicStatus(){
			return new NewTopic(Constants.KAFKA_STATUS_TOPIC, 1, (short) 1);
		}
	}

	/**
	 * Runner for requests set in context
	 */
	class RequestRunner implements Runnable{

		class ReportRunner implements Runnable{
			private int frequency;
			private String email;

			public ReportRunner(String email, int frequency){
				this.frequency = frequency;
				this.email = email;
			}

			@Override
			public void run() {
				ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
				exec.scheduleAtFixedRate(() -> {
					//TODO: send report to set email address
				}, 0, this.frequency, MILLISECONDS);
			}
		}

		ConfigEntity.Request request;

		public RequestRunner(ConfigEntity.Request request){
			this.request = request;
		}

		@Override
		public synchronized void run() {
			if (!this.request.getEmail().isEmpty()){
				new Thread(new ReportRunner(
					this.request.getEmail(), Integer.parseInt(this.request.getFrequency()))).run();
			}
//			kafkaTemplate.
			ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);
			exec.scheduleAtFixedRate(() -> {
				procService.setCommandsWithEval(this.request.getData());
				try {
					procService.start();
				} catch (ProcException e) {
					throw new RuntimeException(e);
				}

				OutputFormatter outputFormatter = new OutputFormatter();
				String formattedOutput = outputFormatter.formatStatusMessage(
					new OutputBuilder(), new OutputFormatter.StatusMessageData(
						request.getTag(), procService.getStdout()));
				kafkaTemplate.send(Constants.KAFKA_STATUS_TOPIC, formattedOutput);
			}, 0, Integer.parseInt(this.request.getFrequency()), MILLISECONDS);
		}
	}

	/**
	 * Runs each request set in context one after another
	 */
	@Override
	public synchronized void run(String... args) {
		var parsedContext = contextService.getParsedContext();

		parsedContext.requests.forEach((final ConfigEntity.Request request) -> {
			new Thread(new RequestRunner(request)).run();
		});
	}
}
