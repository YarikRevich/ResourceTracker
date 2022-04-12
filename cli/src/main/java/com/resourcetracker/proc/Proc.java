package com.resourcetracker.proc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuilder;

import java.util.TreeMap;
import java.util.ArrayList;

public class Proc {
    private ArrayList<String> commands;
    private String stdout;
    private String stderr;
    private TreeMap<String, String> envVars = new TreeMap<String, String>();

    public Proc(String... commands) {
		for (String command : commands){
			this.commands.add(command);
		}
    }

    public void setCommands(String... commands) {
        for (String command : commands){
			this.commands.add(command);
		}
    }

    public void appendCommands(String ...commands){
		for (String command : commands){
			this.commands.add(command);
		}
    }

    public void setEnvVars(TreeMap<String, String> envVars){
        this.envVars = envVars;
    }

    public void start() {
        ProcessBuilder ps = new ProcessBuilder(this.commands);
        ps.redirectErrorStream(true);

        Map<String, String> env = pb.environment();
        for (var envVar : this.envVars){
            env.put(envVar.getKey(), envVar.getValue());
        }

        Process pr = null;
        try {
            pr = ps.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        try {
            while ((line = in.readLine()) != null) {
                this.stdout += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        in = new BufferedReader(new InputStreamReader(pr.getErrorStream()));
        line = "";
        try {
            while ((line = in.readLine()) != null) {
                this.stderr += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            pr.waitFor();
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

    public boolean isError() {
        return this.stderr.length() != 0;
    }

    public boolean isStdout() {
        return this.stdout.length() != 0;
    }



    // public <T>void setFlags(TreeMap<String, T> flags){
    //     for (var flag : flags){
    //         StringBuilder flagVal = new StringBuilder();
    //         if (flag instanceof TreeMap<?, ?>){
    //             for (var entity : flag){
    //                 flagVal.append(entity.getKey());
    //                 flagVal.append("=");
    //                 flagVal.append(entity.getValue());
    //                 flagVal.append(",");
    //             }
    //         }
    //     }
    // }
}
