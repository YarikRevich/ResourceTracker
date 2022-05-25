package com.resourcetracker.request;

// import org.springframework.web.client.RestTemplate;
// import org.springframework.http.HttpStatus;
// import com.resourcetracker.utils.Utils;
// import com.resourcetracker.utils.exceptions.RequestError;

/**
 * Abstraction for making http requests to the specified resource
 *
 * @author YarikRevich
 */
public class Request {
	private String url;

	public Request(String url) {
		this.url = url;
	}

	/**
	 *
	 * @return if request is ok
	 */
	// public boolean run() throws RequestError {
		// if (Utils.isOnline(url)) {
		// 	RestTemplate restTemplate = new RestTemplate();
		// 	ResponseEntity<String> response = restTemplate.getForEntity(this.url, String.class);
		// 	if (response.getStatusCode() != HttpStatus.OK) {
		// 		throw new RequestError(
		// 				String.format("Request for %s ended with code %f", this.url, response.getStatusCode()));
		// 	}
		// 	return true;
		// }
		// throw new RequestError(String.format("Request for %s was not successful, because such host does not exist", this.url));
	// }
}
