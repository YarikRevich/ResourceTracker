package com.resourcetracker;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class KafkaServiceTest {
	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}
}
