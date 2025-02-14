package ai.elimu.web.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterContributionEvent;
import ai.elimu.model.contributor.NumberContributionEvent;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.contributor.WordContributionEvent;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/contributor/list")
@RequiredArgsConstructor
public class ContributorCsvExportController {

  private final Logger logger = LogManager.getLogger();

  private final ContributorDao contributorDao;

  private final LetterContributionEventDao letterContributionEventDao;

  private final NumberContributionEventDao numberContributionEventDao;

  private final WordContributionEventDao wordContributionEventDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  @RequestMapping(value = "/contributors.csv", method = RequestMethod.GET)
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    logger.info("handleRequest");

    List<Contributor> contributors = contributorDao.readAll();
    logger.info("contributors.size(): " + contributors.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT
        .withHeader(
            "contributor_id",
            "ethereum_address",
            "impact_percentage"
        );
    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    List<LetterContributionEvent> letterContributionEventsTotal = letterContributionEventDao.readAll();
    logger.info("letterContributionEventsTotal.size(): " + letterContributionEventsTotal.size());

    List<NumberContributionEvent> numberContributionEventsTotal = numberContributionEventDao.readAll();
    logger.info("numberContributionEventsTotal.size(): " + numberContributionEventsTotal.size());

    List<WordContributionEvent> wordContributionEventsTotal = wordContributionEventDao.readAll();
    logger.info("wordContributionEventsTotal.size(): " + wordContributionEventsTotal.size());

    List<StoryBookContributionEvent> storyBookContributionEventsTotal = storyBookContributionEventDao.readAll();
    logger.info("storyBookContributionEventsTotal.size(): " + storyBookContributionEventsTotal.size());

    for (Contributor contributor : contributors) {
      logger.info("contributor.getId(): " + contributor.getId());

      String ethereumAddress = "0x0000000000000000000000000000000000000000";
      if (contributor.getProviderIdWeb3() != null) {
        ethereumAddress = contributor.getProviderIdWeb3();
      }

      List<LetterContributionEvent> letterContributionEvents = letterContributionEventDao.readAll(contributor);
      Double impactPercentageLetters = letterContributionEvents.size() * 100D / letterContributionEventsTotal.size();
      logger.debug("impactPercentageLetters: " + impactPercentageLetters);

      List<NumberContributionEvent> numberContributionEvents = numberContributionEventDao.readAll(contributor);
      Double impactPercentageNumbers = numberContributionEvents.size() * 100D / numberContributionEventsTotal.size();
      logger.debug("impactPercentageNumbers: " + impactPercentageNumbers);

      List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readAll(contributor);
      Double impactPercentageWords = wordContributionEvents.size() * 100D / wordContributionEventsTotal.size();
      logger.debug("impactPercentageWords: " + impactPercentageWords);

      List<StoryBookContributionEvent> storyBookContributionEvents = storyBookContributionEventDao.readAll(contributor);
      Double impactPercentageStoryBooks = storyBookContributionEvents.size() * 100D / storyBookContributionEventsTotal.size();
      logger.debug("impactPercentageStoryBooks: " + impactPercentageStoryBooks);

      Double impactPercentage = (letterContributionEvents.size() + numberContributionEvents.size() + wordContributionEvents.size() + storyBookContributionEvents.size()) * 100D
          / (letterContributionEventsTotal.size() + numberContributionEventsTotal.size() + wordContributionEventsTotal.size() + storyBookContributionEventsTotal.size());
      logger.debug("impactPercentage: " + impactPercentage);

      csvPrinter.printRecord(
          contributor.getId(),
          ethereumAddress,
          impactPercentage
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
