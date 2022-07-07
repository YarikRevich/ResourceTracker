package unit.com.resourcetracker.service.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.FiveLastLogsConsumerOptions;
import com.resourcetracker.service.consumer.common.ConsumerBuilderOptions;

@ExtendWith(SpringExtension.class)
public class FiveLastLogsConsumerOptsTest {
	public void testIfOptsCorrect(){
		FiveLastLogsConsumerOptions fiveLastlogsConsumerOpts = new FiveLastLogsConsumerOptions();
		ConsumerBuilderOptions opts = fiveLastlogsConsumerOpts.getOpts();

		assertEquals(opts.getLimit(), 5);
		assertEquals(opts.getTopic(), Constants.KAFKA_LOGS_TOPIC);
	}
}
