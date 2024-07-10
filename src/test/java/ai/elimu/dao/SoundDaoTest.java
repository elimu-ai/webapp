package ai.elimu.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.entity.content.Sound;
import ai.elimu.model.v2.enums.content.sound.SoundType;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SoundDaoTest {

    @Autowired
    private SoundDao soundDao;

    @Test
    public void testAllOrderedRead() {
        List<Sound> expectedSounds = new ArrayList<>();
        expectedSounds.add(getSound("A", "A"));
        expectedSounds.add(getSound("B", "B"));
        expectedSounds.add(getSound("C", "C"));
        expectedSounds.add(getSound("D", "D"));
        expectedSounds.add(getSound("Z", "Z"));

        soundDao.create(getSound("Z", "Z"));
        soundDao.create(getSound("C", "C"));
        soundDao.create(getSound("B", "B"));
        soundDao.create(getSound("D", "D"));
        soundDao.create(getSound("A", "A"));

        List<Sound> actualSounds = soundDao.readAllOrdered();

        Assert.assertArrayEquals(
                expectedSounds.stream().map(Sound::getValueIpa).toArray(),
                actualSounds.stream().filter(i -> i.getValueIpa().length() == 1).map(Sound::getValueIpa).toArray()
        );
    }

    @Test
    public void readAllOrderedByIpaValueCharacterLength() {
        List<Sound> expectedSounds = new ArrayList<>();
        expectedSounds.add(getSound("ABC", "ABC"));
        expectedSounds.add(getSound("AB", "AB"));

        soundDao.create(getSound("AB", "AB"));
        soundDao.create(getSound("ABC", "ABC"));

        List<Sound> actualSounds = soundDao.readAllOrderedByIpaValueCharacterLength();

        Assert.assertArrayEquals(
                expectedSounds.stream().map(Sound::getValueIpa).toArray(),
                actualSounds.stream().map(Sound::getValueIpa).toArray()
        );
    }

    @Test
    public void testReadAllOrderedByUsage() {
        List<Sound> expectedSounds = new ArrayList<>();
        expectedSounds.add(getSoundWithUsage("A", "A", 100));
        expectedSounds.add(getSoundWithUsage("B", "B", 90));
        expectedSounds.add(getSoundWithUsage("C", "C", 50));
        expectedSounds.add(getSoundWithUsage("D", "D", 30));
        expectedSounds.add(getSoundWithUsage("F", "F", 10));

        soundDao.create(getSoundWithUsage("B", "B", 10));
        soundDao.create(getSoundWithUsage("C", "C", 50));
        soundDao.create(getSoundWithUsage("D", "D", 30));
        soundDao.create(getSoundWithUsage("A", "A", 100));
        soundDao.create(getSoundWithUsage("B", "B", 90));

        List<Sound> actualSounds = soundDao.readAllOrderedByUsage();

        Assert.assertArrayEquals(
                expectedSounds.stream().map(Sound::getUsageCount).toArray(),
                actualSounds.stream().filter(i -> i.getUsageCount() != 0).map(Sound::getUsageCount).toArray()
        );
    }

    @Test
    public void testStoreSoundType() {
        Sound sound = getSound("ɛ", "E");
        sound.setSoundType(SoundType.VOWEL);
        soundDao.create(sound);
        
        assertThat(soundDao.readByValueSampa("E").getSoundType(), is(SoundType.VOWEL));
        assertTrue("ɛ".equals(soundDao.readByValueIpa("ɛ").getValueIpa()));
    }
    
    @Test
    public void testLowerCaseVsUpperCase() {
        soundDao.create(getSound("t", "t"));
        soundDao.create(getSound("θ", "T"));
        
        assertThat(soundDao.readByValueSampa("t").getValueSampa(), is("t"));
        assertThat(soundDao.readByValueSampa("T").getValueSampa(), is("T"));
    }

    private Sound getSound(String ipa, String sampa) {
        Sound sound = new Sound();
        sound.setValueIpa(ipa);
        sound.setValueSampa(sampa);
        return sound;
    }

    private Sound getSoundWithUsage(String ipa, String sampa, Integer usage) {
        Sound sound = getSound(ipa, sampa);
        sound.setUsageCount(usage);
        return sound;
    }
}
