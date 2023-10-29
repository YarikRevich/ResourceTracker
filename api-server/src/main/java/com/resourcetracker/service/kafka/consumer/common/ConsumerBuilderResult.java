package com.resourcetracker.service.kafka.consumer.common;

import java.util.Properties;

public class ConsumerBuilderResult {
  private Properties props;
  private String topic;
  private ConsumerBuilderOptions opts;

  public ConsumerBuilderOptions getOpts() {
    return this.opts;
  }

  public void setOpts(ConsumerBuilderOptions opts) {
    this.opts = opts;
  }

  public String getTopic() {
    return this.topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public Properties getProps() {
    return this.props;
  }

  public void setProps(Properties props) {
    this.props = props;
  }
}
