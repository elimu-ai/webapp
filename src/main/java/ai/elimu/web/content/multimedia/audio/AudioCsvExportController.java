package ai.elimu.web.content.multimedia.audio;

import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.multimedia.Audio;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/audio/list")
public class AudioCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private AudioDao audioDao;
    
    @RequestMapping(value="/audios.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        // Generate CSV file
        String csvFileContent = "id,content_type,content_license,attribution_url,word_id,title,transcription,download_url,audio_format" + "\n";
        List<Audio> audios = audioDao.readAll();
        logger.info("audios.size(): " + audios.size());
        for (Audio audio : audios) {
            String downloadUrl = "/audio/" + audio.getId() + "." + audio.getAudioFormat().toString().toLowerCase();
            csvFileContent += audio.getId() + ","
                    + audio.getContentType() + ","
                    + audio.getContentLicense() + ","
                    + "\"" + audio.getAttributionUrl() + "\","
                    + ((audio.getWord() != null) ? audio.getWord().getId() : "") + ","
                    + "\"" + audio.getTitle() + "\","
                    + "\"" + audio.getTranscription() + "\","
                    + "\"" + downloadUrl + "\","
                    + audio.getAudioFormat() + "\n";
        }
        
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
