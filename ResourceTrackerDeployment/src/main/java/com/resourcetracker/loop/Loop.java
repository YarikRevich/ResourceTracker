package com.resourcetracker.loop;

import com.resourcetracker.ticker.Ticker;
import com.resourcetracker.context.Context;

public class Loop {
    public static void setContext(){
        Context.init();
    }
    public static void prerun(){}
    public static void run(){
        while(true) {
            //TODO: main loop for tracking
            Ticker.wait();
        }
    }
}
