package com.resourcetracker.services;

import java.util.Map;
import java.util.TreeMap;

import com.resourcetracker.tools.proc.Proc;
import com.resourcetracker.exception.ProcException;

import org.springframework.stereotype.Service;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Application API for executing external terraform
 * There are two core methods: start and stop.
 */
@Service
public class TerraformAPIService {
	final static Logger logger = LogManager.getLogger(TerraformAPIService.class);

	/**
	 * Path to terraform files source
	 */
	// private String path;

	private Proc proc;

	private TreeMap<String, String> envVars = new TreeMap<String, String>();
	private TreeMap<String, String> vars = new TreeMap<String, String>();

	public void setEnvVar(String key, String value) {
		this.envVars.put(key, value);
	}

	public void setVar(String key, String value) {
		this.vars.put(key, value);
	}

	public boolean start() {
		proc = new Proc();

		proc.setCommands("terraform", "init");
		try {
			proc.start();
		} catch (ProcException e) {
			e.printStackTrace();
		}

		proc.setCommands("terraform", "plan");

		vars.forEach((k, v) -> {
			StringBuilder command = new StringBuilder();
			command.append("-var").append(" ").append(k).append("=").append(v);
			proc.appendCommands(command.toString());
		});

		// if (!path.isEmpty()) {
		StringBuilder command = new StringBuilder();
		command.append("-chdir").append("=").append(".");
		proc.appendCommands(command.toString());
		// }

		proc.setEnvVars(this.envVars);
		try {
			proc.start();
		} catch (ProcException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	public boolean stop() {
		proc = new Proc();
		proc.setCommands("terraform", "destroy");
		try {
			proc.start();
		} catch (ProcException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}
