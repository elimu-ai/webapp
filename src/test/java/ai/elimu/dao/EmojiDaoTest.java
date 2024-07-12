package ai.elimu.dao;

import ai.elimu.model.content.Emoji;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@TestMethodOrder(MethodName.class)
public class EmojiDaoTest {

    @Autowired
    private EmojiDao emojiDao;

    @BeforeEach
    void cleanupDao() {
        var emojiList = emojiDao.readAll();
        for (var emoji : emojiList) {
            emojiDao.delete(emoji);
        }
    }

    @Test
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

        assertArrayEquals(
                expectedEmojis.stream().map(Emoji::getGlyph).toArray(),
                actualEmojis.stream().map(Emoji::getGlyph).toArray()
        );
    }

    @Test
    public void testReadByGlyph() {
        emojiDao.create(getEmoji("F1F6"));
        assertEquals("F1F6", emojiDao.readByGlyph("F1F6").getGlyph());
    }

    private Emoji getEmoji(String glyph) {
        Emoji emoji = new Emoji();
        emoji.setGlyph(glyph);
        return emoji;
    }
}
