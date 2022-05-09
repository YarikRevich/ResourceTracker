package com.resourcetracker.models;

/**
 * Model used for YAML configuration file parsing
 */
public class ConfigModel {
	private String project;

	class Cloud {
		public enum Providers {
			GCP("gpc"),
			AWS("aws"),
			AZ("az");
		};

		public Providers provider;
		public String credentials;
		public String profile;
		public Optional<String> region;
	};

	public Cloud cloud;

	class Mailing {
		public String email;
	}

	public Mailing mailing;

	class Scheduler {
		public String frequency;
	}

	public Scheduler scheduler;
}
