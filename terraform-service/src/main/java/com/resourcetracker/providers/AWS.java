package com.resourcetracker.providers;

import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.AwsVpcConfiguration;
import com.amazonaws.services.ecs.model.NetworkConfiguration;
import com.amazonaws.services.ecs.model.RunTaskRequest;
import com.amazonaws.services.ecs.model.RunTaskResult;
import com.resourcetracker.Constants;
import com.resourcetracker.entity.AWSOutput;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.services.TerraformAPIService;

import com.resourcetracker.wrapper.ECSTaskRunner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

//import software.amazon.awssdk.services.ecs;

// import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.ObjectInputFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

/**
 * AWS implementation of Provider
 *
 * @author YarikRevich
 *
 */
public class AWS implements IProvider {
	final static Logger logger = LogManager.getLogger(AWS.class);

	TerraformAPIService terraformAPIService;

	public void setTerraformAPIService(TerraformAPIService terraformAPIService) {
		this.terraformAPIService = terraformAPIService;
	}

	public URL start() {
		ConfigEntity configEntity = terraformAPIService.getConfigEntity();
		ConfigEntity.Cloud cloud = configEntity.getCloud();

		terraformAPIService.setEnvVar("AWS_SHARED_CREDENTIALS_FILE", cloud.getCredentials());
		terraformAPIService.setEnvVar("AWS_PROFILE", cloud.getProfile());
		terraformAPIService.setEnvVar("AWS_REGION", cloud.getRegion());

		terraformAPIService.setVar("context", terraformAPIService.getContext());

		terraformAPIService.setDirectory(terraformAPIService.getProvider());
		AWSOutput output = terraformAPIService.<AWSOutput>apply();
		ECSTaskRunner ecsTaskRunner = new ECSTaskRunner(output);
		return ecsTaskRunner.run();
	}

	public void stop() {
		if (terraformAPIService.destroy()){
			logger.error(String.format("Provider %s is stoped", this.getClass().toString()));
		}else{
			logger.error(String.format("Provider %s is not stoped", this.getClass().toString()));
		}
	}
}
