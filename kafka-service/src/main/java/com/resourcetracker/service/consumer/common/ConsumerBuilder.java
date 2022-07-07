package com.resourcetracker.service.consumer.common;

import java.time.Duration;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerBuilder<T, V> {
	private ConsumerBuilderOptions opts;
	private Properties props;

	public ConsumerBuilder(ConsumerBuilderOptions opts){
		this.opts = opts;
	}

	public ConsumerBuilder<T, V> withProps(Properties props){
		this.props = props;
		return this;
	}

	public T consume() {
		final KafkaConsumer<String, V> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Collections.singletonList(this.opts.getTopic()));
		ConsumerRecords<String, V> records = consumer.poll(Duration.ofSeconds(5));

		ListIterator<ConsumerRecord<String, V>> iter = (ListIterator<ConsumerRecord<String, V>>) records.iterator();

		while (iter.hasNext()) {
			if (this.opts != null) {
				if (iter.nextIndex() == opts.getLimit())
					break;
			}
			ConsumerRecord<String, V> record = iter.next();
			System.out.println(
					"Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
		}

		consumer.commitAsync();
		consumer.close();
		return null;
	};
}
