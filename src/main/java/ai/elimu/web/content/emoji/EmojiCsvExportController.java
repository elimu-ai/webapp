package ai.elimu.web.content.emoji;

import ai.elimu.dao.EmojiDao;
import ai.elimu.model.content.Emoji;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/emoji/list/emojis.csv")
@RequiredArgsConstructor
public class EmojiCsvExportController {

  private final Logger logger = LogManager.getLogger();

  private final EmojiDao emojiDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    logger.info("handleRequest");

    List<Emoji> emojis = emojiDao.readAllOrderedById();
    logger.info("emojis.size(): " + emojis.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT
        .withHeader(
            "id",
            "glyph",
            "unicode_version",
            "unicode_emoji_version",
            "word_ids",
            "word_texts"
        );
    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (Emoji emoji : emojis) {
      logger.info("emoji.getGlyph(): \"" + emoji.getGlyph() + "\"");

      JSONArray wordIdsJsonArray = new JSONArray();
      int index = 0;
      for (Word word : emoji.getWords()) {
        wordIdsJsonArray.put(index, word.getId());
        index++;
      }

      JSONArray wordTextsJsonArray = new JSONArray();
      index = 0;
      for (Word word : emoji.getWords()) {
        wordTextsJsonArray.put(index, word.getText());
        index++;
      }

      csvPrinter.printRecord(
          emoji.getId(),
          emoji.getGlyph(),
          emoji.getUnicodeVersion(),
          emoji.getUnicodeEmojiVersion(),
          wordIdsJsonArray,
          wordTextsJsonArray
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
