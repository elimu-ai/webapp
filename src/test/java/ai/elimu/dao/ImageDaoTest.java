package ai.elimu.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class ImageDaoTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;
	
    @Autowired
    private ImageDao imageDao;

    @Test
    public void testReadAllLabeled() {
        Word wordDog = new Word();
        wordDog.setText("dog");
        wordDao.create(wordDog);
        
        Word wordCat = new Word();
        wordCat.setText("cat");
        wordDao.create(wordCat);
        
        List<Image> images = imageDao.readAllLabeled(wordCat);
        assertThat(images.size(), is(0));
        
        Set<Word> words = new HashSet<>();
        words.add(wordCat);
        
        Image image = new Image();
        image.setTitle("image");
        image.setWords(words);
        imageDao.create(image);
        
        images = imageDao.readAllLabeled(wordDog);
        assertThat(images.size(), is(0));
        
        images = imageDao.readAllLabeled(wordCat);
        assertThat(images.size(), is(1));
        assertThat(images.get(0).getWords().size(), is(1));
        
        words.add(wordDog);
        image.setWords(words);
        imageDao.update(image);
        
        images = imageDao.readAllLabeled(wordCat);
        assertThat(images.size(), is(1));
        assertThat(images.get(0).getWords().size(), is(2));
    }
}
