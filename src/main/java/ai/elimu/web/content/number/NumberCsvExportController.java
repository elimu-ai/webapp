package ai.elimu.web.content.number;

import ai.elimu.dao.NumberDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
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
@RequestMapping("/content/number/list")
public class NumberCsvExportController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @RequestMapping(value="/numbers.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        // Generate CSV file
        String csvFileContent = "id,value,symbol,word_texts,word_ids" + "\n";
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<Number> numbers = numberDao.readAllOrdered(language);
        logger.info("numbers.size(): " + numbers.size());
        for (Number number : numbers) {
            logger.info("word.getValue(): " + number.getValue());
            
            String[] wordTextsArray = new String[number.getWords().size()];
            int index = 0;
            for (Word word : number.getWords()) {
                logger.info("number.getValue(): " + number.getValue());
                wordTextsArray[index] = word.getText();
                index++;
            }
            
            long[] wordIdsArray = new long[number.getWords().size()];
            index = 0;
            for (Word word : number.getWords()) {
                wordIdsArray[index] = word.getId();
                index++;
            }
            
            csvFileContent += number.getId() + ","
                    + number.getValue() + ","
                    + "\"" + number.getSymbol() + "\","
                    + Arrays.toString(wordTextsArray) + ","
                    + Arrays.toString(wordIdsArray) + "\n";
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
