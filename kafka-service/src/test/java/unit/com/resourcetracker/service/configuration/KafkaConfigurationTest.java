package unit.com.resourcetracker.service.configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.resourcetracker.service.configuration.KafkaConfiguration;

@RunWith(SpringRunner.class)
public class KafkaConfigurationTest {
	@Test
	public void testIfPropertiesFileIsLoading(){
		KafkaConfiguration.getConfiguration();
	}

	@Test
	public void testIfPropertiesFileIsNotEmpty(){
		assertFalse(KafkaConfiguration.getConfiguration().isEmpty());
	}
}
