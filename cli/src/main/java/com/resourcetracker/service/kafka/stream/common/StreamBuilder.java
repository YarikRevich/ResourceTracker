package com.resourcetracker.service.kafka.stream.common;

import java.util.Properties;

public class StreamBuilder<T extends StreamBuilderSource>{
  private Properties props;
  private T source;

  public StreamBuilder(T source){
    this.source = source;
  }

  public StreamBuilder<T> withProps(Properties props){
    this.props = props;
    return this;
  }

  public T build(){
    StreamBuilderResult streamBuilderResult = new StreamBuilderResult();
    streamBuilderResult.setProps(this.props);
    source.setStreamBuilderResult(streamBuilderResult);
    return source;
  }
}
