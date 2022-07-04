package unit.com.resourcetracker.service.configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import org.apache.kafka.streams.StreamsConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.resourcetracker.service.configuration.KafkaConfiguration;

@RunWith(SpringRunner.class)
public class KafkaConfigurationTest {
	final String bootstrapServer = "localhost:9092";
	final String clientId = "testclient";
	final String applicationId = "123";
	final String groupId = "testgroup";

	@Test
	public void testIfConfigurationFormats() {
		Properties kafkaConfiguration = KafkaConfiguration.builder()
				.withBootstrapServer(bootstrapServer)
				.withClientId(clientId)
				.withApplicationId(applicationId)
				.withGroupId(groupId)
				.build();

		assertTrue(kafkaConfiguration.get(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG).equals(bootstrapServer));
		assertTrue(kafkaConfiguration.get(StreamsConfig.CLIENT_ID_CONFIG).equals(clientId));
		assertTrue(kafkaConfiguration.get(StreamsConfig.APPLICATION_ID_CONFIG).equals(applicationId));
		assertTrue(kafkaConfiguration.get("group.id").equals(groupId));
	}
}
