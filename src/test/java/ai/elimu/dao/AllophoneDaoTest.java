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
    private SoundDao soundDao;
    
    @Test
    public void testStoreSoundType() {
        Allophone sound = new Allophone();
        sound.setValueIpa("ɛ");
        sound.setValueSampa("E");
        sound.setSoundType(SoundType.VOWEL);
        soundDao.create(sound);
        
        assertThat(soundDao.readByValueSampa("E").getSoundType(), is(SoundType.VOWEL));
    }
    
    @Test
    public void testLowerCaseVsUpperCase() {
        Allophone soundLowerCaseT = new Allophone();
        soundLowerCaseT.setValueIpa("t");
        soundLowerCaseT.setValueSampa("t");
        soundDao.create(soundLowerCaseT);
        
        Allophone soundUpperCaseT = new Allophone();
        soundUpperCaseT.setValueIpa("θ");
        soundUpperCaseT.setValueSampa("T");
        soundDao.create(soundUpperCaseT);
        
        assertThat(soundDao.readByValueSampa("t").getValueSampa(), is("t"));
        assertThat(soundDao.readByValueSampa("T").getValueSampa(), is("T"));
    }
}
