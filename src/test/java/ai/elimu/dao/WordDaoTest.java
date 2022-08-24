package ai.elimu.dao;

import ai.elimu.model.content.Word;
import ai.elimu.model.v2.enums.content.WordType;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class WordDaoTest extends TestCase {

    @Autowired
    private WordDao wordDao;

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

        assertThat(wordDao.readByTextAndType(WORD_TEXT, WordType.ADJECTIVE).getWordType(), is(WordType.ADJECTIVE));
        assertThat(wordDao.readByTextAndType(WORD_TEXT, WordType.ADVERB).getWordType(), is(WordType.ADVERB));
        assertThat(wordDao.readByTextAndType(WORD_TEXT, WordType.NOUN).getWordType(), is(WordType.NOUN));
        assertThat(wordDao.readByTextAndType(WORD_TEXT, WordType.NUMBER).getWordType(), is(WordType.NUMBER));
        assertThat(wordDao.readByTextAndType(WORD_TEXT, WordType.PREPOSITION).getWordType(), is(WordType.PREPOSITION));
        assertThat(wordDao.readByTextAndType(WORD_TEXT, WordType.PRONOUN).getWordType(), is(WordType.PRONOUN));
        assertThat(wordDao.readByTextAndType(WORD_TEXT, WordType.VERB).getWordType(), is(WordType.VERB));
        assertNull(wordDao.readByTextAndType(WORD_TEXT, null).getWordType());
    }

    private Word getWordWitTheSameText(WordType wordType) {
        Word word = new Word();
        word.setText(WORD_TEXT);
        word.setWordType(wordType);
        return word;
    }
}
