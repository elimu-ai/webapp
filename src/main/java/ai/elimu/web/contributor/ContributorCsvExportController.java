package ai.elimu.web.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.ImageContributionEventDao;
import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.SoundContributionEventDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.dao.VideoContributionEventDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.ImageContributionEvent;
import ai.elimu.entity.contributor.LetterContributionEvent;
import ai.elimu.entity.contributor.LetterSoundContributionEvent;
import ai.elimu.entity.contributor.LetterSoundPeerReviewEvent;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.entity.contributor.SoundContributionEvent;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.contributor.StoryBookPeerReviewEvent;
import ai.elimu.entity.contributor.VideoContributionEvent;
import ai.elimu.entity.contributor.WordContributionEvent;
import ai.elimu.entity.contributor.WordPeerReviewEvent;
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

  private final SoundContributionEventDao soundContributionEventDao;

  private final LetterSoundContributionEventDao letterSoundContributionEventDao;
  private final LetterSoundPeerReviewEventDao letterSoundPeerReviewEventDao;

  private final NumberContributionEventDao numberContributionEventDao;

  private final WordContributionEventDao wordContributionEventDao;
  private final WordPeerReviewEventDao wordPeerReviewEventDao;

  private final ImageContributionEventDao imageContributionEventDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;
  private final StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;

  private final VideoContributionEventDao videoContributionEventDao;

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

    List<SoundContributionEvent> soundContributionEventsTotal = soundContributionEventDao.readAll();
    log.info("soundContributionEventsTotal.size(): " + soundContributionEventsTotal.size());

    List<LetterSoundContributionEvent> letterSoundContributionEventsTotal = letterSoundContributionEventDao.readAll();
    log.info("letterSoundContributionEventsTotal.size(): " + letterSoundContributionEventsTotal.size());
    List<LetterSoundPeerReviewEvent> letterSoundPeerReviewEventsTotal = letterSoundPeerReviewEventDao.readAll();
    log.info("letterSoundPeerReviewEventsTotal.size(): " + letterSoundPeerReviewEventsTotal.size());

    List<NumberContributionEvent> numberContributionEventsTotal = numberContributionEventDao.readAll();
    log.info("numberContributionEventsTotal.size(): " + numberContributionEventsTotal.size());

    List<WordContributionEvent> wordContributionEventsTotal = wordContributionEventDao.readAll();
    log.info("wordContributionEventsTotal.size(): " + wordContributionEventsTotal.size());
    List<WordPeerReviewEvent> wordPeerReviewEventsTotal = wordPeerReviewEventDao.readAll();
    log.info("wordPeerReviewEventsTotal.size(): " + wordPeerReviewEventsTotal.size());

    List<ImageContributionEvent> imageContributionEventsTotal = imageContributionEventDao.readAll();
    log.info("imageContributionEventsTotal.size(): " + imageContributionEventsTotal.size());

    List<StoryBookContributionEvent> storyBookContributionEventsTotal = storyBookContributionEventDao.readAll();
    log.info("storyBookContributionEventsTotal.size(): " + storyBookContributionEventsTotal.size());
    List<StoryBookPeerReviewEvent> storyBookPeerReviewEventsTotal = storyBookPeerReviewEventDao.readAll();
    log.info("storyBookPeerReviewEventsTotal.size(): " + storyBookPeerReviewEventsTotal.size());

    List<VideoContributionEvent> videoContributionEventsTotal = videoContributionEventDao.readAll();
    log.info("videoContributionEventsTotal.size(): " + videoContributionEventsTotal.size());

    for (Contributor contributor : contributors) {
      log.info("contributor.getId(): " + contributor.getId());

      String ethereumAddress = "0x0000000000000000000000000000000000000000";
      if (contributor.getProviderIdWeb3() != null) {
        ethereumAddress = contributor.getProviderIdWeb3();
      }

      List<LetterContributionEvent> letterContributionEvents = letterContributionEventDao.readAll(contributor);
      Double impactPercentageLetters = letterContributionEvents.size() * 100D / letterContributionEventsTotal.size();
      log.debug("impactPercentageLetters: " + impactPercentageLetters);

      List<SoundContributionEvent> soundContributionEvents = soundContributionEventDao.readAll(contributor);
      Double impactPercentageSounds = soundContributionEvents.size() * 100D / soundContributionEventsTotal.size();
      log.debug("impactPercentageSounds: " + impactPercentageSounds);

      List<LetterSoundContributionEvent> letterSoundContributionEvents = letterSoundContributionEventDao.readAll(contributor);
      Double impactPercentageLetterSounds = letterSoundContributionEvents.size() * 100D / letterSoundContributionEventsTotal.size();
      log.debug("impactPercentageLetterSounds: " + impactPercentageLetterSounds);
      List<LetterSoundPeerReviewEvent> letterSoundPeerReviewEvents = letterSoundPeerReviewEventDao.readAll(contributor);
      Double impactPercentageLetterSoundPeerReviews = letterSoundPeerReviewEvents.size() * 100D / letterSoundPeerReviewEventsTotal.size();
      log.debug("impactPercentageLetterSoundPeerReviews: " + impactPercentageLetterSoundPeerReviews);

      List<NumberContributionEvent> numberContributionEvents = numberContributionEventDao.readAll(contributor);
      Double impactPercentageNumbers = numberContributionEvents.size() * 100D / numberContributionEventsTotal.size();
      log.debug("impactPercentageNumbers: " + impactPercentageNumbers);

      List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readAll(contributor);
      Double impactPercentageWords = wordContributionEvents.size() * 100D / wordContributionEventsTotal.size();
      log.debug("impactPercentageWords: " + impactPercentageWords);
      List<WordPeerReviewEvent> wordPeerReviewEvents = wordPeerReviewEventDao.readAll(contributor);
      Double impactPercentageWordPeerReviews = wordPeerReviewEvents.size() * 100D / wordPeerReviewEventsTotal.size();
      log.debug("impactPercentageWordPeerReviews: " + impactPercentageWordPeerReviews);

      List<ImageContributionEvent> imageContributionEvents = imageContributionEventDao.readAll(contributor);
      Double impactPercentageImages = imageContributionEvents.size() * 100D / imageContributionEventsTotal.size();
      log.debug("impactPercentageImages: " + impactPercentageImages);

      List<StoryBookContributionEvent> storyBookContributionEvents = storyBookContributionEventDao.readAll(contributor);
      Double impactPercentageStoryBooks = storyBookContributionEvents.size() * 100D / storyBookContributionEventsTotal.size();
      log.debug("impactPercentageStoryBooks: " + impactPercentageStoryBooks);
      List<StoryBookPeerReviewEvent> storyBookPeerReviewEvents = storyBookPeerReviewEventDao.readAll(contributor);
      Double impactPercentageStoryBookPeerReviews = storyBookPeerReviewEvents.size() * 100D / storyBookPeerReviewEventsTotal.size();
      log.debug("impactPercentageStoryBookPeerReviews: " + impactPercentageStoryBookPeerReviews);

      List<VideoContributionEvent> videoContributionEvents = videoContributionEventDao.readAll(contributor);
      Double impactPercentageVideos = videoContributionEvents.size() * 100D / videoContributionEventsTotal.size();
      log.debug("impactPercentageVideos: " + impactPercentageImages);

      Double impactPercentage = (
              letterContributionEvents.size()
            + soundContributionEvents.size()
            + letterSoundContributionEvents.size()
            + letterSoundPeerReviewEvents.size()
            + numberContributionEvents.size()
            + wordContributionEvents.size()
            + wordPeerReviewEvents.size()
            + imageContributionEvents.size()
            + storyBookContributionEvents.size()
            + storyBookPeerReviewEvents.size()
            + videoContributionEvents.size()
          ) * 100D / (
              letterContributionEventsTotal.size()
            + soundContributionEventsTotal.size()
            + letterSoundContributionEventsTotal.size()
            + letterSoundPeerReviewEventsTotal.size()
            + numberContributionEventsTotal.size()
            + wordContributionEventsTotal.size()
            + wordPeerReviewEventsTotal.size()
            + imageContributionEventsTotal.size()
            + storyBookContributionEventsTotal.size()
            + storyBookPeerReviewEventsTotal.size()
            + videoContributionEventsTotal.size()
          );
      log.debug("impactPercentage: " + impactPercentage);

      csvPrinter.printRecord(
          contributor.getId(),
          ethereumAddress,
          impactPercentage
      );
    }
    csvPrinter.flush();
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
