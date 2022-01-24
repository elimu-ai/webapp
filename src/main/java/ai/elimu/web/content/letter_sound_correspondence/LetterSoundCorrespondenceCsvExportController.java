package ai.elimu.web.content.letter_sound_correspondence;

import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
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
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.LetterSoundCorrespondenceDao;

@Controller
@RequestMapping("/content/letter-sound-correspondence/list")
public class LetterSoundCorrespondenceCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundCorrespondenceDao letterSoundCorrespondenceDao;
    
    @RequestMapping(value="/letter-sound-correspondences.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        List<LetterSoundCorrespondence> letterSoundCorrespondences = letterSoundCorrespondenceDao.readAllOrderedByUsage();
        logger.info("letterSoundCorrespondences.size(): " + letterSoundCorrespondences.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "letter_ids",
                        "letter_texts",
                        "sound_ids",
                        "sound_values_ipa",
                        "usage_count"
                );
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        
        for (LetterSoundCorrespondence letterSoundCorrespondence : letterSoundCorrespondences) {
            logger.info("letterSoundCorrespondence.getId(): \"" + letterSoundCorrespondence.getId() + "\"");
            
            JSONArray letterIdsJsonArray = new JSONArray();
            int index = 0;
            for (Letter letter : letterSoundCorrespondence.getLetters()) {
                letterIdsJsonArray.put(index, letter.getId());
                index++;
            }
            
            JSONArray letterTextsJsonArray = new JSONArray();
            index = 0;
            for (Letter letter : letterSoundCorrespondence.getLetters()) {
                letterTextsJsonArray.put(index, letter.getText());
                index++;
            }
            
            JSONArray soundIdsJsonArray = new JSONArray();
            index = 0;
            for (Sound sound : letterSoundCorrespondence.getSounds()) {
                soundIdsJsonArray.put(index, sound.getId());
                index++;
            }
            
            JSONArray soundValuesIpaJsonArray = new JSONArray();
            index = 0;
            for (Sound sound : letterSoundCorrespondence.getSounds()) {
                soundValuesIpaJsonArray.put(index, sound.getValueIpa());
                index++;
            }
            
            csvPrinter.printRecord(letterSoundCorrespondence.getId(),
                    letterIdsJsonArray,
                    letterTextsJsonArray,
                    soundIdsJsonArray,
                    soundValuesIpaJsonArray,
                    letterSoundCorrespondence.getUsageCount()
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
