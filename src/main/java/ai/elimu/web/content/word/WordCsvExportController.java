package ai.elimu.web.content.word;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterToAllophoneMapping;
import ai.elimu.model.content.Word;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

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
                        "allophone_ids",
                        "allophone_values_ipa",
                        "letter_to_allophone_mappings",
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
            
            JSONArray allophoneIdsJsonArray = new JSONArray();
            int index = 0;
            for (Allophone allophone : word.getAllophones()) {
                allophoneIdsJsonArray.put(index, allophone.getId());
                index++;
            }
            
            JSONArray allophoneValuesIpaJsonArray = new JSONArray();
            index = 0;
            for (Allophone allophone : word.getAllophones()) {
                allophoneValuesIpaJsonArray.put(index, allophone.getValueIpa());
                index++;
            }
            
            JSONArray letterToAllophoneMappingsJsonArray = new JSONArray();
            index = 0;
            for (LetterToAllophoneMapping letterToAllophoneMapping : word.getLetterToAllophoneMappings()) {
                JSONObject letterToAllophoneMappingJsonObject = new JSONObject();
                letterToAllophoneMappingJsonObject.put("id", letterToAllophoneMapping.getId());
                letterToAllophoneMappingJsonObject.put("letters", letterToAllophoneMapping.getLetters().stream().map(Letter::getText).collect(Collectors.joining()));
                letterToAllophoneMappingJsonObject.put("allophones", letterToAllophoneMapping.getAllophones().stream().map(Allophone::getValueIpa).collect(Collectors.joining()));
                letterToAllophoneMappingJsonObject.put("usageCount", letterToAllophoneMapping.getUsageCount());
                letterToAllophoneMappingsJsonArray.put(index, letterToAllophoneMappingJsonObject);
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
                    allophoneIdsJsonArray,
                    allophoneValuesIpaJsonArray,
                    letterToAllophoneMappingsJsonArray,
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
