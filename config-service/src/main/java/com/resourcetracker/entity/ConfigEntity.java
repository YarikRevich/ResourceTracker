package com.resourcetracker.entity;

import java.io.Serializable;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.resourcetracker.ProcService;
import com.resourcetracker.exception.ProcException;
import com.resourcetracker.exception.ConfigException;
import com.resourcetracker.Constants;
import com.resourcetracker.tools.frequency.Frequency;
import com.resourcetracker.services.DataFieldMatchService;
import com.resourcetracker.services.DataFieldMatchService.DataFieldType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Model used for YAML configuration file parsing
 */
public class ConfigEntity implements Serializable {
	ProcService procService;
	public ConfigEntity(){
		this.procService = new ProcService();
	}
	public boolean example;
	public void setExample(boolean example){
		if (example){
			 System.out.println("Remove 'example' field from configuration file to run ResourceTracker");
			this.procService.setCommands("cat", Constants.PID_FILE_PATH);
			try {
				this.procService.start();
			} catch (ProcException e) {
				e.printStackTrace();
			}
			 String pid = this.procService.getStdout();
			this.procService.setCommands("kill", "-9", pid);
			 try {
				 this.procService.start();
			 } catch (ProcException e) {
			 	e.printStackTrace();
			 }
		}
		this.example = example;
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

		@Pattern(regexp = "^((^(((\\d+)(s|m|h|d|w))))|(^once))$")
		public String frequency;

		public String setFrequency(String frequency){
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

		public String getCredentials() {
			return credentials;
		}

		public String getProfile() {
			return profile;
		}

		public String getRegion() {
			return region;
		}

		public Provider getProvider(){
			return this.provider;
		}
	};

	public Cloud cloud;

	public Cloud getCloud(){
		return this.cloud;
	}

	public static class Reporter{
		@Pattern(regexp = "^((^(((\\d+)(s|m|h|d|w))))|(^once))$")
		public String frequency;

		public String setFrequency(String frequency){
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

	public String toYAML() {
		ObjectWriter ow = new ObjectMapper(new YAMLFactory()).writer().withDefaultPrettyPrinter();
		String result = "";
		try{
			result = ow.writeValueAsString(this);
		} catch (JsonProcessingException e){
			e.printStackTrace();
		}
		return result;
	}
}
