package unit.com.resourcetracker.service.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.StatusFailureConsumerOptions;
import com.resourcetracker.service.consumer.common.ConsumerBuilderOptions;

@ExtendWith(SpringExtension.class)
public class StatusFailureConsumerOptsTest {
	public void testIfOptsCorrect(){
		StatusFailureConsumerOptions statusFailureConsumerOpts = new StatusFailureConsumerOptions();
		ConsumerBuilderOptions opts = statusFailureConsumerOpts.getOpts();

		assertEquals(opts.getLimit(), 0);
		assertEquals(opts.getTopic(), Constants.KAFKA_STATUS_FAILURE_TOPIC);
	}
}
