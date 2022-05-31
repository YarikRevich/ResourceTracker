package com.resourcetracker.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author YarikRevich
 *
*/
public class TerraformRequestEntity implements Serializable {
	private ArrayList<ConfigEntity.Request> requests;
	private String email;
	private int frequency;

	public TerraformRequestEntity(ArrayList<ConfigEntity.Request> requests, String email, int frequency){
		this.requests = requests;
		this.email = email;
		this.frequency = frequency;
	}

	public String toJSON(){
		JSONArray requestsJSONArray = new JSONArray();
		this.requests.forEach((ConfigEntity.Request request) -> {
			requestsJSONArray.put(request);
		});

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("requests", requestsJSONArray);
		jsonObject.put("email", this.email);
		jsonObject.put("frequency", this.frequency);
		return jsonObject.toString();
	}
}
