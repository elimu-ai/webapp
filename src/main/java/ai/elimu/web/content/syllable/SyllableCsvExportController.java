package ai.elimu.web.content.syllable;

import ai.elimu.dao.SyllableDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Syllable;
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
@RequestMapping("/content/syllable/list")
public class SyllableCsvExportController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private SyllableDao syllableDao;
    
    @RequestMapping(value="/syllables.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        // Generate CSV file
        String csvFileContent = "id,text,allophone_ids,usage_count" + "\n";
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<Syllable> syllables = syllableDao.readAllOrderedByUsage(language);
        logger.info("syllables.size(): " + syllables.size());
        for (Syllable syllable : syllables) {
            long[] allophoneIdsArray = new long[syllable.getAllophones().size()];
            int index = 0;
            for (Allophone allophone : syllable.getAllophones()) {
                allophoneIdsArray[index] = allophone.getId();
                index++;
            }
            csvFileContent += syllable.getId() + ","
                    + "\"" + syllable.getText() + "\","
                    + Arrays.toString(allophoneIdsArray) + ","
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
            logger.error(null, ex);
        }
    }
}
