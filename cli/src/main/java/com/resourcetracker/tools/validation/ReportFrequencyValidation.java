package com.resourcetracker.tools.validation;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.resourcetracker.tools.exception.ValidationException;

public class ReportFrequencyValidation {
	public ReportFrequencyValidation(String reportFrequency, String errMessage) throws ValidationException {
		if (!(Pattern.compile("^[0-9]*$")
				.matcher(reportFrequency.subSequence(0, reportFrequency.length() - 1))
				.matches()
				&& (reportFrequency.endsWith("s") ||
						reportFrequency.endsWith("m") ||
						reportFrequency.endsWith("h") ||
						reportFrequency.endsWith("d") ||
						reportFrequency.endsWith("w")))) {
			throw new ValidationException(errMessage);
		}
	}
}
