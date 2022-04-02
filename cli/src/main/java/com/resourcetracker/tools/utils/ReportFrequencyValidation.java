package com.resourcetracker.tools.utils;

public class ReportFrequencyValidation {
    public patternMatches(String reportFrequency){
        if (reportFrequency.endsWith("d") || 
        reportFrequency.endsWith("w") ||
        reportFrequency.endsWith("m") ||
        reportFrequency.endsWith("h") ||
        reportFrequency.endsWith("s")){
            String rawDigit = reportFrequency.substring(0, reportFrequency.length()-1);
            try{
            Integer.parseInt(rawDigit);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
    }
}
