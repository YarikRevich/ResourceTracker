package unit.com.resourcetracker.service.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.StatusSuccessConsumerOptions;
import com.resourcetracker.service.consumer.common.ConsumerBuilderOptions;

@ExtendWith(SpringExtension.class)
public class StatusSuccessConsumerOptsTest {
	public void testIfOptsCorrect(){
		StatusSuccessConsumerOptions statusSuccessConsumerOpts = new StatusSuccessConsumerOptions();
		ConsumerBuilderOptions opts = statusSuccessConsumerOpts.getOpts();

		assertEquals(opts.getLimit(), 0);
		assertEquals(opts.getTopic(), Constants.KAFKA_STATUS_SUCCESS_TOPIC);
	}
}
