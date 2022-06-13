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

	public String getDirectory() {
		return directory;
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

	public void setEnvVar(String key, String value) {
		this.envVars.put(key, value);
	}
	public TreeMap<String, String> getEnvVars() {
		return envVars;
	}
	private TreeMap<String, String> vars = new TreeMap<String, String>();

	public void setVar(String key, String value) {
		this.vars.put(key, value);
	}

	public TreeMap<String, String> getVars() {
		return vars;
	}

	public TerraformAPIService(ConfigEntity configEntity){
		procService = new ProcService();

		this.configEntity = configEntity;
	}

	/**
	 * @param provider Path to terraform configuration files
	 * @return URL endpoint to the remote resources where execution is
	 */
	public <T> T apply() {
		procService
			.build()
			.setDirectory(this.getDirectory())
			.setCommand("terraform")
			.setEnvVars(this.getEnvVars())
			.setCommand("init")
			.run();

		procService
			.build()
			.setCommand("terraform")
			.setFlag("-chdir", this.getDirectory())
			.setMapOfFlag("-var", this.getVars())
			.setEnvVars(this.getEnvVars())
			.setCommand("apply")
			.setPositionalVar("-auto-approve")
			.run();

		procService
			.build()
			.setCommand("terraform")
			.setFlag("-chdir", this.getDirectory())
			.setEnvVars(this.getEnvVars())
			.setCommand("output")
			.setPositionalVar("-json")
			.run();

		System.out.println(procService.getStdout());
//		return procService.<T>getStdoutAsJSON();
		return null;
	}

	public void destroy() {
		procService
			.build()
			.setCommand("terraform")
			.setCommand("destroy")
			.run();
	}

	public String getContext(){
		return "";
	}

	public String getProvider(){
		return this.configEntity.getCloud().getProvider().toString().toLowerCase();
	}
}
