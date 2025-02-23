package ai.elimu.rest.v2.analytics;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.WordLearningEvent;
import ai.elimu.model.content.Word;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.model.v2.enums.analytics.LearningEventType;
import ai.elimu.util.AnalyticsHelper;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/rest/v2/analytics/word-learning-events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Slf4j
public class WordLearningEventsRestController {

  private final WordLearningEventDao wordLearningEventDao;

  private final ApplicationDao applicationDao;

  private final WordDao wordDao;

  @RequestMapping(value = "/csv", method = RequestMethod.POST)
  public String handleUploadCsvRequest(
      @RequestParam("file") MultipartFile multipartFile,
      HttpServletResponse response
  ) {
    log.info("handleUploadCsvRequest");

    String name = multipartFile.getName();
    log.info("name: " + name);

    // Expected format: "7161a85a0e4751cd_3001012_word-learning-events_2020-04-23.csv"
    String originalFilename = multipartFile.getOriginalFilename();
    log.info("originalFilename: " + originalFilename);

    // TODO: Send notification to the #📊-data-collection channel in Discord
    // Hide parts of the Android ID, e.g. "7161***51cd_3001012_word-learning-events_2020-04-23.csv"
    String anonymizedOriginalFilename = originalFilename.substring(0, 4) + "***" + originalFilename.substring(12);
    DiscordHelper.sendChannelMessage("Received dataset: `" + anonymizedOriginalFilename + "`", null, null, null, null);

    String androidIdExtractedFromFilename = AnalyticsHelper.extractAndroidIdFromCsvFilename(originalFilename);
    log.info("androidIdExtractedFromFilename: \"" + androidIdExtractedFromFilename + "\"");

    Integer versionCodeExtractedFromFilename = AnalyticsHelper.extractVersionCodeFromCsvFilename(originalFilename);
    log.info("versionCodeExtractedFromFilename: " + versionCodeExtractedFromFilename);

    String contentType = multipartFile.getContentType();
    log.info("contentType: " + contentType);

    JSONObject jsonObject = new JSONObject();

    try {
      byte[] bytes = multipartFile.getBytes();
      log.info("bytes.length: " + bytes.length);

      // Store a backup of the original CSV file on the filesystem (in case it will be needed for debugging)
      File elimuAiDir = new File(System.getProperty("user.home"), ".elimu-ai");
      File languageDir = new File(elimuAiDir, "lang-" + Language.valueOf(ConfigHelper.getProperty("content.language")));
      File analyticsDir = new File(languageDir, "analytics");
      File androidIdDir = new File(analyticsDir, "android-id-" + androidIdExtractedFromFilename);
      File versionCodeDir = new File(androidIdDir, "version-code-" + versionCodeExtractedFromFilename);
      File wordLearningEventsDir = new File(versionCodeDir, "word-learning-events");
      wordLearningEventsDir.mkdirs();
      File csvFile = new File(wordLearningEventsDir, originalFilename);
      log.info("Storing CSV file at " + csvFile);
      multipartFile.transferTo(csvFile);

      // Iterate each row in the CSV file
      Path csvFilePath = Paths.get(csvFile.toURI());
      log.info("csvFilePath: " + csvFilePath);
      Reader reader = Files.newBufferedReader(csvFilePath);
      CSVFormat csvFormat = CSVFormat.DEFAULT
          .withHeader(
              "id", // The Room database ID
              "time",
              "android_id",
              "package_name",
              "word_id",
              "word_text",
              "learning_event_type"
          )
          .withSkipHeaderRecord();
      CSVParser csvParser = new CSVParser(reader, csvFormat);
      for (CSVRecord csvRecord : csvParser) {
        log.info("csvRecord: " + csvRecord);

        // Convert from CSV to Java

        WordLearningEvent wordLearningEvent = new WordLearningEvent();

        long timeInMillis = Long.valueOf(csvRecord.get("time"));
        Calendar timestamp = Calendar.getInstance();
        timestamp.setTimeInMillis(timeInMillis);
        wordLearningEvent.setTimestamp(timestamp);

        String androidId = csvRecord.get("android_id");
        wordLearningEvent.setAndroidId(androidId);

        String packageName = csvRecord.get("package_name");
        wordLearningEvent.setPackageName(packageName);

        Application application = applicationDao.readByPackageName(packageName);
        log.info("application: " + application);
        if (application == null) {
          // Return error message saying that the reporting Application has not yet been added
          log.warn("An Application with package name " + packageName + " was not found");

          jsonObject.put("result", "error");
          jsonObject.put("errorMessage", "An Application with package name " + packageName + " was not found");
          response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());

          break;
        }
        wordLearningEvent.setApplication(application);

        Long wordId = Long.valueOf(csvRecord.get("word_id"));
        Word word = wordDao.read(wordId);
        log.info("word: " + word);
        wordLearningEvent.setWord(word);
        if (word == null) {
          // Return error message saying that the Word ID was not found
          log.warn("A Word with ID " + wordId + " was not found");

          jsonObject.put("result", "error");
          jsonObject.put("errorMessage", "A Word with ID " + wordId + " was not found");
          response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());

          break;
        }

        String wordText = csvRecord.get("word_text");
        wordLearningEvent.setWordText(wordText);

        LearningEventType learningEventType = LearningEventType.valueOf(csvRecord.get("learning_event_type"));
        wordLearningEvent.setLearningEventType(learningEventType);

        // Check if the event has already been stored in the database
        WordLearningEvent existingWordLearningEvent = wordLearningEventDao.read(timestamp, androidId, application, word);
        log.info("existingWordLearningEvent: " + existingWordLearningEvent);
        if (existingWordLearningEvent == null) {
          // Store the event in the database
          wordLearningEventDao.create(wordLearningEvent);
          log.info("Stored WordLearningEvent in database with ID " + wordLearningEvent.getId());

          jsonObject.put("result", "success");
          jsonObject.put("successMessage", "The WordLearningEvent was stored in the database");
        } else {
          // Return error message saying that the event has already been uploaded
          log.warn("The event has already been stored in the database");

          jsonObject.put("result", "error");
          jsonObject.put("errorMessage", "The event has already been stored in the database");
          response.setStatus(HttpStatus.CONFLICT.value());
        }
      }
    } catch (Exception ex) {
      log.error(ex.getMessage());

      jsonObject.put("result", "error");
      jsonObject.put("errorMessage", ex.getMessage());
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    String jsonResponse = jsonObject.toString();
    log.info("jsonResponse: " + jsonResponse);
    return jsonResponse;
  }
}
