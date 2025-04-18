package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ai.elimu.entity.content.Word;
import ai.elimu.entity.content.multimedia.Image;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@Slf4j
public class ImageDaoTest {

  private final WordDao wordDao;

  private final ImageDao imageDao;

  @Autowired
  public ImageDaoTest(WordDao wordDao, ImageDao imageDao) {
    this.wordDao = wordDao;
    this.imageDao = imageDao;
  }

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
