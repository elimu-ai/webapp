package ai.elimu.dao;

import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.allophone.SoundType;
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
        Language language = Language.values()[(int) (Math.random() * Language.values().length)];
        logger.info("language: " + language);
        
        Allophone allophone = new Allophone();
        allophone.setLanguage(language);
        allophone.setValueIpa("ɛ");
        allophone.setValueSampa("E");
        allophone.setSoundType(SoundType.VOWEL);
        allophoneDao.create(allophone);
        
        assertThat(allophoneDao.readByValueSampa("E").getSoundType(), is(SoundType.VOWEL));
    }
    
    @Test
    public void testLowerCaseVsUpperCase() {
        Language language = Language.values()[(int) (Math.random() * Language.values().length)];
        logger.info("language: " + language);
        
        Allophone allophoneLowerCaseT = new Allophone();
        allophoneLowerCaseT.setLanguage(language);
        allophoneLowerCaseT.setValueIpa("t");
        allophoneLowerCaseT.setValueSampa("t");
        allophoneDao.create(allophoneLowerCaseT);
        
        Allophone allophoneUpperCaseT = new Allophone();
        allophoneUpperCaseT.setLanguage(language);
        allophoneUpperCaseT.setValueIpa("θ");
        allophoneUpperCaseT.setValueSampa("T");
        allophoneDao.create(allophoneUpperCaseT);
        
        assertThat(allophoneDao.readByValueSampa("t").getValueSampa(), is("t"));
        assertThat(allophoneDao.readByValueSampa("T").getValueSampa(), is("T"));
    }
}
