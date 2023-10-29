package com.resourcetracker.service.kafka.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBase;
import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.entity.StatusFailureConsumerResult;
import com.resourcetracker.service.entity.StatusEntity;

public class StatusFailureConsumer extends ConsumerBase<StatusFailureConsumerResult, StatusEntity> {
  public static ConsumerBuilder<StatusFailureConsumer> builder() {
    return new ConsumerBuilder<StatusFailureConsumer>(
        new StatusFailureConsumer(), new StatusFailureConsumerOptions().getOpts());
  }
}
