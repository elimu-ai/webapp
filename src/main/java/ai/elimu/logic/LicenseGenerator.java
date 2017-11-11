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
    
    /**
     * Ambiguous characters '0' an '1' are not included.
     */
    private static final String VALID_CHARACTERS = "abcdef23456789";
    
    /**
     * Generate license number on the format "bddf-d8f4-2adf-a365".
     */
    public static String generateLicenseNumber() {
        String licenseNumber = "";
        
        for (int i = 0; i < LICENSE_LENGTH; i++) {
            // Add separator for increased readability
            if (i < VALID_CHARACTERS.length()) {
                // Add separator between every group of 4 characters
                if ((i > 0) && (i % 4 == 0)) {
                    licenseNumber += "-";
                }
            }
            
            int randomIndex = (int) (Math.random() * VALID_CHARACTERS.length());
            String randomCharacter = VALID_CHARACTERS.substring(randomIndex, randomIndex + 1);
            licenseNumber += randomCharacter;
        }
        
        return licenseNumber;
    }
}
