package com.resourcetracker.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import con.resourcetracker.config.Config;

public class ConfigTest {
    final static String src = """

            """;

    @BeforeAll
    public static void setUp() {
        Config.setSrc(src);
    }

    @Test
    public void IntegrationConfigTest() {
        assertTrue(Config.isValid());
        
    }
}
