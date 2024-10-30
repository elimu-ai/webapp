package ai.elimu.util;

/**
 * Utility class for handling CSV files uploaded by the Analytics application: https://github.com/elimu-ai/analytics
 */
public class AnalyticsHelper {
    
    /**
     * E.g."7161a85a0e4751cd_3001012_word-learning-events_2020-04-23.csv" --> "7161a85a0e4751cd"
     * 
     * @param filename The name of the CSV file
     * @return The Android ID
     */
    public static String extractAndroidIdFromCsvFilename(String filename) {
       int indexOfFirstUnderscore = filename.indexOf("_");
       String androidId = filename.substring(0, indexOfFirstUnderscore);
       return androidId;
    }
    
    /**
     * E.g."7161a85a0e4751cd_3001012_word-learning-events_2020-04-23.csv" --> 3001012
     * 
     * @param filename The name of the CSV file
     * @return The {@code versionCode} of the Analytics app that was used when generating the original CSV file.
     */
    public static Integer extractVersionCodeFromCsvFilename(String filename) {
       int indexOfFirstUnderscore = filename.indexOf("_");
       int indexOfSecondUnderscore = filename.indexOf("_", indexOfFirstUnderscore + 1);
       String versionCodeAsString = filename.substring(indexOfFirstUnderscore + 1, indexOfSecondUnderscore);
       Integer versionCode = Integer.valueOf(versionCodeAsString);
       return versionCode;
    }

    /**
     * E.g. "7161a85a0e4751cd" --> "7161***51cd"
     * 
     * @param androidId The Android ID
     * @return The redacted version of the Android ID
     */
    public static String redactAndroidId(String androidId) {
      return androidId.substring(0, 4) + "***" + androidId.substring(12);
    }
}
