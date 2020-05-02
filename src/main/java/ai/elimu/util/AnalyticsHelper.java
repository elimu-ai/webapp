package ai.elimu.util;

public class AnalyticsHelper {
    
    /**
     * E.g. "7161a85a0e4751cd_word-learning-events_2020-04-23.csv" --> "7161a85a0e4751cd"
     */
    public static String extractAndroidIdFromCsvFilename(String filename) {
       int indexOfFirstUnderscore = filename.indexOf("_");
       String androidId = filename.substring(0, indexOfFirstUnderscore);
       return androidId;
    }
}
