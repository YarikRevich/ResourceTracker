package com.resourcetracker.service.kafka.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.resourcetracker.Constants;
import com.resourcetracker.service.configuration.KafkaConfiguration;
import com.resourcetracker.service.entity.StatusEntity;
import com.resourcetracker.service.entity.StatusEntity.StatusType;
import com.resourcetracker.service.stream.common.StreamBuilder;
import com.resourcetracker.service.stream.common.StreamBuilderResult;
import com.resourcetracker.service.stream.common.StreamBuilderSource;

/**
 * Stream, which processes 'status' topic
 */
public class StatusSplitStream implements StreamBuilderSource {
  StreamBuilderResult streamBuilderResult;

  public void setStreamBuilderResult(StreamBuilderResult streamBuilderResult) {
    this.streamBuilderResult = streamBuilderResult;
  }

  public static StreamBuilder<StatusSplitStream> builder(){
    return new StreamBuilder<StatusSplitStream>(new StatusSplitStream());
  }

  public void run() {
    final StreamsBuilder builder = new StreamsBuilder();
    final KStream<String, StatusEntity> status = builder.stream(Constants.KAFKA_STATUS_TOPIC, Consumed.with(
        Serdes.String(),
        Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(StatusEntity.class))));
    status
        .filter((k, v) -> v.getStatusType() == StatusType.FAILURE)
        .to(Constants.KAFKA_STATUS_FAILURE_TOPIC);
    status
        .filter((k, v) -> v.getStatusType() == StatusType.SUCCESS)
        .to(Constants.KAFKA_STATUS_SUCCESS_TOPIC);
    final KafkaStreams streams = new KafkaStreams(builder.build(), this.streamBuilderResult.getProps());

    streams.cleanUp();
    streams.start();

    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }
}
