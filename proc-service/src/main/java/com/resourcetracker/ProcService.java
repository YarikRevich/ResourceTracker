package com.resourcetracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resourcetracker.exception.ProcException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.TreeMap;
import java.util.ArrayList;

@Service
public class ProcService {
	private ArrayList<String> commands = new ArrayList<String>();

	public ProcService setFlag(String key, String value){
		this.commands.add(key);
		this.commands.add("=");
		this.commands.add(value);
		return this;
	}

	public ProcService setMapOfFlag(String key, TreeMap<String, String> values){
		values.forEach((k, v) -> {
			this.setFlag(key, String.format("'%s=%s'", k, v));
		});
		return this;
	}

	public ProcService setPositionalVar(String value){
		this.commands.add(value);
		return this;
	}

	public ProcService setCommand(String command){
		this.commands.add(command);
		return this;
	}
	private String stdout = "";

	public String getStdout() {
		return this.stdout;
	}

	public <T> T getStdoutAsJSON(){
		T result = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.readValue(this.stdout, new TypeReference<T>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	private String stderr = "";

	public String getStderr(){
		return this.stderr;
	}

	private TreeMap<String, String> envVars = new TreeMap<String, String>();

	public void setEnvVar(String key, String value) {
		this.envVars.put(key, value);
	}

	public ProcService setEnvVars(TreeMap<String, String> envVars){
		for (var envVar: envVars.entrySet()){
			this.setEnvVar(envVar.getKey(), envVar.getValue());
		}
		return this;
	}

	public TreeMap<String, String> getEnvVars(){
		return this.envVars;
	}

	private String directory = "";

	public ProcService setDirectory(String directory){
		this.directory = directory;
		return this;
	}

	public String getDirectory() {
		return directory;
	}

	String wrapper = "";

	/**
	 * @param wrapper Wrapper function for the whole command
	 * @return instance of ProcService
	 */
	public ProcService setWrapper(String wrapper){
		this.wrapper = wrapper;
		return this;
	}

	public ProcService build(){
		this.commands.clear();
		this.envVars.clear();
		this.directory = "";

		return this;
	}

	public void run() {
		if (!this.wrapper.isEmpty()){
			String copyCommandString = this.toString();
			ArrayList<String> copyCommandArray = new ArrayList<String>(this.commands);
			this.commands.clear();
			this.commands.add(this.wrapper);
			this.commands.add(String.format("'%s'", copyCommandString));
		}


		if (this.commands.isEmpty()) {
			throw new RuntimeException("command is empty");
		}

		ProcessBuilder processBuilder = new ProcessBuilder(this.commands);
		processBuilder.redirectErrorStream(true);

		var env = processBuilder.environment();

		for (var envVar : this.envVars.entrySet()) {
			env.put(envVar.getKey(), envVar.getValue());
		}

		if (!this.directory.isEmpty()) {
			processBuilder.directory(new File(this.directory));
		}

		Process process = null;
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		try {
			while ((line = in.readLine()) != null) {
				this.stdout += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (this.stdout.length() > 0) {
			this.stdout = this.stdout.substring(0, this.stdout.length() - 1);
		}

		in = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		line = "";
		try {
			while ((line = in.readLine()) != null) {
				this.stderr += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (this.stderr.length() > 0) {
			this.stderr = this.stderr.substring(0, this.stderr.length() - 1);
		}
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return command representation as a text
	 */
	public String toString(){
		StringBuilder rawCommand = new StringBuilder();
		for (String command: commands){
			rawCommand.append(rawCommand);
		}
		return rawCommand.toString();
	}
}
