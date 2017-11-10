package ai.elimu.logic;

import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.Project;

/**
 * Utility class for generating license numbers to be used for unlocking access to {@link AppCollection}s 
 * in custom {@link Project}s
 */
public class LicenseGenerator {
    
    private static final int LICENSE_LENGTH = 16;
    
    private static final String GROUP_SEPARATOR = "-";
    
    private static final String[] VALID_LETTERS = new String[]{"a", "b", "c", "d", "e", "f"};
    
    private static final int[] VALID_NUMBERS = new int[]{2, 3, 4, 5, 6, 7, 8, 9};
    
    public static String generateLicenseNumber() {
        String licenseNumber = null;
        
        // TODO
        
        return licenseNumber;
    }
}
