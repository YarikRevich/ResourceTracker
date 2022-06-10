package com.resourcetracker.service.formatter;

import java.util.HashMap;

public interface IOutputBuilder {
	void setOutputType(OutputType outputType);
	void setHeader(String msg);
	void setBody(String msg);
	String getResult();
}
