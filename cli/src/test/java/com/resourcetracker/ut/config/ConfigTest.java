package com.resourcetracker.ut.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import con.resourcetracker.config.Config;

public class ConfigTest {
    final static String srcCorrect = """
            addresses: [

            ]
            """;

    final static String srcFail = """
            addresses: [

            ]
            """;

    @BeforeAll
    public static void setUp() {
        new Config(src);
    }

    @Test
    public void testIsValidTrue() {
        assertTrue(Config.isValid());
    }

    @Test
    public void testIsValidFalse() {
        assertFalse(Config.isValid());
    }
}
