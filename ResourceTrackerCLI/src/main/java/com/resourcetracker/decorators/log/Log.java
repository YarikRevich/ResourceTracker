package com.resourcetracker.decorators.log;

import org.javatuples.Pair;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;

/**
 * Overrides log4j xml config and applies credentails to
 * aws profile
 * 
 * @author YarikRevich
 */
public class Log {
    private final static Logger _wrappee = LogManager.getLogger(Log.class);

    /**
     * 
     * @param credentials credentials to aws profile, first is 'aws_key', and the
     *                    second is 'aws_secret'
     */
    public static void activateCloudBackuping(Pair<String, String> credentials) {
        ThreadContext.put("s3AwsKey", credentials.getValue0());
        ThreadContext.put("s3AwsSecret", credentials.getValue1());
    };

    /**
     * Cleans log configuration file from s3 appender
     */
    public static void cleanCloudBackuping(){
        ThreadContext.remove("Log4j2Appender");
    }
}
