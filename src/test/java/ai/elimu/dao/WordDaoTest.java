package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import ai.elimu.entity.content.Word;
import ai.elimu.model.v2.enums.content.WordType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class WordDaoTest {

  private final WordDao wordDao;

  @Autowired
  public WordDaoTest(WordDao wordDao) {
    this.wordDao = wordDao;
  }

  private final String WORD_TEXT = "ExampleWord";

  @Test
  public void testStoreTheSameWordWithOtherType() {
    wordDao.create(getWordWitTheSameText(WordType.ADJECTIVE));
    wordDao.create(getWordWitTheSameText(WordType.ADVERB));
    wordDao.create(getWordWitTheSameText(WordType.NOUN));
    wordDao.create(getWordWitTheSameText(WordType.NUMBER));
    wordDao.create(getWordWitTheSameText(WordType.PREPOSITION));
    wordDao.create(getWordWitTheSameText(WordType.PRONOUN));
    wordDao.create(getWordWitTheSameText(WordType.VERB));
    wordDao.create(getWordWitTheSameText(null));

    assertEquals(WordType.ADJECTIVE, wordDao.readByTextAndType(WORD_TEXT, WordType.ADJECTIVE).getWordType());
    assertEquals(WordType.ADVERB, wordDao.readByTextAndType(WORD_TEXT, WordType.ADVERB).getWordType());
    assertEquals(WordType.NOUN, wordDao.readByTextAndType(WORD_TEXT, WordType.NOUN).getWordType());
    assertEquals(WordType.NUMBER, wordDao.readByTextAndType(WORD_TEXT, WordType.NUMBER).getWordType());
    assertEquals(WordType.PREPOSITION, wordDao.readByTextAndType(WORD_TEXT, WordType.PREPOSITION).getWordType());
    assertEquals(WordType.PRONOUN, wordDao.readByTextAndType(WORD_TEXT, WordType.PRONOUN).getWordType());
    assertEquals(WordType.VERB, wordDao.readByTextAndType(WORD_TEXT, WordType.VERB).getWordType());
    assertNull(wordDao.readByTextAndType(WORD_TEXT, null).getWordType());
  }

  private Word getWordWitTheSameText(WordType wordType) {
    Word word = new Word();
    word.setText(WORD_TEXT);
    word.setWordType(wordType);
    return word;
  }
}
