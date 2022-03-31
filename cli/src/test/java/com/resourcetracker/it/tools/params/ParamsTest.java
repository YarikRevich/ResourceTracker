package com.resourcetracker.it.tools.params;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import com.resourcetracker.tools.params.Params;

public class ParamsTest {
    @BeforeAll
    public static void setUpOnce(){
        Params.parse(new String[]{"validate", "start", "stop"});
    }

    @Test
    public void canIfValid() throws Exception {
        if (!Params.ifValidateDo(new ParamCallbackDefault() {
            public void call() throws Exception {
                assertTrue(true);
            };
        })){
            fail();
        };
    }

    @Test
    public void canIfStart() throws Exception {
        if (!Params.ifStartDo(new ParamCallbackDefault() {
            public void call() throws Exception {
                assertTrue(true);
            };
        })){
            fail();
        };
    }

    @Test
    public void canIfStop() throws Exception {
        if (!Params.ifStopDo(new ParamCallbackDefault() {
            public void call() throws Exception {
                assertTrue(true);
            };
        })){
            fail();
        };
    }
}
