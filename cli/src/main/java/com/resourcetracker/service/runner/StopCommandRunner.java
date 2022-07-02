package com.resourcetracker.service.runner;

/**
 * Manages starting of each project
 * described in a configuration file
 */
public class StopCommandRunner implements Runnable {

	private ConfigEntity configEntity;

	public StopRunner(ConfigEntity configEntity){
			this.configEntity = configEntity;
		}

	/**
	 *
	 */
	@Override
	public void run() {
		if (stateService.isMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED)) {
			terraformService.setConfigEntity(configEntity);
			terraformService.stop();

			stateService.removeKafkaBootstrapServer();
			stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STOPED);
			numberOfStartedProjects++;
		}
	}
}
