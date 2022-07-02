package com.resourcetracker.service.runner;

/**
 * Manages starting of each project
 * described in a configuration file
 */
public class StartCommandRunner implements Runnable {
	private ConfigEntity configEntity;

	public StartRunner(ConfigEntity configEntity){
			this.configEntity = configEntity;
		}

	/**
	 *
	 */
	@Override
	public void run() {
		if (stateService.isMode(configEntity.getProject().getName(), StateEntity.Mode.STOPED)) {
			terraformService.setConfigEntity(configEntity);
			terraformService.start();

			stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED);
			numberOfStartedProjects++;
		}
	}
}
