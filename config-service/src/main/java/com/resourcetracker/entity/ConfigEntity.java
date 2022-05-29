package com.resourcetracker.entity;

import java.io.Serializable;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.Optional;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;

import com.resourcetracker.ProcService;
import com.resourcetracker.exception.ProcException;
import com.resourcetracker.exception.ConfigException;
import com.resourcetracker.exception.DataFieldException;
import com.resourcetracker.Constants;
import com.resourcetracker.tools.parsing.Frequency;
import com.resourcetracker.services.DataFieldMatchService;
import com.resourcetracker.services.DataFieldMatchService.DataFieldType;
import com.resourcetracker.entity.TerraformRequestEntity;

import jakarta.inject.Inject;
import io.micronaut.http.client.annotation.*;
import io.micronaut.http.client.HttpClient;
import static io.micronaut.http.HttpRequest.*;

/**
 * Model used for YAML configuration file parsing
 */
public class ConfigEntity implements Serializable {
	@Client("http://localhost:10075")
    @Inject RxHttpClient client; // example injected service

	private boolean example;

	public void setExample(boolean example){
		if (example){
			HttpClient.POST("/shutdown");
			// ProcService procService = new ProcService();
			// System.out.println("Remove 'example' field from configuration file to run ResourceTracker");
			// procService.setCommands("cat", Constants.PID_FILE_PATH);
			// String pid = procService.getStdout();
			// procService.setCommands("kill", pid);
			// System.out.println(procService.getStdout());
			// System.out.println(procService.getStderr());
			// try {
			// 	procService.start();
			// } catch (ProcException e) {
			// 	e.printStackTrace();
			// }
		}
	}

	static class Project {
		public String name;
	}

	public Project project;

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum Method {
		POST("post"),
		GET("get"),
		PUT("put");

		public String method;

		private Method(String method) {
			this.method = method;
		}
	}



	// Represents request, which will be executed on a remote machine
	public static class Request {
		public String tag;
		public String url;
		public Method method;
		public String data;

		public void setData(String data) throws ConfigException {
			DataFieldType match = DataFieldMatchService.matches(data);
			switch (match){
				case FILE:
					File file = new File(data);
					if (!file.exists()){
						throw new ConfigException();
					}
					BufferedReader reader = null;
					try{
						reader = new BufferedReader(new FileReader(file));
					} catch (IOException e){
						e.printStackTrace();
					}
					try{
						this.data = reader.readLine();
					} catch (IOException e){
						e.printStackTrace();
					}
				case SCRIPT:
					this.data = data;
			}
		}

		@Pattern(regexp = "^((^((([0-9]*)(s|m|h|d|w))))|(^once))$")
		public String frequency;
	}

	public List<Request> requests;

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public static enum Provider {
		@JsonProperty("gcp")
		GCP,
		@JsonProperty("aws")
		AWS,
		@JsonProperty("az")
		AZ;
	};

	public static class Cloud {
		public Provider provider;

		@Pattern(regexp = "^(((~./)?)|((~/.)?)|((/?))?)([a-zA-Z/]*)((\\.([a-z]+))?)$")
		public String credentials;

		public String profile;
		public String region;
	};

	public Cloud cloud;

	static class Mailing {
		@Email(message = "Report email should be valid")
		public String email;
	}

	public Mailing mailing;

	static class Scheduler {
		@Pattern(regexp = "^([0-9]*)(s|m|h|d|w)$")
		public String frequency;

		/**
		 * Parses raw string frequency and converts it to int
		 *
		 * @return int representation of frequency
		 */
		public int toInt() {
			int number = Integer.parseInt(frequency.substring(0, frequency.length() - 1));
			if (frequency.endsWith("s")) {
				return number * Frequency.secondInMilliseconds;
			} else if (frequency.endsWith("m")) {
				return number * Frequency.minuteInMilliseconds;
			} else if (frequency.endsWith("h")) {
				return number * Frequency.hourInMilliseconds;
			} else if (frequency.endsWith("d")) {
				return number * Frequency.dayInMilliseconds;
			} else if (frequency.endsWith("w")) {
				return number * Frequency.weekInMilliseconds;
			}
			return 0;
		}
	}

	public Scheduler scheduler;

	public TerraformRequestEntity toTerraformRequestEntity() {
		// return new TerraformRequestEntity(
		// 		this.requests,
		// 		this.mailing.email,
		// 		this.scheduler.toInt());
		return null;
	}
}
