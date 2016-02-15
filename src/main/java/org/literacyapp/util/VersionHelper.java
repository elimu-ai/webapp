package org.literacyapp.util;

/**
 * Helper class for extracting Integer version of String version in pom.xml
 */
public class VersionHelper {

    /**
     * E.g. "1.2.3-SNAPSHOT" --> "100200300"
     */
    public static Integer getPomVersionAsInteger(String pomVersion) {
        Integer version = null;

        if (pomVersion.endsWith("-SNAPSHOT")) {
            pomVersion = pomVersion.replace("-SNAPSHOT", "");
        }
        
        String[] numbers = pomVersion.split("\\.");
        
        String versionMajor = numbers[0];
        if (versionMajor.length() == 1) {
            versionMajor = versionMajor + "00";
        } else if (versionMajor.length() == 2) {
            versionMajor = versionMajor + "0";
        }
        
        String versionMinor = numbers[1];
        if (versionMinor.length() == 1) {
            versionMinor = versionMinor + "00";
        } else if (versionMinor.length() == 2) {
            versionMinor = versionMinor + "0";
        }
        
        String versionIncremental = numbers[2];
        if (versionIncremental.length() == 1) {
            versionIncremental = versionIncremental + "00";
        } else if (versionIncremental.length() == 2) {
            versionIncremental = versionIncremental + "0";
        }
        
        version = new Integer(versionMajor + versionMinor + versionIncremental);
        
        return version;
    }
}
