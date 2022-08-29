package com.resourcetracker.service.consumer.common;

import java.time.Duration;
import java.util.Collections;
import java.util.ListIterator;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerBase<T, V> implements ConsumerBuilderSource {
	private ConsumerBuilderResult consumerBuilderResult;

	public void setConsumerBuilderResult(ConsumerBuilderResult consumerBuilderResult) {
		this.consumerBuilderResult = consumerBuilderResult;
	}

	public T consume() {
		final KafkaConsumer<String, V> consumer = new KafkaConsumer<>(this.consumerBuilderResult.getProps());
		consumer.subscribe(Collections.singletonList(this.consumerBuilderResult.getOpts().getTopic()));
		ConsumerRecords<String, V> records = consumer.poll(Duration.ofSeconds(5));

		ListIterator<ConsumerRecord<String, V>> iter = (ListIterator<ConsumerRecord<String, V>>) records.iterator();

		while (iter.hasNext()) {

			if (iter.nextIndex() == this.consumerBuilderResult.getOpts().getLimit())
				break;

			ConsumerRecord<String, V> record = iter.next();
			System.out.println(
					"Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
		}

		consumer.commitAsync();
		consumer.close();
		return null;
	};
}
