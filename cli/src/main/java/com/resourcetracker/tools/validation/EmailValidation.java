package com.resourcetracker.tools.validation;

import java.util.regex.Pattern;
import com.resourcetracker.tools.exceptions.ValidationException;

public class EmailValidation {
	public EmailValidation(String email, String errMessage) throws ValidationException {
		if (!Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
				+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
				.matcher(email)
				.matches()) {
			throw new ValidationException(errMessage);
		}
	}
}
