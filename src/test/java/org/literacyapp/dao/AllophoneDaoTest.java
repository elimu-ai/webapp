package org.literacyapp.dao;

import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.enums.content.allophone.SoundType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class AllophoneDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Test
    public void testStoreSoundType() {
        Locale locale = Locale.values()[(int) (Math.random() * Locale.values().length)];
        logger.info("locale: " + locale);
        
        Allophone allophone = new Allophone();
        allophone.setLocale(locale);
        allophone.setValueIpa("ɛ");
        allophone.setValueSampa("E");
        allophone.setSoundType(SoundType.VOWEL);
        allophoneDao.create(allophone);
        
        assertThat(allophoneDao.readByValueSampa(locale, "E").getSoundType(), is(SoundType.VOWEL));
    }
    
    @Test
    public void testLowerCaseVsUpperCase() {
        Locale locale = Locale.values()[(int) (Math.random() * Locale.values().length)];
        logger.info("locale: " + locale);
        
        Allophone allophoneLowerCaseT = new Allophone();
        allophoneLowerCaseT.setLocale(locale);
        allophoneLowerCaseT.setValueIpa("t");
        allophoneLowerCaseT.setValueSampa("t");
        allophoneDao.create(allophoneLowerCaseT);
        
        Allophone allophoneUpperCaseT = new Allophone();
        allophoneUpperCaseT.setLocale(locale);
        allophoneUpperCaseT.setValueIpa("θ");
        allophoneUpperCaseT.setValueSampa("T");
        allophoneDao.create(allophoneUpperCaseT);
        
        assertThat(allophoneDao.readByValueSampa(locale, "t").getValueSampa(), is("t"));
        assertThat(allophoneDao.readByValueSampa(locale, "T").getValueSampa(), is("T"));
    }
}
