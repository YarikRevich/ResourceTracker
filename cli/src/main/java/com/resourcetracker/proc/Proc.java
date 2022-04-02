package com.resourcetracker.proc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuilder;

import java.util.hash.HashMap;

public class Proc {
    // public final static String ACTIVE_UNIX = "active";
    // public final static String INACTIVE_UNIX = "inactive";

    private String[] commands;
    private String stdout;
    private String stderr;
    private HashMap<String, String> envVars;

    public Proc(String... command) {
        this.commands = command;
    }

    public void setCommands(String... command) {
        this.commands = command;
    }

    public void appendCommands(String ...command){
        this.commands.append(command);
    }

    public void setEnvVars(HashMap<String, String> envVars){
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



    // public <T>void setFlags(HashMap<String, T> flags){
    //     for (var flag : flags){
    //         StringBuilder flagVal = new StringBuilder();
    //         if (flag instanceof HashMap<?, ?>){
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
