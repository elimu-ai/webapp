package ai.elimu.web.content.letter_to_allophone_mapping;

import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterToAllophoneMapping;
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

@Controller
@RequestMapping("/content/letter-to-allophone-mapping/list")
public class LetterToAllophoneMappingCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;
    
    @RequestMapping(value="/letter-to-allophone-mappings.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        List<LetterToAllophoneMapping> letterToAllophoneMappings = letterToAllophoneMappingDao.readAllOrderedByUsage();
        logger.info("letterToAllophoneMappings.size(): " + letterToAllophoneMappings.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "letter_ids",
                        "letter_texts",
                        "allophone_ids",
                        "allophone_values_ipa",
                        "usage_count"
                );
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        
        for (LetterToAllophoneMapping letterToAllophoneMapping : letterToAllophoneMappings) {
            logger.info("letterToAllophoneMapping.getId(): \"" + letterToAllophoneMapping.getId() + "\"");
            
            JSONArray letterIdsJsonArray = new JSONArray();
            int index = 0;
            for (Letter letter : letterToAllophoneMapping.getLetters()) {
                letterIdsJsonArray.put(index, letter.getId());
                index++;
            }
            
            JSONArray letterTextsJsonArray = new JSONArray();
            index = 0;
            for (Letter letter : letterToAllophoneMapping.getLetters()) {
                letterTextsJsonArray.put(index, letter.getText());
                index++;
            }
            
            JSONArray allophoneIdsJsonArray = new JSONArray();
            index = 0;
            for (Allophone allophone : letterToAllophoneMapping.getAllophones()) {
                allophoneIdsJsonArray.put(index, allophone.getId());
                index++;
            }
            
            JSONArray allophoneValuesIpaJsonArray = new JSONArray();
            index = 0;
            for (Allophone allophone : letterToAllophoneMapping.getAllophones()) {
                allophoneValuesIpaJsonArray.put(index, allophone.getValueIpa());
                index++;
            }
            
            csvPrinter.printRecord(letterToAllophoneMapping.getId(),
                    letterIdsJsonArray,
                    letterTextsJsonArray,
                    allophoneIdsJsonArray,
                    allophoneValuesIpaJsonArray,
                    letterToAllophoneMapping.getUsageCount()
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
