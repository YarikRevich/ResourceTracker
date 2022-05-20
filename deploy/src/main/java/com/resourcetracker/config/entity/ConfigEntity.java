package com.resourcetracker.entity;

public class ConfigEntity {
	// Represents request, which will be executed on a remote machine
	public class Request {
		public Optional<String> tag;
		public String url;

		public enum Method{
			POST("post"),
			GET("get"),
			PUT("put");
		}

		public Optional<Method> method;

		public Optional<String> data;

		@Pattern(regexp="^((^((([0-9]*)(s|m|h|d|w))))|(^once))$")
		public String frequency;
	}

	public ArrayList<Request> requests;

	// jsonObject.put("email", this.email);
	// jsonObject.put("frequency", this.frequency);
}
