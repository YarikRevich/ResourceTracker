package com.resourcetracker.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.resourcetracker.Constants;
import com.resourcetracker.ProcService;
import com.resourcetracker.TerraformService;
import com.resourcetracker.entity.ConfigEntity;
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

	private String directory = "";

	public void setDirectory(String provider){
		StringBuilder rawDirectory = new StringBuilder();
		rawDirectory
			.append(Constants.TERRAFORM_CONFIG_FILES_PATH)
			.append("/")
			.append(provider);
		this.directory = rawDirectory.toString();
	}

	/**
	 * Path to terraform files source
	 */
	private ProcService procService;

	private ConfigEntity configEntity;

	public ConfigEntity getConfigEntity(){
		return this.configEntity;
	}

	private TreeMap<String, String> envVars = new TreeMap<String, String>();
	private TreeMap<String, String> vars = new TreeMap<String, String>();

	public TerraformAPIService(ConfigEntity configEntity){
		procService = new ProcService();

		this.configEntity = configEntity;
	}

	public void setEnvVar(String key, String value) {
		this.envVars.put(key, value);
	}

	public void setVar(String key, String value) {
		this.vars.put(key, value);
	}


	/**
	 * @param provider Path to terraform configuration files
	 * @return URL endpoint to the remote resources where execution is
	 */
	public <T> T apply() {
		procService.setCommands("terraform", "init");
		procService.setDirectory(this.directory);
		procService.setEnvVars(this.envVars);
		try {
			procService.start();
		} catch (ProcException e) {
			e.printStackTrace();
		}
		procService.clear();

		procService.setCommands("terraform", "apply", "-auto-approve");
		procService.appendCommands("-chdir", this.directory);
		procService.setEnvVars(this.envVars);
		vars.forEach((k, v) -> {
			StringBuilder command = new StringBuilder();
			command.append("-var").append(" ").append(k).append("=").append(v);
			procService.appendCommands(command.toString());
		});
		try {
			procService.start();
		} catch (ProcException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		procService.clear();

		procService.setCommands("terraform", "output", "-json");
		procService.appendCommands("-chdir", this.directory);
		procService.setEnvVars(this.envVars);
		try {
			procService.start();
		} catch (ProcException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		procService.clear();

		System.out.println(procService.getStdout());
		return procService.<T>getStdoutAsJSON();
	}

	public boolean destroy() {
		procService.setCommands("terraform", "destroy");
		try {
			procService.start();
		} catch (ProcException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	public String getContext(){
		return "";
	}

	public String getProvider(){
		return this.configEntity.getCloud().getProvider().toString().toLowerCase();
	}
}
