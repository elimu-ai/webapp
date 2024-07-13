package ai.elimu.web.content.word;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.content.Word;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/list")
public class WordCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;
    
    @RequestMapping(value="/words.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        List<Word> words = wordDao.readAllOrderedByUsage();
        logger.info("words.size(): " + words.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "text",
                        "letter_sound_correspondences",
                        "usage_count",
                        "word_type",
                        "spelling_consistency",
                        "root_word_id",
                        "root_word_text"
                );
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        
        for (Word word : words) {
            logger.info("word.getText(): \"" + word.getText() + "\"");
            
            JSONArray letterSoundCorrespondencesJsonArray = new JSONArray();
            int index = 0;
            for (LetterSoundCorrespondence letterSound : word.getLetterSounds()) {
                JSONObject letterSoundCorrespondenceJsonObject = new JSONObject();
                letterSoundCorrespondenceJsonObject.put("id", letterSound.getId());
                String[] lettersArray = new String[letterSound.getLetters().size()];
                for (int i = 0; i < lettersArray.length; i++) {
                    lettersArray[i] = letterSound.getLetters().get(i).getText();
                }
                letterSoundCorrespondenceJsonObject.put("letters", lettersArray);
                String[] soundsArray = new String[letterSound.getSounds().size()];
                for (int i = 0; i < soundsArray.length; i++) {
                    soundsArray[i] = letterSound.getSounds().get(i).getValueIpa();
                }
                letterSoundCorrespondenceJsonObject.put("sounds", soundsArray);
                letterSoundCorrespondenceJsonObject.put("usageCount", letterSound.getUsageCount());
                letterSoundCorrespondencesJsonArray.put(index, letterSoundCorrespondenceJsonObject);
                index++;
            }
            
            Long rootWordId = null;
            String rootWordText = null;
            if (word.getRootWord() != null) {
                rootWordId = word.getRootWord().getId();
                rootWordText = word.getRootWord().getText();
            }
            
            csvPrinter.printRecord(
                    word.getId(),
                    word.getText(),
                    letterSoundCorrespondencesJsonArray,
                    word.getUsageCount(),
                    word.getWordType(),
                    word.getSpellingConsistency(),
                    rootWordId,
                    rootWordText
            );
            
            csvPrinter.flush();
        }
        
        String csvFileContent = stringWriter.toString();
        
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
