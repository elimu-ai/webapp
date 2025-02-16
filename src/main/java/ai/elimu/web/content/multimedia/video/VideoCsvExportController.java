package ai.elimu.web.content.multimedia.video;

import ai.elimu.dao.VideoDao;
import ai.elimu.model.content.multimedia.Video;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;

@Controller
@RequestMapping("/content/video/list")
public class VideoCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private VideoDao videoDao;
    
    @GetMapping(value="/videos.csv")
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        // Generate CSV file
        String csvFileContent = "id,content_type,content_license,attribution_url,title,download_url,video_format" + "\n";
        List<Video> videos = videoDao.readAllOrderedById();
        logger.info("videos.size(): " + videos.size());
        for (Video video : videos) {
            String downloadUrl = "/video/" + video.getId() + "." + video.getVideoFormat().toString().toLowerCase();
            csvFileContent += video.getId() + ","
                    + video.getContentType() + ","
                    + video.getContentLicense()+ ","
                    + "\"" + video.getAttributionUrl() + "\","
                    + "\"" + video.getTitle() + "\","
                    + "\"" + downloadUrl + "\","
                    + video.getVideoFormat() + "\n";
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
