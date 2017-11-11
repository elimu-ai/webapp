package ai.elimu.logic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LicenseGeneratorTest {

    @Test
    public void testGenerateLicenseNumber() {
        String licenseNumber = LicenseGenerator.generateLicenseNumber();
        assertThat(licenseNumber.length(), is(20));
    }
}
