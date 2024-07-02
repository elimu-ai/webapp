package ai.elimu.web.content.syllable;

import ai.elimu.dao.SyllableDao;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Syllable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/syllable/list")
public class SyllableCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private SyllableDao syllableDao;
    
    @RequestMapping(value="/syllables.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        // Generate CSV file
        String csvFileContent = "id,text,sound_ids,usage_count" + "\n";
        List<Syllable> syllables = syllableDao.readAllOrderedByUsage();
        logger.info("syllables.size(): " + syllables.size());
        for (Syllable syllable : syllables) {
            long[] soundIdsArray = new long[syllable.getSounds().size()];
            int index = 0;
            for (Sound sound : syllable.getSounds()) {
                soundIdsArray[index] = sound.getId();
                index++;
            }
            csvFileContent += syllable.getId() + ","
                    + "\"" + syllable.getText() + "\","
                    + Arrays.toString(soundIdsArray) + ","
                    + syllable.getUsageCount() + "\n";
        }
        
        response.setContentType("text/csv");
        byte[] bytes = csvFileContent.getBytes();
        response.setContentLength(bytes.length);
        try {
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            logger.error(ex);
        }
    }
}
