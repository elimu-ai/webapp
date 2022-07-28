package ai.elimu.dao;

import ai.elimu.model.content.Emoji;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class EmojiDaoTest extends TestCase {

    @Autowired
    private EmojiDao emojiDao;

    public void testReadAllOrdered() {
        List<Emoji> expectedEmojis = new ArrayList<>();
        expectedEmojis.add(getEmoji("A1F6"));
        expectedEmojis.add(getEmoji("C1F6"));
        expectedEmojis.add(getEmoji("D1F6"));
        expectedEmojis.add(getEmoji("F1F6"));
        expectedEmojis.add(getEmoji("M1F6"));

        emojiDao.create(getEmoji("F1F6"));
        emojiDao.create(getEmoji("A1F6"));
        emojiDao.create(getEmoji("C1F6"));
        emojiDao.create(getEmoji("D1F6"));
        emojiDao.create(getEmoji("M1F6"));

        List<Emoji> actualEmojis = emojiDao.readAllOrdered();

        Assert.assertArrayEquals(
                expectedEmojis.stream().map(Emoji::getGlyph).toArray(),
                actualEmojis.stream().map(Emoji::getGlyph).toArray()
        );
    }

    @Test
    public void testReadByGlyph() {
        emojiDao.create(getEmoji("F1F6"));
        assertTrue("F1F6".equals(emojiDao.readByGlyph("F1F6").getGlyph()));
    }

    private Emoji getEmoji(String glyph) {
        Emoji emoji = new Emoji();
        emoji.setGlyph(glyph);
        return emoji;
    }
}
