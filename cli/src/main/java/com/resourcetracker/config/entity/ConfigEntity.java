package com.resourcetracker.config.entity;

import java.util.Optional;
import java.util.ArrayList;
import javax.validation.constraints.*;

/**
 * Model used for YAML configuration file parsing
 */
public class ConfigEntity {
	private String example;

	class Project{
		private String name;
	}

	// Represents request, which will be executed on a remote machine
	public class Request {
		public Optional<String> tag;
		public String url;

		public enum Method{
			POST("post"),
			GET("get"),
			PUT("put");
		}

		public Optional<Method> method;

		public Optional<String> data;

		@Pattern(regexp="^((^((([0-9]*)(s|m|h|d|w))))|(^once))$")
		public String frequency;
	}

	public ArrayList<Request> requests;

	class Cloud {
		public enum Providers {
			GCP("gpc"),
			AWS("aws"),
			AZ("az");
		};

		public Providers provider;

		@Pattern(regexp="^(((~./)?)|((~/.)?)|((/?))?)([a-zA-Z/]*)$")
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
		@Pattern(regexp="^([0-9]*)(s|m|h|d|w)$")
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

	public TerraformRequest toTerraformRequest(){
		return new TerraformRequest(
			this.requests,
			this.mailing.email,
			this.scheduler.toInt());
	}
}
