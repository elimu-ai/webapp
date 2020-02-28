package ai.elimu.web.content.allophone;

import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.content.Allophone;
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
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/allophone/list")
public class AllophoneCsvExportController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @RequestMapping(value="/allophones.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(language);
        logger.info("allophones.size(): " + allophones.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id", 
                        "value_ipa", 
                        "value_sampa", 
                        "audio_id", 
                        "diacritic", 
                        "sound_type", 
                        "usage_count"
                );
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        
        for (Allophone allophone : allophones) {
            Long audioId = null;
            if (allophone.getAudio() != null) {
                audioId = allophone.getAudio().getId();
            }
            
            csvPrinter.printRecord(
                    allophone.getId(),
                    allophone.getValueIpa(),
                    allophone.getValueSampa(),
                    audioId,
                    allophone.isDiacritic(),
                    allophone.getSoundType(),
                    allophone.getUsageCount()
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
