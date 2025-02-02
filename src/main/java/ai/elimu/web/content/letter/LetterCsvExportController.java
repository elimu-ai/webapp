package ai.elimu.web.content.letter;

import ai.elimu.dao.LetterDao;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Letter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter/list")
public class LetterCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterDao letterDao;
    
    @RequestMapping(value="/letters.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        List<Letter> letters = letterDao.readAllOrderedByUsage();
        logger.info("letters.size(): " + letters.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id", 
                        "text", 
                        "diacritic", 
                        "usage_count"
                );
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        
        for (Letter letter : letters) {
            logger.info("letter.getText(): \"" + letter.getText() + "\"");
            
            csvPrinter.printRecord(
                    letter.getId(),
                    letter.getText(),
                    letter.isDiacritic(),
                    letter.getUsageCount()
            );
            
            csvPrinter.flush();
        }
        csvPrinter.close();
        
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
