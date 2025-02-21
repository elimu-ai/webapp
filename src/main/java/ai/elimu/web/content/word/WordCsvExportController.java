package ai.elimu.web.content.word;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Word;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/list")
@RequiredArgsConstructor
public class WordCsvExportController {

  private final Logger logger = LogManager.getLogger();

  private final WordDao wordDao;

  @RequestMapping(value = "/words.csv", method = RequestMethod.GET)
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    logger.info("handleRequest");

    List<Word> words = wordDao.readAllOrderedById();
    logger.info("words.size(): " + words.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT
        .withHeader(
            "id",
            "text",
            "letter_sound_correspondences",
            "usage_count",
            "word_type",
            "spelling_consistency",
            "root_word_id",
            "root_word_text"
        );
    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (Word word : words) {
      logger.info("word.getText(): \"" + word.getText() + "\"");

      JSONArray letterSoundsJsonArray = new JSONArray();
      int index = 0;
      for (LetterSound letterSound : word.getLetterSounds()) {
        JSONObject letterSoundJsonObject = new JSONObject();
        letterSoundJsonObject.put("id", letterSound.getId());
        letterSoundJsonObject.put("letters", letterSound.getLetters().stream().map(Letter::getText).toArray(String[]::new));
        letterSoundJsonObject.put("sounds", letterSound.getSounds().stream().map(Sound::getValueIpa).toArray(String[]::new));
        letterSoundJsonObject.put("usageCount", letterSound.getUsageCount());
        letterSoundsJsonArray.put(index, letterSoundJsonObject);
        index++;
      }

      Long rootWordId = null;
      String rootWordText = null;
      if (word.getRootWord() != null) {
        rootWordId = word.getRootWord().getId();
        rootWordText = word.getRootWord().getText();
      }

      csvPrinter.printRecord(
          word.getId(),
          word.getText(),
          letterSoundsJsonArray,
          word.getUsageCount(),
          word.getWordType(),
          word.getSpellingConsistency(),
          rootWordId,
          rootWordText
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
