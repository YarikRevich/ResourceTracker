package com.resourcetracker.ut.tools.params;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.lang.reflect.Field;

import com.resourcetracker.tools.params.BooleanParam;

public class BooleanParamTest {
    @Test  
    public void testSetValue(){
        BooleanParam booleanParam = new BooleanParam(false);
        booleanParam.setValue(true);

        final Field field = booleanParam.getClass().getDeclaredField("value");
        field.setAccessible(true);
        assertTrue(field.get(booleanParam));
    }

    @Test  
    public void testGetValue(){
        final PlainOldJavaObject booleanParam = new PlainOldJavaObject();
        final Field field = booleanParam.getClass().getDeclaredField("value");
        field.setAccessible(true);
        field.set(booleanParam, true);

        assertTrue(booleanParam.getValue());
    }
}
