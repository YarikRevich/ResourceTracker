package com.resourcetracker.service.formatter;

import java.util.HashMap;

public class OutputBuilder implements IOutputBuilder {
	private String output;

	/**
	 * @param outputType output type for external representation
	 */
	@Override
	public void setOutputType(OutputType outputType) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n");
		stringBuilder.append("#########");
		stringBuilder.append(outputType.toString());
		stringBuilder.append("#########");
		stringBuilder.append("\n");
		this.output += stringBuilder.toString();
	}

	@Override
	public void setHeader(String msg) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(msg);
		stringBuilder.append("\n");
		this.output += stringBuilder.toString();
	}

	@Override
	public void setBody(String msg) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(msg);
		stringBuilder.append("\n");
		this.output += stringBuilder.toString();
	}

	@Override
	public String getResult(){
		return this.output;
	}
}
