package com.resourcetracker.request;

import com.resourcetracker.context.Context;
import com.resourcetracker.request.Request;
import com.resourcetracker.tools.utils.AddressSort;
// import org.javatuples.Pair;
import java.util.ArrayList;

/**
 * Sends requests using addresses specified in the context
 */
public class RequestManager {
	// public static synchronized Pair<ArrayList<String>, ArrayList<String>> handleRequestsFromContext() {
		// Context.getAddresses().forEach((k, v) -> {
		// 	new Thread(() -> {
		// 		for (String url : v.schemas()) {
		// 			Request req = new Request(url);
		// 			req.run();

		// 				// boolean isReachable = this.isReachable(addressWithTag.address());
		// 			// if (isReachable) {
		// 			// infoResult.add(new ListenerResult(addressWithTag.index(),
		// 			// String.format("%s(%s): ok",
		// 			// addressWithTag.tag(), addressWithTag.address().toString())));
		// 			// } else {
		// 			// infoResult.add(new ListenerResult(addressWithTag.index(),
		// 			// String.format("%s(%s): bad",
		// 			// addressWithTag.tag(), addressWithTag.address().toString())));
		// 			// }

		// 		}
		// 	}).start();
		// });
		// AddressSort.sort(res);

	// 	return null;
	// }
}
