package ai.elimu.web.content.word;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/list")
public class WordCsvExportController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @RequestMapping(value="/words.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<Word> words = wordDao.readAllOrderedByUsage(language);
        logger.info("words.size(): " + words.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id", 
                        "text", 
                        "allophone_ids", 
                        "allophone_values_ipa", 
                        "usage_count",
                        "word_type",
                        "spelling_consistency"
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
            
            csvPrinter.printRecord(
                    word.getId(),
                    word.getText(),
                    allophoneIdsJsonArray,
                    allophoneValuesIpaJsonArray,
                    word.getUsageCount(),
                    word.getWordType(),
                    word.getSpellingConsistency()
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
            logger.error(null, ex);
        }
    }
}
