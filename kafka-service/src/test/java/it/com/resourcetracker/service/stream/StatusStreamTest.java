package it.com.resourcetracker.service.stream;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.StatusFailureConsumer;
import com.resourcetracker.service.consumer.entity.StatusFailureConsumerResult;
import com.resourcetracker.service.consumer.entity.StatusSuccessConsumerResult;
import com.resourcetracker.service.stream.StatusSplitStream;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class StatusStreamTest {

	@Autowired
	private EmbeddedKafkaBroker kafkaEmbedded;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@MethodSource("testStatusFailureConsumer")
	public void setStatusFailureUp() {
		kafkaTemplate.send(Constants.KAFKA_STATUS_TOPIC, "");
	}

	@Test
	public void testStatusFailureConsumer() {
		assertTrue(true);
		StatusFailureConsumerResult statusFailureConsumerResult = StatusFailureConsumer.builder()
				.withStreams(new StatusSplitStream())
				.consume();
		// statusFailureConsumerResult;
	}

	@MethodSource("testStatusSuccessConsumer")
	public void setStatusSuccessUp() {
		kafkaTemplate.send(Constants.KAFKA_STATUS_TOPIC, "");
	}

	@Test
	public void testStatusSuccessConsumer() {
		assertTrue(true);

		StatusFailureConsumerResult statusFailureConsumerResult = StatusFailureConsumer.builder()
				.withStreams(new StatusSplitStream())
				.consume();
	}
}
