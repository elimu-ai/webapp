package ai.elimu.dao;

import ai.elimu.dao.ImageDao;
import ai.elimu.dao.WordDao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.Device;
import ai.elimu.model.Student;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class ImageDaoTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
	
    @Autowired
    private ImageDao imageDao;

    @Test
    public void testReadAllLabeled() {
        Word wordDog = new Word();
        wordDog.setText("dog");
        wordDog.setLocale(Locale.EN);
        wordDao.create(wordDog);
        
        Word wordCat = new Word();
        wordCat.setText("cat");
        wordCat.setLocale(Locale.EN);
        wordDao.create(wordCat);
        
        List<Image> images = imageDao.readAllLabeled(wordCat, Locale.EN);
        assertThat(images.size(), is(0));
        
        Set<Word> words = new HashSet<>();
        words.add(wordCat);
        
        Image image = new Image();
        image.setTitle("image");
        image.setWords(words);
        image.setLocale(Locale.EN);
        imageDao.create(image);
        
        images = imageDao.readAllLabeled(wordDog, Locale.EN);
        assertThat(images.size(), is(0));
        
        images = imageDao.readAllLabeled(wordCat, Locale.EN);
        assertThat(images.size(), is(1));
        assertThat(images.get(0).getWords().size(), is(1));
        
        words.add(wordDog);
        image.setWords(words);
        imageDao.update(image);
        
        images = imageDao.readAllLabeled(wordCat, Locale.EN);
        assertThat(images.size(), is(1));
        assertThat(images.get(0).getWords().size(), is(2));
    }
}
