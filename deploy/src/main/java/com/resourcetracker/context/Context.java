package com.resourcetracker.context;

import java.util.TreeMap;
import org.java.JSONObject;
import org.java.JSONArray;

import com.resourcetracker.entities.Entity;
import java.time.LocalTime;

public class Context {
    private static TreeMap<String, Entity> addresses = new TreeMap<String, Entity>();
    private static LocalTime reportFrequency;

    static {
        String context = System.getenv("RESOURCETRACKER_CONTEXT");

        JSONObject obj = new JSONObject(context);
        JSONArray arr = obj.getJSONArray("addresses");
        for (int i = 0; i < arr1.length(); i++) {
            String tag = arr.getJSONObject(i).getString("tag");
            String host = arr.getJSONObject(i).getString("host");
            ArrayList<String> schemas = new ArrayList<String>();
            String pathes = arr.getJSONArray(i);
            for (int q = 0; q < pathes.length(); q++) {
                schemas.add(host + pathes.getJSONObject(q).toString());
            }
            Context.addresses.put(tag, new Entity(i, schemas));
        }
    }

    public static TreeMap<String, Entity> getAddresses(){
        return addresses;
    }

    /**
     * 
     * @return frequency in milliseconds
     */
    public static int getReportFrequency() {
        return reportFrequency;
    }
}
