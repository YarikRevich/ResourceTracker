package com.resourcetrackersdk.tools.utils;

import java.util.TreeMap;
import java.util.Map;

/**
 * 
 * 
 * @author YarikRevich
 * 
 */
public class ConvertMapToTreeMap<T> {
    public TreeMap<String, T> value;

    @SuppressWarnings("unchecked")
    public ConvertMapToTreeMap(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("obj set to convert is null");
        }
        Map<String, Object> rawObj = null;
        try {
            rawObj = (Map<String, Object>) obj;
        } catch (Exception e) {
            
        }
        TreeMap<String, T> value = new TreeMap<String, T>();
        for (Map.Entry<String, Object> v : rawObj.entrySet()) {
            value.put(v.getKey(), (T) v.getValue());
        }
        this.value = value;
    };

    public TreeMap<String, T> getValue() {
        return this.value;
    }
}
