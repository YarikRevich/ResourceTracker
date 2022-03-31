package com.resourcetracker.loop;

import com.resourcetracker.tools.params.Params;
import com.resourcetracker.tools.params.ParamCallbackDefault;
import com.resourcetracker.config.Config;
import com.resourcetracker.cloud.Manager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.springframework.boot.SpringApplication;

public class Loop {
    private static String[] args;

    final static Logger logger = LogManager.getLogger(Loop.class);

    public static void setContext(String[] args) {
        Loop.args = args;
        new Config();
    }

    public static void prerun() {
        Params.parse(args);
    }

    public static void run() throws Exception {
        Params.ifValidateDo(new ParamCallbackDefault() {
            public void call() throws Exception {
                if (Config.isValid()){
                    logger.info("config file is valid!");
                }else{
                    logger.info("config file is not valid!");
                }
            };
        });

        Params.ifStartDo(new ParamCallbackDefault() {
            public void call() throws Exception {
                if (!Manager.isOk()) {
                    Manager.start(Config.formatContext());
                } else {
                    logger.info("tracker is already started");
                }
            };
        });

        Params.ifStopDo(new ParamCallbackDefault() {
            public void call() throws Exception {
                if (Manager.isOk()) {
                    Manager.stop();
                } else {
                    logger.info("tracker is already stoped");
                }
            };
        });
    }
}
