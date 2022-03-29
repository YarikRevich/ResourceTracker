package com.resourcetracker;

import com.resourcetracker.loop.Loop;

/**
 * Hello world!
 *
 */
public class Deployment
{
    public static void main( String[] args )
    {
        Loop.setContext();
        Loop.prerun();
        Loop.run();
    }
}
