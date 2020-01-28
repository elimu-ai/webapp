package ai.elimu.web.content.allophone;

import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.content.Allophone;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
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
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        // Generate CSV file
        String csvFileContent = "id,value_ipa,value_sampa,audio_id,diacritic,sound_type,usage_count" + "\n";
        List<Allophone> allophones = allophoneDao.readAll();
        logger.info("allophones.size(): " + allophones.size());
        for (Allophone allophone : allophones) {
            csvFileContent += allophone.getId() + ","
                    + "\"" + allophone.getValueIpa().replace("\"", "\"\"") + "\","
                    + "\"" + allophone.getValueSampa().replace("\"", "\"\"") + "\","
                    + ((allophone.getAudio() != null) ? allophone.getAudio().getId() : "null") + ","
                    + allophone.isDiacritic() + ","
                    + allophone.getSoundType() + ","
                    + allophone.getUsageCount() + "\n";
        }
        
        response.setContentType("text/csv");
        byte[] bytes = csvFileContent.getBytes();
        response.setContentLength(bytes.length);
        try {
            outputStream.write(bytes);
        } catch (EOFException ex) {
            // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
            logger.warn(ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        } finally {
            try {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (EOFException ex) {
                    // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
                    logger.warn(ex);
                }
            } catch (IOException ex) {
                logger.error(null, ex);
            }
        }
    }
}
