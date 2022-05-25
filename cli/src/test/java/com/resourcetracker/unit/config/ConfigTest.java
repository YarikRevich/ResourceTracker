package com.resourcetracker.ut.config;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

// import con.resourcetracker.config.Config;

public class ConfigTest {
    // final static String srcCorrect = """
    //         addresses:
    //             - tag: `it works`
    //               host: `https://google.com`
    //               paths: [`/`]
    //         cloud:
    //             provider: `aws`
    //             credentials: `~/.aws/credentials`
    //             profile: `default`
    //         mailing: `yariksvitlitskiy81@gmail.com`
    //         report_frequency: 5d
    //         """;

    // /**
    //  * Error -> there is no such available provider
    //  */
    // final static String srcFail_Cloud_Provider = """
    //         addresses:
    //             - tag: `it works`
    //               host: `https://google.com`
    //               paths: [`/`]
    //         cloud:
    //             provider: `oracle`
    //         mailing: `yariksvitlitskiy81@gmail.com`
    //         report_frequency: 5d
    //         """;

    // /**
    //  * Error -> mailing email is not correct
    //  */
    // final static String srcFail_Mailing = """
    //         addresses:
    //             - tag: `it works`
    //               host: `https://google.com`
    //               paths: [`/`]
    //         cloud:
    //             provider: `oracle`
    //         mailing: `yariksvitlitskiy81gmail.com`
    //         report_frequency: 5d
    //         """;

    // /**
    //  * Error -> mailing email is not correct
    //  */
    // final static String srcFail_ReportFrequency = """
    //         addresses:
    //             - tag: `it works`
    //               host: `https://google.com`
    //               paths: [`/`]
    //         cloud:
    //             provider: `oracle`
    //         mailing: `yariksvitlitskiy81@gmail.com`
    //         report_frequency: 5o
    //         """;

    // @Test
    // public void testIsValidCorrect() {
    //     Config.setSrc(srcCorrect);
    //     assertTrue(Config.isValid());
    // }

    // @Test
    // public void testIsValidFailed_Cloud_Provider() {
    //     Config.setSrc(srcFail_Cloud_Provider);
    //     assertFalse(Config.isValid());
    // }

    // @Test
    // public void testIsValidFailed_Mailing() {
    //     Config.setSrc(srcFail_Mailing);
    //     assertFalse(Config.isValid());
    // }

    // @Test
    // public void testIsValidFailed_ReportFrequency() {
    //     Config.setSrc(srcFail_ReportFrequency);
    //     assertFalse(Config.isValid());
    // }

    // @Test
    // public void testFormatContext() {
    //     // Config.setSrc();
    //     // assertFalse(Config.isValid());
    // }
}
