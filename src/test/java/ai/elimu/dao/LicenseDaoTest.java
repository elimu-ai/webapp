package ai.elimu.dao;

import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.project.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class LicenseDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LicenseDao licenseDao;
    
    @Test
    public void testRead() {
        License license = new License();
        license.setLicenseEmail("info@elimu.ai");
        license.setLicenseNumber("bddf-d8f4-2adf-a365");
        
        License existingLicense = licenseDao.read(license.getLicenseEmail(), license.getLicenseNumber());
        assertThat(existingLicense, is(nullValue()));
        
        licenseDao.create(license);
        existingLicense = licenseDao.read(license.getLicenseEmail(), license.getLicenseNumber());
        assertThat(existingLicense.getLicenseEmail(), is("info@elimu.ai"));
        assertThat(existingLicense.getLicenseNumber(), is("bddf-d8f4-2adf-a365"));
    }
}
