package com.resourcetracker.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.resourcetracker.ProcService;
import com.resourcetracker.exception.ProcException;

import org.springframework.stereotype.Service;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;

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
	@Autowired
	private ProcService procService;

	private TreeMap<String, String> envVars = new TreeMap<String, String>();
	private TreeMap<String, String> vars = new TreeMap<String, String>();

	public void setEnvVar(String key, String value) {
		this.envVars.put(key, value);
	}

	public void setVar(String key, String value) {
		this.vars.put(key, value);
	}

	/**
	 *
	 * @param pathToConfiguration Optional path to terraform configuration files conserning special provider
	 * @return URL endpoint to the remote resources where execution is
	 */
	public URL start(Optional<String> pathToConfiguration) {
		procService.setCommands("terraform", "init");
		try {
			procService.start();
		} catch (ProcException e) {
			e.printStackTrace();
		}

		procService.setCommands("terraform", "plan");

		vars.forEach((k, v) -> {
			StringBuilder command = new StringBuilder();
			command.append("-var").append(" ").append(k).append("=").append(v);
			procService.appendCommands(command.toString());
		});

		StringBuilder command = new StringBuilder();
		command.append("-chdir").append("=").append(ClassLoader.getSystemResource("tf").getPath()).append(pathToConfiguration.isPresent() ? pathToConfiguration.get() : ".");
		procService.appendCommands(command.toString());

		procService.setEnvVars(this.envVars);
		try {
			procService.start();
		} catch (ProcException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		URL publicEndpoint = null;
		try {
			publicEndpoint = new URL("");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return publicEndpoint;
	}

	public boolean stop() {
		procService.setCommands("terraform", "destroy");
		try {
			procService.start();
		} catch (ProcException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}
