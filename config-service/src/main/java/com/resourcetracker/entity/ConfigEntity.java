package com.resourcetracker.entity;

import java.util.Optional;
import java.util.List;
import javax.validation.constraints.*;
import com.resourcetracker.tools.parsing.Frequency;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.resourcetracker.entity.TerraformRequestEntity;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.resourcetracker.ProcService;
import com.resourcetracker.exception.ProcException;
import com.resourcetracker.Constants;

/**
 * Model used for YAML configuration file parsing
 */
public class ConfigEntity {
	private boolean example;

	public void setExample(boolean example){
		if (example){
			ProcService procService = new ProcService();
			System.out.println("Remove 'example' field from configuration file to run ResourceTracker");
			procService.setCommands("kill", "-9", String.format("`cat %s`", Constants.PID_FILE_PATH));
			try {
				procService.start();
			} catch (ProcException e) {
				e.printStackTrace();
			}
		}
	}

	public class Project {
		public String name;
	}

	public Project project;

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	enum Method {
		POST("post"),
		GET("get"),
		PUT("put");

		private String method;

		private Method(String method) {
			this.method = method;
		}
	}

	// Represents request, which will be executed on a remote machine
	static class Request {
		public Optional<String> tag;
		public String url;
		public Optional<Method> method;
		public Optional<String> data;

		@Pattern(regexp = "^((^((([0-9]*)(s|m|h|d|w))))|(^once))$")
		public String frequency;
	}

	public List<Request> requests;

	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum Provider {
		GCP("gpc"),
		AWS("aws"),
		AZ("az");

		private String provider;

		private Provider(String provider) {
			this.provider = provider;
		}
	};

	public class Cloud {
		public Provider provider;

		@Pattern(regexp = "^(((~./)?)|((~/.)?)|((/?))?)([a-zA-Z/]*)$")
		public String credentials;

		public String profile;
		public Optional<String> region;
	};

	public Cloud cloud;

	class Mailing {
		@Email(message = "Report email should be valid")
		public String email;
	}

	public Mailing mailing;

	class Scheduler {
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
