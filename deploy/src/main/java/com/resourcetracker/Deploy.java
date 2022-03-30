package com.resourcetracker;

import com.resourcetracker.loop.Loop;

/**
 * Hello world!
 *
 */
public class Deploy
{
    public static void main( String[] args )
    {
        Loop.setContext();
        Loop.prerun();
        Loop.run();
    }
}
