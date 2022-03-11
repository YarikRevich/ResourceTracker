package com.resourcetracker.config;

import java.nio.file.*;
import java.io.*;
import java.util.*;
import org.yaml.snakeyaml.Yaml;


/**
 * Represents config file
 * 
 * @author YarikRevich
 */
public class Config {
	Map<String, Object> config;
		
	
	public void main(String[] args) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(getConfigPath()));
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
	public String getConfigPath() {
		String os = System.getProperty("os.name");
		if (os.contains("Windows")){
			return windowsPath.toString();
		}else if (os.contains("Linux") || os.contains("MacOS")) {
			return unixPath.toString();
		}
		return "";
	};

	
	/**
	 * Gets value from config by key
	 * 
	 * @param key names key from config file
	 * @return value from config file gotten by key
	 */
	public Object getFromConfig(String key) {
		return config.get(key);
	}
};
