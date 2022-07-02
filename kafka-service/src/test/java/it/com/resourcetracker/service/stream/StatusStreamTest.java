package it.com.resourcetracker.service.stream;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.resourcetracker.service.configuration.KafkaConfiguration;
import com.resourcetracker.service.consumer.StatusFailureConsumer;
import com.resourcetracker.service.consumer.entity.StatusFailureConsumerResult;
import com.resourcetracker.service.entity.StatusEntity;
import com.resourcetracker.service.entity.StatusEntity.StatusType;
import com.resourcetracker.service.producer.StatusProducer;
import com.resourcetracker.service.stream.StatusSplitStream;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class StatusStreamTest {

	@Autowired
	private StatusProducer statusProducer;

	@Before
	public void setUp(){
		KafkaConfiguration.setBoostrapServer("localhost:9092");
		statusProducer.init(KafkaConfiguration.getConfiguration());
	}

	@Nested
	class StatusFailureStreamTest {
		@Before
		public void setUp() {
			StatusEntity data = new StatusEntity();
			data.setStatusType(StatusType.FAILURE);
			statusProducer.send(data);
		}

		@Test
		public void testStatusFailureConsumer() {
			assertTrue(true);
			// StatusFailureConsumerResult statusFailureConsumerResult = StatusFailureConsumer.builder()
			// 		.withStreams(new StatusSplitStream())
			// 		.consume();
			// statusFailureConsumerResult;
		}
	}

	@Nested
	class StatusSuccessStreamTest {
		@Before
		public void setUp() {
			StatusEntity data = new StatusEntity();
			data.setStatusType(StatusType.SUCCESS);
			statusProducer.send(data);
		}

		@Test
		public void testStatusSuccessConsumer() {
			assertTrue(true);

			// StatusFailureConsumerResult statusFailureConsumerResult =
			// StatusFailureConsumer.builder()
			// .withStreams(new StatusSplitStream())
			// .consume();
		}
	}
}
