package com.resrourcetracker;

import com.resourcetracker.config.*;
import java.util.ArrayList;

public class Tracker{
	public static void main(String[] args) throws Throwable {
		Reader reader = new Reader();
		Parser parser = new Parser(reader);
		parser.validate();
//		parser.isLocal();
//		parser.isCloud();
//		(ArrayList<String>)config.getFromConfig("test");
	};
}
