package ai.elimu.web.analytics;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.analytics.StoryBookLearningEvent;
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
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/storybook-learning-event/list")
public class StoryBookLearningEventCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookLearningEventDao storyBookLearningEventDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @RequestMapping(value="/storybook-learning-events.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.readAll();
        logger.info("storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id", // The Room database ID
                        "time",
                        "android_id",
                        "package_name",
                        "storybook_id",
                        "storybook_title",
                        "learning_event_type"
                );
        
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        
        for (StoryBookLearningEvent storyBookLearningEvent : storyBookLearningEvents) {
            logger.info("storyBookLearningEvent.getId(): " + storyBookLearningEvent.getId());
            
            csvPrinter.printRecord(
                    storyBookLearningEvent.getId(),
                    storyBookLearningEvent.getTime().getTimeInMillis(),
                    storyBookLearningEvent.getAndroidId(),
                    storyBookLearningEvent.getApplication().getPackageName(),
                    storyBookLearningEvent.getStoryBook().getId(),
                    storyBookLearningEvent.getStoryBookTitle(),
                    storyBookLearningEvent.getLearningEventType()
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
