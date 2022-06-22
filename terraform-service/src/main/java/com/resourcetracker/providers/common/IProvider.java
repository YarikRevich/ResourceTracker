package com.resourcetracker.providers.common;

import com.resourcetracker.services.TerraformAPIService;

import java.net.URL;

public interface IProvider {

	/**
	 * Sets instance of TerraformAPIService
	 * @param terraformAPIService instance of TerraformAPIService
	 */
	public void setTerraformAPIService(TerraformAPIService terraformAPIService);

	/**
	 * Starts remote execution on a chosen provider
	 */
	public URL start();

	/**
	 * Stops remote execution on a chosen provider
	 */
	public void stop();
}
