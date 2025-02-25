package ai.elimu.web.content.multimedia.audio;

import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.content.AudioFormat;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.audio.AudioMetadataExtractionHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/multimedia/audio/create")
@RequiredArgsConstructor
@Slf4j
public class AudioCreateController {

  private final AudioDao audioDao;

  private final WordDao wordDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final EmojiDao emojiDao;

  private final AudioContributionEventDao audioContributionEventDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(
      Model model,
      @RequestParam(required = false) Long wordId,
      @RequestParam(required = false) Long storyBookParagraphId,
      @RequestParam(required = false) String autoFillTitle,
      @RequestParam(required = false) String autoFillTranscription
  ) {
    log.info("handleRequest");

    Audio audio = new Audio();

    // Pre-select the Audio's corresponding Word
    if (wordId != null) {
      Word word = wordDao.read(wordId);
      audio.setWord(word);
    }

    // Pre-select the Audio's corresponding StoryBookParagraph
    if (storyBookParagraphId != null) {
      StoryBookParagraph storyBookParagraph = storyBookParagraphDao.read(storyBookParagraphId);
      audio.setStoryBookParagraph(storyBookParagraph);
    }

    // Pre-fill the Audio's title
    if (StringUtils.isNotBlank(autoFillTitle)) {
      audio.setTitle(autoFillTitle);
    }

    // Pre-fill the Audio's transcription
    if (StringUtils.isNotBlank(autoFillTranscription)) {
      audio.setTranscription(autoFillTranscription);
    }

    model.addAttribute("audio", audio);

    model.addAttribute("words", wordDao.readAllOrdered());
    model.addAttribute("storyBookParagraphs", storyBookParagraphDao.readAll());

    model.addAttribute("contentLicenses", ContentLicense.values());

    model.addAttribute("literacySkills", LiteracySkill.values());
    model.addAttribute("numeracySkills", NumeracySkill.values());

    model.addAttribute("timeStart", System.currentTimeMillis());

    model.addAttribute("emojisByWordId", getEmojisByWordId());

    return "content/multimedia/audio/create";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      /*@Valid*/ Audio audio,
      @RequestParam("bytes") MultipartFile multipartFile,
      BindingResult result,
      Model model) {
    log.info("handleSubmit");

    try {
      byte[] bytes = multipartFile.getBytes();
      if (multipartFile.isEmpty() || (bytes == null) || (bytes.length == 0)) {
        result.rejectValue("bytes", "NotNull");
      } else {
        String originalFileName = multipartFile.getOriginalFilename();
        log.info("originalFileName: " + originalFileName);
        if (originalFileName.toLowerCase().endsWith(".mp3")) {
          audio.setAudioFormat(AudioFormat.MP3);
        } else if (originalFileName.toLowerCase().endsWith(".ogg")) {
          audio.setAudioFormat(AudioFormat.OGG);
        } else if (originalFileName.toLowerCase().endsWith(".wav")) {
          audio.setAudioFormat(AudioFormat.WAV);
        } else {
          result.rejectValue("bytes", "typeMismatch");
        }

        if (audio.getAudioFormat() != null) {
          String contentType = multipartFile.getContentType();
          log.info("contentType: " + contentType);
          audio.setContentType(contentType);

          audio.setBytes(bytes);

          // TODO: convert to a default audio format?

          // Convert from MultipartFile to File, and extract audio duration
          String tmpDir = System.getProperty("java.io.tmpdir");
          File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
          tmpDirElimuAi.mkdir();
          File file = new File(tmpDirElimuAi, multipartFile.getOriginalFilename());
          log.info("file: " + file);
          multipartFile.transferTo(file);
          Long durationMs = AudioMetadataExtractionHelper.getDurationInMilliseconds(file);
          log.info("durationMs: " + durationMs);
          audio.setDurationMs(durationMs);
        }
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    if (result.hasErrors()) {
      model.addAttribute("words", wordDao.readAllOrdered());
      model.addAttribute("storyBookParagraphs", storyBookParagraphDao.readAll());

      model.addAttribute("contentLicenses", ContentLicense.values());

      model.addAttribute("literacySkills", LiteracySkill.values());
      model.addAttribute("numeracySkills", NumeracySkill.values());

      model.addAttribute("timeStart", request.getParameter("timeStart"));

      model.addAttribute("emojisByWordId", getEmojisByWordId());

      return "content/multimedia/audio/create";
    } else {
      audio.setTitle(audio.getTitle().toLowerCase());
      audio.setTimeLastUpdate(Calendar.getInstance());
      audioDao.create(audio);

      AudioContributionEvent audioContributionEvent = new AudioContributionEvent();
      audioContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      audioContributionEvent.setTimestamp(Calendar.getInstance());
      audioContributionEvent.setAudio(audio);
      audioContributionEvent.setRevisionNumber(audio.getRevisionNumber());
      audioContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      audioContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
      audioContributionEventDao.create(audioContributionEvent);

      if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
        String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/multimedia/audio/edit/" + audio.getId();
        DiscordHelper.sendChannelMessage(
            "Audio created: " + contentUrl,
            "\"" + audio.getTranscription() + "\"",
            "Comment: \"" + audioContributionEvent.getComment() + "\"",
            null,
            null
        );
      }

      return "redirect:/content/multimedia/audio/list#" + audio.getId();
    }
  }

  /**
   * See http://www.mkyong.com/spring-mvc/spring-mvc-failed-to-convert-property-value-in-file-upload-form/
   * <p></p>
   * Fixes this error message: "Cannot convert value of type [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile] to required type [byte] for property
   * 'bytes[0]'"
   */
  @InitBinder
  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
    log.info("initBinder");
    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
  }

  private Map<Long, String> getEmojisByWordId() {
    log.info("getEmojisByWordId");

    Map<Long, String> emojisByWordId = new HashMap<>();

    for (Word word : wordDao.readAll()) {
      String emojiGlyphs = "";

      List<Emoji> emojis = emojiDao.readAllLabeled(word);
      for (Emoji emoji : emojis) {
        emojiGlyphs += emoji.getGlyph();
      }

      if (StringUtils.isNotBlank(emojiGlyphs)) {
        emojisByWordId.put(word.getId(), emojiGlyphs);
      }
    }

    return emojisByWordId;
  }
}
