package com.resourcetracker.service.kafka.producer.common;

import java.util.Properties;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


public class ProducerBuilder<T extends ProducerBuilderSource> {
  private Properties props;
  private ProducerBuilderOptions opts;
  private T source;

  public ProducerBuilder(T source, ProducerBuilderOptions opts){
    this.source = source;
    this.opts = opts;
  }

  public ProducerBuilder<T> withProps(Properties props){
    this.props = props;
    return this;
  }

  public T build(){
    ProducerBuilderResult producerBuilderResult = new ProducerBuilderResult();
    producerBuilderResult.setProps(this.props);
    producerBuilderResult.setOpts(this.opts);
    this.source.setProducerBuilderResult(producerBuilderResult);
    return source;
  }
}
