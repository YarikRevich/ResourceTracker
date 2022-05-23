package com.resourcetracker.terraform.providers.common;

public interface IProvider {
	public void start();
	public void stop();
	public void setTerraformAPI();
}
