package ai.elimu.util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Expected format: id:163|deviceId:2312aff4939750ea|time:1496843219926|packageName:ai.elimu.nyaqd|studentId:2312aff4939750ea_4
 */
public class EventLineHelper {
    
    private static String[] getEventLineValues(String eventLine) {
        return eventLine.split("\\|");
    }

    public static String getDeviceId(String eventLine) {
        String deviceId = null;
        
        String[] eventLineValues = getEventLineValues(eventLine);
        for (int i = 0; i < eventLineValues.length; i++) {
            String eventLineValue = eventLineValues[i];
            if (eventLineValue.startsWith("deviceId:")) {
                deviceId = eventLineValue.replace("deviceId:", "");
            }
        }
        
        return deviceId;
    }
    
    public static Calendar getTime(String eventLine) {
        Calendar calendar = null;
        
        String[] eventLineValues = getEventLineValues(eventLine);
        for (int i = 0; i < eventLineValues.length; i++) {
            String eventLineValue = eventLineValues[i];
            if (eventLineValue.startsWith("time:")) {
                String timeAsString = eventLineValue.replace("time:", "");
                long timeInMillis = Long.parseLong(timeAsString);
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeInMillis);
            }
        }
        
        return calendar;
    }
    
    public static String getPackageName(String eventLine) {
        String packageName = null;
        
        String[] eventLineValues = getEventLineValues(eventLine);
        for (int i = 0; i < eventLineValues.length; i++) {
            String eventLineValue = eventLineValues[i];
            if (eventLineValue.startsWith("packageName:")) {
                packageName = eventLineValue.replace("packageName:", "");
            }
        }
        
        return packageName;
    }
    
    public static String getUniqueStudentId(String eventLine) {
        String uniqueStudentId = null;
        
        String[] eventLineValues = getEventLineValues(eventLine);
        for (int i = 0; i < eventLineValues.length; i++) {
            String eventLineValue = eventLineValues[i];
            if (eventLineValue.startsWith("studentId:")) {
                uniqueStudentId = eventLineValue.replace("studentId:", "");
            }
        }
        
        return uniqueStudentId;
    }
}
