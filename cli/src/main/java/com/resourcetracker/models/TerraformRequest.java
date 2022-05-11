package com.resourcetracker.entities;

import java.util.ArrayList;
import org.json.JSONObject;

/**
 * @author YarikRevich
 *
*/
public class TerraformRequest {
	private ArrayList<ConfigModel.Request> requests;
	private String email;
	private int frequency;

	public TerraformRequest(ArrayList<ConfigModel.Request> requests, String email, int frequency){
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
