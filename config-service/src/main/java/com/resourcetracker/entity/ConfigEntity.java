package com.resourcetracker.entity;

import java.util.Optional;
import java.util.ArrayList;
import javax.validation.constraints.*;
import com.resourcetracker.tools.parsing.Frequency;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.resourcetracker.entity.TerraformRequestEntity;

/**
 * Model used for YAML configuration file parsing
 */
public class ConfigEntity {
	private String example;

	public class Project {
		private String name;
	}

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
	class Request {
		public Optional<String> tag;
		public String url;
		public Optional<Method> method;
		public Optional<String> data;

		@Pattern(regexp = "^((^((([0-9]*)(s|m|h|d|w))))|(^once))$")
		public String frequency;
	}

	public ArrayList<Request> requests;

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

	class Cloud {
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
		return new TerraformRequestEntity(
				this.requests,
				this.mailing.email,
				this.scheduler.toInt());
	}
}
