package ai.elimu.dao;

import org.apache.logging.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.v2.enums.content.allophone.SoundType;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class AllophoneDaoTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Test
    public void testStoreSoundType() {
        Allophone allophone = new Allophone();
        allophone.setValueIpa("ɛ");
        allophone.setValueSampa("E");
        allophone.setSoundType(SoundType.VOWEL);
        allophoneDao.create(allophone);
        
        assertThat(allophoneDao.readByValueSampa("E").getSoundType(), is(SoundType.VOWEL));
    }
    
    @Test
    public void testLowerCaseVsUpperCase() {
        Allophone allophoneLowerCaseT = new Allophone();
        allophoneLowerCaseT.setValueIpa("t");
        allophoneLowerCaseT.setValueSampa("t");
        allophoneDao.create(allophoneLowerCaseT);
        
        Allophone allophoneUpperCaseT = new Allophone();
        allophoneUpperCaseT.setValueIpa("θ");
        allophoneUpperCaseT.setValueSampa("T");
        allophoneDao.create(allophoneUpperCaseT);
        
        assertThat(allophoneDao.readByValueSampa("t").getValueSampa(), is("t"));
        assertThat(allophoneDao.readByValueSampa("T").getValueSampa(), is("T"));
    }
}
