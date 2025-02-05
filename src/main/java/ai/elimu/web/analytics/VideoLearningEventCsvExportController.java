package ai.elimu.web.analytics;

import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.enums.OrderDirection;
import ai.elimu.model.analytics.VideoLearningEvent;
import ai.elimu.util.AnalyticsHelper;

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
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/video-learning-event/list")
public class VideoLearningEventCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private VideoLearningEventDao videoLearningEventDao;
    
    @RequestMapping(value="/video-learning-events.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        List<VideoLearningEvent> videoLearningEvents = videoLearningEventDao.readAllOrderedByTimestamp(OrderDirection.ASC);
        logger.info("videoLearningEvents.size(): " + videoLearningEvents.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(
                    "id", // The Room database ID
                    "timestamp",
                    "android_id",
                    "package_name",
                    "video_id",
                    "video_title",
                    "learning_event_type",
                    "additional_data"
                )
                .build();
        
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        
        for (VideoLearningEvent videoLearningEvent : videoLearningEvents) {
            logger.info("videoLearningEvent.getId(): " + videoLearningEvent.getId());

            videoLearningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(videoLearningEvent.getAndroidId()));
            
            csvPrinter.printRecord(
                    videoLearningEvent.getId(),
                    videoLearningEvent.getTimestamp().getTimeInMillis(),
                    videoLearningEvent.getAndroidId(),
                    videoLearningEvent.getPackageName(),
                    videoLearningEvent.getVideoId(),
                    videoLearningEvent.getVideoTitle(),
                    videoLearningEvent.getLearningEventType(),
                    videoLearningEvent.getAdditionalData()
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
