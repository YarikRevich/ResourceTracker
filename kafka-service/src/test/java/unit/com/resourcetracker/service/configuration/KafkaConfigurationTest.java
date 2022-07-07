package unit.com.resourcetracker.service.configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.StreamsConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.resourcetracker.service.configuration.KafkaConfiguration;

@ExtendWith(SpringExtension.class)
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
		assertTrue(kafkaConfiguration.get(ConsumerConfig.GROUP_ID_CONFIG).equals(groupId));
	}
}
