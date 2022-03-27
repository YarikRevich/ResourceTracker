package com.resourcetrackersdk.loop;

import com.resourcetrackersdk.tools.params.Params;
import com.resourcetrackersdk.tools.params.ParamCallbackDefault;
import com.resourcetrackersdk.api.API;
import com.resourcetrackersdk.process.Manager;

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
                if (!Manager.isOk()) {
                    Manager.start();
                } else {
                    logger.info("ResourceTracker is already started");
                }
            };
        });

        Params.ifStopDo(new ParamCallbackDefault() {
            public void call() {
                if (Manager.isOk()) {
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
