package com.resourcetracker.service.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.resourcetracker.service.consumer.entity.StatusEntity;
import com.resourcetracker.service.consumer.entity.StatusEntity.StatusType;
import com.resourcetracker.service.stream.common.Stream;
import com.resourcetracker.Constants;

/**
 * Stream, which processes 'status' topic
 */
public class StatusStream implements Stream{
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
		final KafkaStreams streams = new KafkaStreams(builder.build(), StreamConfiguration.getConfiguration());

		streams.cleanUp();
		streams.start();
	}
}
