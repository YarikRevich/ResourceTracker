package com.resourcetrackersdk.processcreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessCreator {
    public final static String ACTIVE_UNIX = "active";
    public final static String INACTIVE_UNIX = "inactive";


    private String[] commands;
    private String stdout;
    private String stderr;
    

    public ProcessCreator(String... command) {
        this.commands = command;
    }

    public void start() {
        ProcessBuilder ps = new ProcessBuilder(this.commands);
        ps.redirectErrorStream(true);

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

    public boolean isError(){
        return this.stderr.length() != 0;
    }

    public boolean isStdout(){
        return this.stdout.length() != 0;
    }
}
