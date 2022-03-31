package com.resourcetracker.tools.params;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import com.resourcetracker.tools.params.Params;

public class ParamsTest {
    @BeforeAll
    public static void setUpOnce(){
        Params.parse(new String[]{"validate", "start", "stop"});
    }

    @Test
    public void testIfValid() throws Exception {
        boolean ok = Params.ifValidateDo(new ParamCallbackDefault() {
            public void call() throws Exception {
                assertTrue(true);
            };
        });
        if (!ok){
            fail();
        }
    }

    @Test
    public void testIfStart() throws Exception {
        
        boolean ok = Params.ifStartDo(new ParamCallbackDefault() {
            public void call() throws Exception {
                assertTrue(true);
            };
        });
        if (!ok){
            fail();
        }
    }

    @Test
    public void testIfStop() throws Exception {
        boolean ok = Params.ifStopDo(new ParamCallbackDefault() {
            public void call() throws Exception {
                assertTrue(true);
            };
        });
        if (!ok){
            fail();
        }
    }
}
