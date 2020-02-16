package ai.elimu.web.content.emoji;

import ai.elimu.dao.EmojiDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/emoji/list")
public class EmojiCsvExportController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private EmojiDao emojiDao;
    
    @RequestMapping(value="/emojis.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        // Generate CSV file
        String csvFileContent = "id,glyph,unicode_version,unicode_emoji_version,word_texts,word_ids" + "\n";
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<Emoji> emojis = emojiDao.readAllOrdered(language);
        logger.info("emojis.size(): " + emojis.size());
        for (Emoji emoji : emojis) {
            logger.info("emoji.getGlyph(): \"" + emoji.getGlyph() + "\"");
            
            String[] wordTextsArray = new String[emoji.getWords().size()];
            int index = 0;
            for (Word word : emoji.getWords()) {
                wordTextsArray[index] = word.getText();
                index++;
            }
            
            long[] wordIdsArray = new long[emoji.getWords().size()];
            index = 0;
            for (Word word : emoji.getWords()) {
                wordIdsArray[index] = word.getId();
                index++;
            }
            
            csvFileContent += emoji.getId() + ","
                    + emoji.getGlyph() + ","
                    + emoji.getUnicodeVersion() + ","
                    + emoji.getUnicodeEmojiVersion() + ","
                    + Arrays.toString(wordTextsArray) + ","
                    + Arrays.toString(wordIdsArray) + "\n";
        }
        
        response.setContentType("text/csv");
        byte[] bytes = csvFileContent.getBytes();
        response.setContentLength(bytes.length);
        try {
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            logger.error(null, ex);
        }
    }
}
