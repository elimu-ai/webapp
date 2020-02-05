package ai.elimu.web.content.emoji;

import ai.elimu.dao.EmojiDao;
import ai.elimu.model.content.Emoji;
import java.io.IOException;
import java.io.OutputStream;
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
        String csvFileContent = "id,glyph,unicode_version,unicode_emoji_version" + "\n";
        List<Emoji> emojis = emojiDao.readAll();
        logger.info("emojis.size(): " + emojis.size());
        for (Emoji emoji : emojis) {
            csvFileContent += emoji.getId() + ","
                    + emoji.getGlyph() + ","
                    + emoji.getUnicodeVersion() + ","
                    + emoji.getUnicodeEmojiVersion() + "\n";
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
