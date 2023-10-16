package com.resourcetracker.service.formatter;

public class OutputFormatter {

	public static class StatusMessageData {
		private String requestTag;

		private String requestResult;

		public StatusMessageData(String requestTag, String requestResult){
			this.requestTag = requestTag;
			this.requestResult = requestResult;
		}

		public String getRequestTag() {
			return requestTag;
		}

		public String getRequestResult() {
			return requestResult;
		}
	}
	public String formatStatusMessage(OutputBuilder outputBuilder, StatusMessageData statusMessageData){
		outputBuilder.setOutputType(OutputType.STATUS);
		outputBuilder.setHeader(String.format("# Tag of request: %s", statusMessageData.getRequestTag()));
		outputBuilder.setBody(String.format("# Result of request: %s", statusMessageData.getRequestResult()));
		return outputBuilder.getResult();
	}
}
