package ai.elimu.util;

/**
 * Helper class for extracting Integer version of String version in pom.xml
 */
public class VersionHelper {

    /**
     * E.g. "1.2.3-SNAPSHOT" --> "1002003"
     */
    public static Integer getPomVersionAsInteger(String pomVersion) {
        Integer version = null;

        if (pomVersion.endsWith("-SNAPSHOT")) {
            pomVersion = pomVersion.replace("-SNAPSHOT", "");
        }
        
        String[] numbers = pomVersion.split("\\.");
        
        String versionMajor = numbers[0];
        
        String versionMinor = numbers[1];
        if (versionMinor.length() == 1) {
            versionMinor = "00" + versionMinor;
        } else if (versionMinor.length() == 2) {
            versionMinor = "0" + versionMinor;
        }
        
        String versionIncremental = numbers[2];
        if (versionIncremental.length() == 1) {
            versionIncremental = "00" + versionIncremental;
        } else if (versionIncremental.length() == 2) {
            versionIncremental = "0" + versionIncremental;
        }
        
        version = new Integer(versionMajor + versionMinor + versionIncremental);
        
        return version;
    }
}
