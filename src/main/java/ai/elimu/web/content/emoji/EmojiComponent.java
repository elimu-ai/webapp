package ai.elimu.web.content.emoji;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Word;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmojiComponent {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private WordDao wordDao;

    @Autowired
    private EmojiDao emojiDao;

    public Map<Long, String> getEmojisByWordId() {
        logger.info("getEmojisByWordId");

        Map<Long, String> emojisByWordId = new HashMap<>();

        for (Word word : wordDao.readAll()) {
            String emojiGlyphs = "";

            for (Emoji emoji : emojiDao.readAllLabeled(word)) {
                emojiGlyphs += emoji.getGlyph();
            }

            if (StringUtils.isNotBlank(emojiGlyphs)) {
                emojisByWordId.put(word.getId(), emojiGlyphs);
            }
        }

        return emojisByWordId;
    }

}
