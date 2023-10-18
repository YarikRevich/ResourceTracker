package com.resourcetracker.service.kafka.consumer.common;

import java.time.Duration;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerBuilder<T extends ConsumerBuilderSource> {
  private ConsumerBuilderOptions opts;
  private Properties props;
  private T source;

  public ConsumerBuilder(T source, ConsumerBuilderOptions opts){
    this.source = source;
    this.opts = opts;
  }

  public ConsumerBuilder<T> withProps(Properties props){
    this.props = props;
    return this;
  }

  public T build(){
    ConsumerBuilderResult consumerBuilderResult = new ConsumerBuilderResult();
    consumerBuilderResult.setProps(this.props);
    consumerBuilderResult.setOpts(this.opts);
    this.source.setConsumerBuilderResult(consumerBuilderResult);
    return this.source;
  }
}
