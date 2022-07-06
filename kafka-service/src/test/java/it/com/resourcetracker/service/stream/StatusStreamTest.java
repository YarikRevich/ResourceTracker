package it.com.resourcetracker.service.stream;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.resourcetracker.service.configuration.KafkaConfiguration;
import com.resourcetracker.service.entity.StatusEntity;
import com.resourcetracker.service.entity.StatusEntity.StatusType;
import com.resourcetracker.service.producer.StatusProducer;
import com.resourcetracker.service.stream.StatusSplitStream;
import com.resourcetracker.Constants;

/**
 * Unit test for simple App.
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092",
		"port=9092" }, topics = { Constants.KAFKA_STATUS_TOPIC })

@Import({ StatusProducer.class })
// @DirtiesContext
@ExtendWith({ SpringExtension.class })
public class StatusStreamTest {
	private static final Logger logger = LogManager.getLogger(StatusSplitStream.class);

	@Autowired
	EmbeddedKafkaBroker kafkaEmbeddedBroker;

	@Autowired
	StatusProducer statusProducer;

	// Properties kafkaConfiguration;

	// private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	// private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	// private final PrintStream originalErr = System.err;
	// private final PrintStream originalErr = System.err;

	@BeforeEach
	public void setUp() {
		Properties kafkaConfiguration = KafkaConfiguration
				.convertMapToProps(KafkaTestUtils.producerProps(kafkaEmbeddedBroker));
		kafkaConfiguration.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringSerializer");
		kafkaConfiguration.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
				"org.springframework.kafka.support.serializer.JsonSerializer");

		statusProducer.init(kafkaConfiguration);
		// Map<String, Object> producerConfigs = new
		// HashMap<>(KafkaTestUtils.producerProps(kafkaEmbeddedBroker));
		// Producer<String, String> producer = new DefaultKafkaProducerFactory<String,
		// String>(producerConfigs,
		// new StringSerializer(),
		// new StringSerializer()).createProducer();

		// assertFalse(producer.send(new ProducerRecord<String, String>("fdfd",
		// "fdfd")).isCancelled());
		// logger.info("SUCCESS");
		// System.setOut(new PrintStream(outContent));
		// System.setErr(new PrintStream(errContent));

	}

	@Test
	public void testStatusFailureConsumer() {
		StatusEntity data = new StatusEntity();
		data.setStatusType(StatusType.FAILURE);
		statusProducer.send(data, () -> {
			assertTrue(true);
		}, () -> {
			fail();
		});

		// try {
		// System.setOut(new PrintStream(new BufferedOutputStream(new
		// FileOutputStream("file.txt")), true));
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		// System.out.println();

		// kafkaConfiguration = KafkaConfiguration.builder()
		// .withBootstrapServer(kafkaEmbeddedBroker.getBrokersAsString())
		// .withClientId("testclient")
		// .withApplicationId("123")
		// .withGroupId("testgroup")
		// .build();

		// for(var v : KafkaTestUtils.producerProps(kafkaEmbeddedBroker).entrySet()){
		// System.out.print(v.getKey());
		// System.out.print(" ");
		// System.out.println(v.getValue());
		// }
		// System.out.println(KafkaConfiguration.convertMapToProps(KafkaTestUtils.producerProps(kafkaEmbeddedBroker)));



		logger.error("SUCCESS");
		// statusProducer.init(kafkaConfiguration);

		// System.setErr(originalErr);

		// StatusSplitStream statusSplitStream = new StatusSplitStream();
		// statusSplitStream.init(kafkaConfiguration);
		// statusSplitStream.run();
		// StatusFailureConsumerResult statusFailureConsumerResult =
		// StatusFailureConsumer.builder()
		// .withProps(kafkaConfiguration)
		// // .withStreams(new StatusSplitStream())
		// .consume();
		// statusFailureConsumerResult;
	}

	// @Nested
	// class StatusFailureStreamTest {
	// // @Before
	// // public void setUp() {
	// // StatusEntity data = new StatusEntity();
	// // data.setStatusType(StatusType.FAILURE);
	// // statusProducer.init(kafkaConfiguration);
	// // statusProducer.send(data);
	// // }

	// @Test
	// public void testStatusFailureConsumer() {
	// // StatusSplitStream statusSplitStream = new StatusSplitStream();
	// // statusSplitStream.init(kafkaConfiguration);
	// // statusSplitStream.run();
	// // StatusFailureConsumerResult statusFailureConsumerResult =
	// // StatusFailureConsumer.builder()
	// // .withProps(kafkaConfiguration)
	// // .withStreams(new StatusSplitStream())
	// // .consume();
	// // statusFailureConsumerResult;
	// }
	// }

	// @Nested
	// class StatusSuccessStreamTest {
	// @Before
	// public void setUp() {
	// StatusEntity data = new StatusEntity();
	// data.setStatusType(StatusType.SUCCESS);
	// statusProducer.init(kafkaConfiguration);
	// statusProducer.send(data);
	// }

	// @Test
	// public void testStatusSuccessConsumer() {
	// assertTrue(true);

	// // StatusFailureConsumerResult statusFailureConsumerResult =
	// // StatusFailureConsumer.builder()
	// // .withStreams(new StatusSplitStream())
	// // .consume();
	// }
	// }
}
