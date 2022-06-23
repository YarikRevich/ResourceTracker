package com.resourcetracker;

import com.resourcetracker.ConfigService;
import com.resourcetracker.entity.ConfigEntity;

import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.providers.AWS;
import com.resourcetracker.providers.AZ;
import com.resourcetracker.providers.GCP;
import com.resourcetracker.services.TerraformAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
@Import({TerraformAPIService.class})
public class TerraformService {

	@Autowired TerraformAPIService terraformAPIService;

	private IProvider chosenProvider;

	private ConfigEntity configEntity;
	public void setConfigEntity(ConfigEntity configEntity){
		this.configEntity = configEntity;
	}

	private void selectProvider(){
		switch (this.configEntity.getCloud().getProvider()) {
			case AWS:
				chosenProvider = new AWS();
				break;
			case GCP:
				chosenProvider = new GCP();
				break;
			case AZ:
				chosenProvider = new AZ();
		}

		terraformAPIService.setConfigEntity(configEntity);
		chosenProvider.setTerraformAPIService(terraformAPIService);
	}

	/**
	 * Starts remote execution on a chosen provider
	 * @return URL endpoint to the remote resources where execution is
	 * going
	 */
	public String start() {
		this.selectProvider();
		return chosenProvider.start();
	}

	public void stop() {
		this.selectProvider();
		chosenProvider.stop();
	}
}
