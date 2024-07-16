package ai.elimu.dao;

import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(locations = {
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
        assertTrue(images.isEmpty());
        
        Set<Word> words = new HashSet<>();
        words.add(wordCat);
        
        Image image = new Image();
        image.setTitle("image");
        image.setWords(words);
        imageDao.create(image);
        
        images = imageDao.readAllLabeled(wordDog);
        assertTrue(images.isEmpty());
        
        images = imageDao.readAllLabeled(wordCat);
        assertEquals(1, images.size());
        assertEquals(1, images.get(0).getWords().size());
        
        words.add(wordDog);
        image.setWords(words);
        imageDao.update(image);
        
        images = imageDao.readAllLabeled(wordCat);
        assertEquals(1, images.size());
        assertEquals(2, images.get(0).getWords().size());
    }
}
