package com.resourcetracker.service.formatter;

import java.util.HashMap;

public class OutputBuilder implements OutputBuilder {
	private HashMap<String, String> data;

	private String output;

	public StatusBuilder(HashMap<String, String> data){
		this.data = data;
	}

	@Override
	public HashMap<String, String> getData(){
		return this.data;
	}

	/**
	 * @param outputType output type for external representation
	 */
	@Override
	public void setOutputType(OutputType outputType) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Output type: ");
		stringBuilder.append(outputType.toString());
		stringBuilder.append("\n");
		this.output += stringBuilder.toString();
	}

	@Override
	public void setHeader(String msg) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Header: ");
		stringBuilder.append(msg);
		stringBuilder.append("\n");
		this.output += stringBuilder.toString();
	}

	@Override
	public void setBody(String msg) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Body: ");
		stringBuilder.append(msg);
		stringBuilder.append("\n");
		this.output += stringBuilder.toString();
	}

	@Override
	public String format(){
		return this.output;
	}
}
