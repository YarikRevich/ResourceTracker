package com.resourcetracker.entity;

import java.util.ArrayList;
import org.json.JSONObject;

import com.resourcetracker.entity.ConfigEntity;

/**
 * @author YarikRevich
 *
*/
public class TerraformRequestEntity {
	private ArrayList<ConfigEntity.Request> requests;
	private String email;
	private int frequency;

	public TerraformRequestEntity(ArrayList<ConfigEntity.Request> requests, String email, int frequency){
		this.requests = requests;
		this.email = email;
		this.frequency = frequency;
	}

	public String toJSON(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("requests", this.requests);
		jsonObject.put("email", this.email);
		jsonObject.put("frequency", this.frequency);
		return jsonObject.toString();
	}
}

// public record Entity(int index, ArrayList<String> schemas){};
