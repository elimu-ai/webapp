package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.*;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ImageComponent {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private LetterDao letterDao;

    @Autowired
    private NumberDao numberDao;

    @Autowired
    private WordDao wordDao;

    @Autowired
    private EmojiDao emojiDao;

    @Autowired
    private AudioDao audioDao;

    @Autowired
    private ImageContributionEventDao imageContributionEventDao;

    public void setImageModel(Model model, Image image) {
        model.addAttribute("image", image);
        model.addAttribute("contentLicenses", ContentLicense.values());
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());

        model.addAttribute("timeStart", System.currentTimeMillis());
        model.addAttribute("imageContributionEvents", imageContributionEventDao.readAll(image));

        model.addAttribute("letters", letterDao.readAllOrdered());
        model.addAttribute("numbers", numberDao.readAllOrdered());
        model.addAttribute("words", wordDao.readAllOrdered());
        model.addAttribute("emojisByWordId", getEmojisByWordId());

        Audio audio = audioDao.readByTranscription(image.getTitle());
        model.addAttribute("audio", audio);
    }

    private Map<Long, String> getEmojisByWordId() {
        logger.info("getEmojisByWordId");

        Map<Long, String> emojisByWordId = new HashMap<>();

        for (Word word : wordDao.readAll()) {
            String emojiGlyphs = "";

            List<Emoji> emojis = emojiDao.readAllLabeled(word);
            for (Emoji emoji : emojis) {
                emojiGlyphs += emoji.getGlyph();
            }

            if (StringUtils.isNotBlank(emojiGlyphs)) {
                emojisByWordId.put(word.getId(), emojiGlyphs);
            }
        }

        return emojisByWordId;
    }
}
