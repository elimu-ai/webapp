package ai.elimu.logic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LicenseGeneratorTest {

    @Test
    public void testFormat() {
        String licenseNumber = LicenseGenerator.generateLicenseNumber();
        
        assertThat(licenseNumber, not(nullValue()));
        
        // Verify that a separater has been inserted after each group of 4 characters
        assertThat(licenseNumber.length(), is(16 + 3));
        
        // Verify that the length is 16 after stripping away all separators
        assertThat(licenseNumber.replaceAll("-", "").length(), is(16));
    }
    
    /**
     * Test that different values are generated.
     */
    @Test
    public void testUniqueness() {
        String licenseNumber1 = LicenseGenerator.generateLicenseNumber();
        String licenseNumber2 = LicenseGenerator.generateLicenseNumber();
        
        assertThat(licenseNumber1, not(licenseNumber2));
    }
}
