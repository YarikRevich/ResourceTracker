package com.resourcetracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuilder;

import com.resourcetracker.exception.ProcException;
import org.springframework.stereotype.Service;

import java.util.TreeMap;
import java.util.ArrayList;

@Service
public class ProcService {
	private ArrayList<String> commands = new ArrayList<String>();
	private String stdout = "";
	private String stderr = "";
	private TreeMap<String, String> envVars = new TreeMap<String, String>();

	public void setCommands(String... commands) {
		this.commands.clear();
		for (String command : commands) {
			this.commands.add(command);
		}
	}

	public void appendCommands(String... commands) {
		for (String command : commands) {
			this.commands.add(command);
		}
	}

	public void setEnvVars(TreeMap<String, String> envVars) {
		this.envVars = envVars;
	}

	public void start() throws ProcException {
		if (this.commands.isEmpty()) {
			throw new ProcException();
		}

		ProcessBuilder processBuilder = new ProcessBuilder(this.commands);
		processBuilder.redirectErrorStream(true);

		var env = processBuilder.environment();

		for (var envVar : this.envVars.entrySet()) {
			env.put(envVar.getKey(), envVar.getValue());
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

	public String getStdout() {
		return this.stdout;
	}

	public String getStderr() {
		return this.stderr;
	}

	public boolean isStderr() {
		return this.stderr.length() != 0;
	}

	public boolean isStdout() {
		return this.stdout.length() != 0;
	}
}
