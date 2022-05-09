package com.resourcetracker.commands;

import org.springframework.stereotype.Component;

@Component
@Command(
	subcommands = {
		Start.class,
		Stop.class,
		Status.class,
		Validate.class,
	}
)
public class Base {

}
