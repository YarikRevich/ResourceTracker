package com.resourcetracker.tf;

import java.util.Map;
import java.util.TreeMap;

import com.resourcetracker.proc.Proc;

public class TF {
    private String src;
    private Proc proc;
    private TreeMap<String, String> envVars = new TreeMap<String, String>();
    private TreeMap<String, String> vars = new TreeMap<String, String>();

    public void setSrc(String src) {
        this.src = src;
    }

    public void setEnvVar(String key, String value) {
        this.envVars.put(key, value);
    }

    public void setVar(String key, String value) {
        this.vars.put(key, value);
    }

    public void start() {
        proc = new Proc("terraform", "init");
        proc.start();

        proc.setCommands("terraform", "plan");

        vars.forEach((k, v) -> {
            StringBuilder command = new StringBuilder();
            command.append("-var").append(" ").append(k).append("=").append(v);
            proc.appendCommands(command.toString());
        });

        StringBuilder command = new StringBuilder();
        command.append("-chdir").append("=").append(this.src);
        proc.appendCommands(command.toString());

        proc.setEnvVars(this.envVars);
        proc.start();
    }

    public void stop() {
        proc = new Proc("terraform", "destroy");
        proc.start();
    }

    public boolean isOk() {
        return !proc.isError();
    }
}
