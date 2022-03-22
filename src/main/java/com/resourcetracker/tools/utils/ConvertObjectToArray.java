package com.resourcetracker.tools.utils;

import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * 
 * @author YarikRevich
 * 
 */
public class ConvertObjectToArray<T> {
    public ArrayList<T> value;

    @SuppressWarnings("unchecked")
    public ConvertObjectToArray(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("obj set to convert is null");
        }
        ArrayList<T> rawObj = null;
        try {
            rawObj = (ArrayList<T>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.value = rawObj;
    };

    public ArrayList<T> getValue() {
        return this.value;
    }
}
