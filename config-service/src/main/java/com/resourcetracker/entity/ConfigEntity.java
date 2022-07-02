package com.resourcetracker.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.resourcetracker.ProcService;
import com.resourcetracker.exception.ConfigException;
import com.resourcetracker.services.DataFieldMatchService;
import com.resourcetracker.services.DataFieldMatchService.DataFieldType;
import com.resourcetracker.tools.frequency.Frequency;

/**
 * Model used for YAML configuration file parsing
 */
public class ConfigEntity implements Serializable {
	ProcService procService;

	public ConfigEntity() {
		this.procService = new ProcService();
	}

	public boolean example;

	@JsonGetter
	public boolean isExample() {
		return example;
	}

	public static class Project {
		public String name;

		public String getName() {
			return name;
		}
	}

	public Project project;

	public Project getProject() {
		return project;
	}

	// Represents request, which will be executed on a remote machine
	public static class Request {
		public String tag;

		public String getTag() {
			return tag;
		}

		public String data;

		public String getData() {
			return data;
		}

		public void setData(String data) throws ConfigException {
			DataFieldType match = DataFieldMatchService.matches(data);
			switch (match) {
				case FILE:
					File file = new File(data);
					if (!file.exists()) {
						throw new ConfigException();
					}
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(new FileReader(file));
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						this.data = reader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				case SCRIPT:
					this.data = data;
			}
		}

		@Pattern(regexp = "^((^(((\\d+)(s|m|h|d|w))))|(^once))$")
		public String frequency;

		public String setFrequency(String frequency) {
			int number = Integer.parseInt(frequency.substring(0, frequency.length() - 1));
			if (frequency.endsWith("s")) {
				return String.format("%d", number * Frequency.secondInMilliseconds);
			} else if (frequency.endsWith("m")) {
				return String.format("%d", number * Frequency.minuteInMilliseconds);
			} else if (frequency.endsWith("h")) {
				return String.format("%d", number * Frequency.hourInMilliseconds);
			} else if (frequency.endsWith("d")) {
				return String.format("%d", number * Frequency.dayInMilliseconds);
			} else if (frequency.endsWith("w")) {
				return String.format("%d", number * Frequency.weekInMilliseconds);
			}
			return "";
		}

		public String getFrequency() {
			return frequency;
		}

		@Email(message = "Report email should be valid")
		public String email;

		public String getEmail() {
			return email;
		}
	}

	public List<Request> requests;

	public List<Request> getRequests() {
		return requests;
	}

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

		public String clientId;
		public String clientSecret;
		public String subscriptionId;
		public String tenantId;

		public String getClientId() {
			return this.clientId;
		}

		public String getClientSecret() {
			return this.clientSecret;
		}

		public String getSubscriptionId() {
			return this.subscriptionId;
		}

		public String getTenantId() {
			return this.tenantId;
		}

		public String getCredentials() {
			return credentials;
		}

		public String getProfile() {
			return profile;
		}

		public String getRegion() {
			return region;
		}

		public Provider getProvider() {
			return this.provider;
		}

		public String getProviderAsLowerString() {
			return this.provider.toString().toLowerCase();
		}
	};

	public Cloud cloud;

	public Cloud getCloud() {
		return this.cloud;
	}

	public static class Reporter {
		@Pattern(regexp = "^((^(((\\d+)(s|m|h|d|w))))|(^once))$")
		public String frequency;

		public String setFrequency(String frequency) {
			if (frequency == "once") {
				return frequency;
			}

			int number = Integer.parseInt(frequency.substring(0, frequency.length() - 1));
			if (frequency.endsWith("s")) {
				return String.format("%d", number * Frequency.secondInMilliseconds);
			} else if (frequency.endsWith("m")) {
				return String.format("%d", number * Frequency.minuteInMilliseconds);
			} else if (frequency.endsWith("h")) {
				return String.format("%d", number * Frequency.hourInMilliseconds);
			} else if (frequency.endsWith("d")) {
				return String.format("%d", number * Frequency.dayInMilliseconds);
			} else if (frequency.endsWith("w")) {
				return String.format("%d", number * Frequency.weekInMilliseconds);
			}
			return "";
		}

		public String getFrequency() {
			return frequency;
		}
	}

	public Reporter reporter;

	public Reporter getReporter() {
		return reporter;
	}

	public void setReporter(Reporter reporter) {
		this.reporter = reporter;
	}

	/**
	 * Formats raw data to context passed to
	 * TerraformAPI and then formats it to JSON string
	 */
	public String toJSON() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String result = "";
		try {
			result = ow.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
