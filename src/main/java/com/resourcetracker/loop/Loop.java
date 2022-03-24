package com.resourcetracker.loop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Process;

import com.resourcetracker.tools.params.Params;
import com.resourcetracker.tools.params.ParamCallbackDefault;
import com.resourcetracker.status.Status;
import com.resourcetracker.api.API;
import com.resourcetracker.process.Manager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.boot.SpringApplication;

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
        SpringApplication.run(API.class, args);

        Params.ifValidateDo(new ParamCallbackDefault() {
            public void call() {

            };
        });

        Params.ifStartDo(new ParamCallbackDefault() {
            public void call() {
                if (!Status.isStarted()) {
                    Manager.start();
                    
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
