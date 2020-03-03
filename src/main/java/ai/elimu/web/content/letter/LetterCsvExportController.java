package ai.elimu.web.content.letter;

import ai.elimu.dao.LetterDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
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
@RequestMapping("/content/letter/list")
public class LetterCsvExportController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterDao letterDao;
    
    @RequestMapping(value="/letters.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<Letter> letters = letterDao.readAllOrderedByUsage(language);
        logger.info("letters.size(): " + letters.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id", 
                        "text", 
                        "allophone_ids", 
                        "allophone_values_ipa", 
                        "diacritic", 
                        "usage_count"
                );
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        
        for (Letter letter : letters) {
            logger.info("letter.getText(): \"" + letter.getText() + "\"");
            
            JSONArray allophoneIdsJsonArray = new JSONArray();
            int index = 0;
            for (Allophone allophone : letter.getAllophones()) {
                allophoneIdsJsonArray.put(index, allophone.getId());
                index++;
            }
            
            JSONArray allophoneValuesIpaJsonArray = new JSONArray();
            index = 0;
            for (Allophone allophone : letter.getAllophones()) {
                allophoneValuesIpaJsonArray.put(index, allophone.getValueIpa());
                index++;
            }
            
            csvPrinter.printRecord(
                    letter.getId(),
                    letter.getText(),
                    allophoneIdsJsonArray,
                    allophoneValuesIpaJsonArray,
                    letter.isDiacritic(),
                    letter.getUsageCount()
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
