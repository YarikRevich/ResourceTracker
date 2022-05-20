package com.resourcetracker.terraform.common;

import java.util.Map;
import java.util.TreeMap;

import com.resourcetracker.tools.proc.Proc;

/**
 * Application API for executing external terraform
 * There are two core methods: start and stop.
 */
public class TerraformAPI {
	/**
	 * Path to terraform files source
	 */
	private String path;

	private Proc proc;

	private TreeMap<String, String> envVars = new TreeMap<String, String>();
	private TreeMap<String, String> vars = new TreeMap<String, String>();

	public TerraformAPI(String path) {
		this.path = path;
	}

	public void setEnvVar(String key, String value) {
		this.envVars.put(key, value);
	}

	public void setVar(String key, String value) {
		this.vars.put(key, value);
	}

	public void start() {
		proc = new Proc();
		proc.setCommands("terraform", "init");
		proc.start();

		proc.setCommands("terraform", "plan");

		vars.forEach((k, v) -> {
			StringBuilder command = new StringBuilder();
			command.append("-var").append(" ").append(k).append("=").append(v);
			proc.appendCommands(command.toString());
		});

		if (!path.isEmpty()) {
			StringBuilder command = new StringBuilder();
			command.append("-chdir").append("=").append(this.path);
			proc.appendCommands(command.toString());
		}

		proc.setEnvVars(this.envVars);
		proc.start();
	}

	public void stop() {
		proc = new Proc();
		proc.setCommands("terraform", "destroy");
		proc.start();
	}

	public boolean isError() {
		return proc.isError();
	}
}
