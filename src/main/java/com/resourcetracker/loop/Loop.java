package com.resourcetracker.loop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Process;

import com.resourcetracker.tools.params.Params;
import com.resourcetracker.tools.params.ParamCallbackDefault;
import com.resourcetracker.daemonizer.Manager;
import com.resourcetracker.status.Status;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Loop {
    private static String[] args;

    final static Logger logger = LogManager.getLogger(Loop.class);

    public static void setContext(String[] args) {
        Loop.args = args;
    }

    public static void prerun() {
        Params.parse(args);
    }

    public static void run() {
        Params.ifValidateDo(new ParamCallbackDefault() {
            public void call() {

            };
        });

        Params.ifStartDo(new ParamCallbackDefault() {
            public void call() {
                if (!Status.isStarted()) {
                    Manager.start();
                    ProcessBuilder ps = new ProcessBuilder("pwd");

                    // From the DOC: Initially, this property is false, meaning that the
                    // standard output and error output of a subprocess are sent to two
                    // separate streams
                    ps.redirectErrorStream(true);

                    Process pr = null;
                    try {
                        pr = ps.start();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
                    String line;
                    try {
                        while ((line = in.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        pr.waitFor();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("ok!");
                    // Process process;
                    // try {
                    // process = Runtime.getRuntime().exec("ls");
                    // } catch (IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }
                    // process.inputReader().;
                } else {
                    logger.info("ResourceTracker is already started");
                }
            };
        });

        Params.ifStopDo(new ParamCallbackDefault() {
            public void call() {
                if (!Status.isStoped()) {
                    Manager.stop();
                } else {
                    logger.info("ResourceTracker is already stoped");
                }
            };
        });

        // listenerPoll.add(parser.getCloudProviderRawPublicAddresses());
        // listenerPoll.add(parser.getCloudProviderRawPublicAddressesWithTags());

        // listenerPoll.add(parser.getLocalRawPublicAddresses());
        // listenerPoll.add(parser.getLocalRawPublicAddressesWithTags());
    }
}
