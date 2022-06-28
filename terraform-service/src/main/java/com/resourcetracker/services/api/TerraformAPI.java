package com.resourcetracker.services.api;

import java.util.TreeMap;

import com.resourcetracker.Constants;
import com.resourcetracker.ProcService;
import com.resourcetracker.ShutdownService;
import com.resourcetracker.entity.ConfigEntity;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Application API for executing external terraform
 * There are two core methods: start and stop.
 */
@Component
@Import({ShutdownService.class})
public class TerraformAPI {
	final static Logger logger = LogManager.getLogger(TerraformAPI.class);

	@Autowired
	ShutdownService shutdownManager;

	private String directory = "";

	private void selectDirectory(){
		StringBuilder rawDirectory = new StringBuilder();
		rawDirectory
			.append(Constants.TERRAFORM_CONFIG_FILES_PATH)
			.append("/")
			.append(this.getConfigEntity().getCloud().getProviderAsLowerString());
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

	public void setConfigEntity(ConfigEntity configEntity){
		this.configEntity = configEntity;
	}

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

	private TreeMap<String, String> backendConfig = new TreeMap<String, String>();

	public void setBackendConfig(String key, String value) {
		this.backendConfig.put(key, value);
	}

	public TreeMap<String, String> getBackendConfig() {
		return backendConfig;
	}

	public TerraformAPI(){
		procService = new ProcService();
	}

	public void apply() {
		this.selectDirectory();

		procService
			.build()
			.setDirectory(this.getDirectory())
			.setCommand("terraform")
			.setEnvVars(this.getEnvVars())
			.setCommand("init")
			.setMapOfFlag("-backend-config", this.getBackendConfig())
			.setPositionalVar("-no-color")
			.setPositionalVar("-upgrade")
			.setPositionalVar("-reconfigure")
			.run();

		System.out.println(procService.getEnvVars());

		if (this.procService.isStderr()) {
			logger.fatal(this.procService.getStderr());
			shutdownManager.initiateShutdown(1);
		}

		procService
			.build()
			.setCommand("terraform")
			.setFlag("-chdir", this.getDirectory())
			.setEnvVars(this.getEnvVars())
			.setCommand("apply")
			.setMapOfFlag("-var", this.getVars())
			.setPositionalVar("-auto-approve")
			.setPositionalVar("-no-color")
			.run();

		System.out.println(procService.getEnvVars());

		if (this.procService.isStderr()) {
			logger.fatal(this.procService.getStderr());
			shutdownManager.initiateShutdown(1);
		}

		procService
			.build()
			.setCommand("terraform")
			.setFlag("-chdir", this.getDirectory())
			.setEnvVars(this.getEnvVars())
			.setCommand("output")
			.setPositionalVar("-json")
			.setPositionalVar("-no-color")
			.run();

		System.out.println(procService.getEnvVars());

		if (this.procService.isStderr()) {
			logger.fatal(this.procService.getStderr());
			shutdownManager.initiateShutdown(1);
		}

		logger.info(String.format("Project '%s' is run!", this.configEntity.getProject().getName()));
	}

	public void destroy() {
		this.selectDirectory();

		procService
			.build()
			.setCommand("terraform")
			.setFlag("-chdir", this.getDirectory())
			.setEnvVars(this.getEnvVars())
			.setCommand("destroy")
			.setPositionalVar("-no-color")
			.run();

		if (this.procService.isStderr()) {
			logger.fatal(this.procService.getStderr());
			shutdownManager.initiateShutdown(1);
		}

		logger.info(String.format("Project '%s' is destroyed!", this.configEntity.getProject().getName()));
	}

	public String getResult(){
		return procService.getStdout();
	}

	/**
	 * Wrappers for ConfigEntity
	 */

	// public String getContext(){
	// 	return "";
	// }

	// public  getCloud(){

	// }

	// public String getProvider(){
	// 	return this.configEntity.getCloud().getProvider().toString().toLowerCase();
	// }

	// public String getCredentials(){
	// 	return this.configEntity.getCloud().getCredentials();
	// }

	// public String getProfile() {
	// 	return this.configEntity.getCloud().getProfile();
	// };

	// public String getRegion() {
	// 	return this.configEntity.getCloud().getRegion();
	// };
}
