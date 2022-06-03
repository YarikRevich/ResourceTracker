package com.resourcetracker.service.context.entity;

import javax.validation.constraints.Pattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.resourcetracker.entity.ConfigEntity.Method;
import com.resourcetracker.entity.ConfigEntity.DataType;

public class ContextEntity {
	public class Request {
		public String tag;
		public String data;
		/**
		 * Email that used to send user a report, which is
		 * collected during stated period of time
		 */
		public String email;
		public int frequency;
	}
	public ArrayList<Request> requests;

}
