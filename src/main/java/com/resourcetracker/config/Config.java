package com.resourcetracker.config;

import java.nio.file.*;
import java.io.*;
import java.util.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.*;


/**
 * Represents config file
 * 
 * @author YarikRevich
 */
public class Config implements Reader {
	private Object config;
	
		
	public Config() {
		InputStream inputStream = null;
		try {
			File file = new File(getConfigPath());
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			};
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Yaml yaml = new Yaml();
		 config = yaml.load(inputStream);
	};


	private Path unixPath = Paths.get("/usr/local/etc/resourcetracker.yaml");
	private Path windowsPath = Paths.get("/usr/local/etc/resourcetracker.yaml");

	/**
	 * Gets path to config file with a view
	 * on a system kind
	 * 
	 * @return path to a config file
	 */
	private String getConfigPath() {
		String os = System.getProperty("os.name");
		if (os.contains("Windows")){
			return windowsPath.toString();
		}else if (os.contains("Linux") || os.contains("Mac OS X")) {
			return unixPath.toString();
		}
		return "";
	};
	
	public Object getConfig() {
		return this.config;
	}
};
