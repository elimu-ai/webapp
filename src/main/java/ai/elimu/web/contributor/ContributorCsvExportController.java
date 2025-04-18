package ai.elimu.web.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterContributionEvent;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.contributor.WordContributionEvent;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contributor/list/contributors.csv")
@RequiredArgsConstructor
@Slf4j
public class ContributorCsvExportController {

  private final ContributorDao contributorDao;

  private final LetterContributionEventDao letterContributionEventDao;

  private final NumberContributionEventDao numberContributionEventDao;

  private final WordContributionEventDao wordContributionEventDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    List<Contributor> contributors = contributorDao.readAll();
    log.info("contributors.size(): " + contributors.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT
        .withHeader(
            "contributor_id",
            "ethereum_address",
            "impact_percentage"
        );
    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    List<LetterContributionEvent> letterContributionEventsTotal = letterContributionEventDao.readAll();
    log.info("letterContributionEventsTotal.size(): " + letterContributionEventsTotal.size());

    List<NumberContributionEvent> numberContributionEventsTotal = numberContributionEventDao.readAll();
    log.info("numberContributionEventsTotal.size(): " + numberContributionEventsTotal.size());

    List<WordContributionEvent> wordContributionEventsTotal = wordContributionEventDao.readAll();
    log.info("wordContributionEventsTotal.size(): " + wordContributionEventsTotal.size());

    List<StoryBookContributionEvent> storyBookContributionEventsTotal = storyBookContributionEventDao.readAll();
    log.info("storyBookContributionEventsTotal.size(): " + storyBookContributionEventsTotal.size());

    for (Contributor contributor : contributors) {
      log.info("contributor.getId(): " + contributor.getId());

      String ethereumAddress = "0x0000000000000000000000000000000000000000";
      if (contributor.getProviderIdWeb3() != null) {
        ethereumAddress = contributor.getProviderIdWeb3();
      }

      List<LetterContributionEvent> letterContributionEvents = letterContributionEventDao.readAll(contributor);
      Double impactPercentageLetters = letterContributionEvents.size() * 100D / letterContributionEventsTotal.size();
      log.debug("impactPercentageLetters: " + impactPercentageLetters);

      List<NumberContributionEvent> numberContributionEvents = numberContributionEventDao.readAll(contributor);
      Double impactPercentageNumbers = numberContributionEvents.size() * 100D / numberContributionEventsTotal.size();
      log.debug("impactPercentageNumbers: " + impactPercentageNumbers);

      List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readAll(contributor);
      Double impactPercentageWords = wordContributionEvents.size() * 100D / wordContributionEventsTotal.size();
      log.debug("impactPercentageWords: " + impactPercentageWords);

      List<StoryBookContributionEvent> storyBookContributionEvents = storyBookContributionEventDao.readAll(contributor);
      Double impactPercentageStoryBooks = storyBookContributionEvents.size() * 100D / storyBookContributionEventsTotal.size();
      log.debug("impactPercentageStoryBooks: " + impactPercentageStoryBooks);

      Double impactPercentage = (letterContributionEvents.size() + numberContributionEvents.size() + wordContributionEvents.size() + storyBookContributionEvents.size()) * 100D
          / (letterContributionEventsTotal.size() + numberContributionEventsTotal.size() + wordContributionEventsTotal.size() + storyBookContributionEventsTotal.size());
      log.debug("impactPercentage: " + impactPercentage);

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
      log.error(ex.getMessage());
    }
  }
}
