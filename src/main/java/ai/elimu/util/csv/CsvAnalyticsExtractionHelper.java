package ai.elimu.util.csv;

import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.ReadingLevel;
import ai.elimu.model.v2.gson.content.StoryBookChapterGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.StoryBookParagraphGson;
import ai.elimu.web.content.storybook.StoryBookCsvExportController;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class CsvAnalyticsExtractionHelper {
    
    private static final Logger logger = LogManager.getLogger();
    
    /**
     * For information on how the CSV files were generated, see {@link StoryBookCsvExportController#handleRequest}.
     * <p />
     * Also see {@link #getStoryBookChaptersFromCsvBackup}
     */
    public static List<StoryBookGson> getStoryBooksFromCsvBackup(File csvFile) {
        logger.info("getStoryBooksFromCsvBackup");
        
        List<StoryBookGson> storyBookGsons = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id",
                            "title",
                            "description",
                            "content_license",
                            "attribution_url",
                            "reading_level",
                            "cover_image_id",
                            "chapters"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                // Convert from CSV to GSON
                
                StoryBookGson storyBookGson = new StoryBookGson();
                
                String title = csvRecord.get("title");
                storyBookGson.setTitle(title);
                
                String description = csvRecord.get("description");
                storyBookGson.setDescription(description);
                
                if (StringUtils.isNotBlank(csvRecord.get("content_license"))) {
                    ContentLicense contentLicense = ContentLicense.valueOf(csvRecord.get("content_license"));
//                    storyBookGson.setContentLicense(contentLicense);
                }
                
                String attributionUrl = csvRecord.get("attribution_url");
//                storyBookGson.setAttributionUrl(attributionUrl);
                
                if (StringUtils.isNotBlank(csvRecord.get("reading_level"))) {
                    ReadingLevel readingLevel = ReadingLevel.valueOf(csvRecord.get("reading_level"));
                    storyBookGson.setReadingLevel(readingLevel);
                }
                
                if (StringUtils.isNotBlank(csvRecord.get("cover_image_id"))) {
                    Long coverImageId = Long.valueOf(csvRecord.get("cover_image_id"));
//                    storyBookGson.setCoverImage();
                }
                
                List<StoryBookChapterGson> storyBookChapterGsons = new ArrayList<>();
                JSONArray chaptersJsonArray = new JSONArray(csvRecord.get("chapters"));
                logger.info("chaptersJsonArray: " + chaptersJsonArray);
                for (int i = 0; i < chaptersJsonArray.length(); i++) {
                    JSONObject chapterJsonObject = chaptersJsonArray.getJSONObject(i);
                    logger.info("chapterJsonObject: " + chapterJsonObject);
                    
                    StoryBookChapterGson storyBookChapterGson = new StoryBookChapterGson();
                    storyBookChapterGson.setSortOrder(chapterJsonObject.getInt("sortOrder"));
                    
                    List<StoryBookParagraphGson> storyBookParagraphGsons = new ArrayList<>();
                    JSONArray paragraphsJsonArray = chapterJsonObject.getJSONArray("storyBookParagraphs");
                    logger.info("paragraphsJsonArray: " + paragraphsJsonArray);
                    for (int j = 0; j < paragraphsJsonArray.length(); j++) {
                        JSONObject paragraphJsonObject = paragraphsJsonArray.getJSONObject(j);
                        logger.info("paragraphJsonObject: " + paragraphJsonObject);

                        StoryBookParagraphGson storyBookParagraphGson = new StoryBookParagraphGson();
                        storyBookParagraphGson.setSortOrder(paragraphJsonObject.getInt("sortOrder"));
                        storyBookParagraphGson.setOriginalText(paragraphJsonObject.getString("originalText"));
                        // TODO: setWords
                        
                        storyBookParagraphGsons.add(storyBookParagraphGson);
                    }
                    storyBookChapterGson.setStoryBookParagraphs(storyBookParagraphGsons);
                    
                    storyBookChapterGsons.add(storyBookChapterGson);
                }
                storyBookGson.setStoryBookChapters(storyBookChapterGsons);
                
                storyBookGsons.add(storyBookGson);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return storyBookGsons;
    }
}
