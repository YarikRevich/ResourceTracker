package com.resourcetracker.ticker;

/**
 * 
 * @author YarikRevich
 */
public class Ticker {
    public static void waitInRuntime(int timeout){
        System.sleep(timeout);
    }

    public static void wait(int timeout){

    }

    public static boolean isWaiting(){
        return false;
    }
}
