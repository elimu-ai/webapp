package ai.elimu.web.content.number;

import ai.elimu.dao.NumberDao;
import ai.elimu.model.content.Number;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/number/list")
@RequiredArgsConstructor
public class NumberCsvExportController {

  private final Logger logger = LogManager.getLogger();

  private final NumberDao numberDao;

  @RequestMapping(value = "/numbers.csv", method = RequestMethod.GET)
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    logger.info("handleRequest");

    List<Number> numbers = numberDao.readAllOrderedById();
    logger.info("numbers.size(): " + numbers.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT
        .withHeader(
            "id",
            "value",
            "symbol",
            "word_ids",
            "word_texts"
        );
    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (Number number : numbers) {
      logger.info("number.getValue(): \"" + number.getValue() + "\"");

      JSONArray wordIdsJsonArray = new JSONArray();
      int index = 0;
      for (Word word : number.getWords()) {
        wordIdsJsonArray.put(index, word.getId());
        index++;
      }

      JSONArray wordTextsJsonArray = new JSONArray();
      index = 0;
      for (Word word : number.getWords()) {
        wordTextsJsonArray.put(index, word.getText());
        index++;
      }

      csvPrinter.printRecord(
          number.getId(),
          number.getValue(),
          number.getSymbol(),
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
