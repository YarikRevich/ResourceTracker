package com.resourcetracker.service.formatter.common;

import java.util.HashMap;

import com.resourcetracker.service.formatter.OutputType;

public interface OutputBuilderInterface {
  void setOutputType(OutputType outputType);
  void setHeader(String msg);
  void setBody(String msg);
  String getResult();
}
