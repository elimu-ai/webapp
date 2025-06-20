package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageContributionEventDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.ImageContributionEvent;
import ai.elimu.entity.enums.ContentLicense;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import ai.elimu.util.ChecksumHelper;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import ai.elimu.util.GitHubLfsHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/multimedia/image/edit/{id}")
@RequiredArgsConstructor
@Slf4j
public class ImageEditController {

  private final ImageDao imageDao;

  private final ImageContributionEventDao imageContributionEventDao;

  private final LetterDao letterDao;

  private final NumberDao numberDao;

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  @GetMapping
  public String handleRequest(
      HttpServletRequest request,
      HttpSession session,
      Model model,
      @PathVariable Long id) {
    log.info("handleRequest");

    Image image = imageDao.read(id);

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    if ((image.getChecksumGitHub() == null) && (contributor != null)) {
      log.info("image.getUrl(): " + image.getUrl());

      // Download the image file to a temporary folder on the file system
      String tmpDir = System.getProperty("java.io.tmpdir");
      log.info("tmpDir: " + tmpDir);
      File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
      log.info("tmpDirElimuAi: " + tmpDirElimuAi);
      log.info("tmpDirElimuAi.mkdir(): " + tmpDirElimuAi.mkdir());
      Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
      File tmpDirLanguage = new File(tmpDirElimuAi, "lang-" + language);
      log.info("tmpDirLanguage: " + tmpDirLanguage);
      log.info("tmpDirLanguage.mkdir(): " + tmpDirLanguage.mkdir());
      File tmpFileImage = new File(tmpDirLanguage, image.getChecksumMd5() + "." + image.getImageFormat().toString().toLowerCase());
      log.info("tmpFileImage.getPath(): " + tmpFileImage.getPath());
      try {
        FileUtils.copyURLToFile(new URL(image.getUrl()), tmpFileImage);
        log.info("tmpFileImage.exists(): " + tmpFileImage.exists());
        byte[] bytes = IOUtils.toByteArray(tmpFileImage.toURI());
        String existingChecksumGitHub = imageDao.readChecksumGitHub(image.getChecksumMd5());
        if (StringUtils.isNotBlank(existingChecksumGitHub)) {
          // Re-use existing checksum previously returned from the GitHub API
          image.setChecksumGitHub(existingChecksumGitHub);
        } else {
          String checksumGitHub = GitHubLfsHelper.uploadImageToLfs(image, bytes);
          image.setChecksumGitHub(checksumGitHub);
        }
        image.setRevisionNumber(image.getRevisionNumber() + 1);
        imageDao.update(image);

        ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
        imageContributionEvent.setContributor(contributor);
        imageContributionEvent.setTimestamp(Calendar.getInstance());
        imageContributionEvent.setImage(image);
        imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
        imageContributionEvent.setComment("Updated file name in LFS (🤖 auto-generated comment)");
        imageContributionEventDao.create(imageContributionEvent);
      } catch (IOException e) {
        log.error(null, e);
      }
    }

    model.addAttribute("image", image);
    model.addAttribute("contentLicenses", ContentLicense.values());
    model.addAttribute("literacySkills", LiteracySkill.values());
    model.addAttribute("numeracySkills", NumeracySkill.values());

    model.addAttribute("imageContributionEvents", imageContributionEventDao.readAll(image));

    model.addAttribute("letters", letterDao.readAllOrdered());
    model.addAttribute("numbers", numberDao.readAllOrdered());
    model.addAttribute("words", wordDao.readAllOrdered());
    model.addAttribute("emojisByWordId", getEmojisByWordId());

    return "content/multimedia/image/edit";
  }

  @PostMapping
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      Image image,
      @RequestParam("bytes") MultipartFile multipartFile,
      BindingResult result,
      Model model) {
    log.info("handleSubmit");

    if (StringUtils.isBlank(image.getTitle())) {
      result.rejectValue("title", "NotNull");
    } else {
      Image existingImage = imageDao.read(image.getTitle());
      if ((existingImage != null) && !existingImage.getId().equals(image.getId())) {
        result.rejectValue("title", "NonUnique");
      }
    }

    byte[] bytes = null;
    try {
      bytes = multipartFile.getBytes();
      if (multipartFile.isEmpty() || (bytes == null) || (bytes.length == 0)) {
        result.rejectValue("bytes", "NotNull");
      } else {
        String originalFileName = multipartFile.getOriginalFilename();
        log.info("originalFileName: " + originalFileName);

        byte[] headerBytes = Arrays.copyOfRange(bytes, 0, 6);
        byte[] gifHeader87a = {71, 73, 70, 56, 55, 97}; // "GIF87a"
        byte[] gifHeader89a = {71, 73, 70, 56, 57, 97}; // "GIF89a"
        if (Arrays.equals(gifHeader87a, headerBytes) || Arrays.equals(gifHeader89a, headerBytes)) {
          image.setImageFormat(ImageFormat.GIF);
        } else if (originalFileName.toLowerCase().endsWith(".png")) {
          image.setImageFormat(ImageFormat.PNG);
        } else if (originalFileName.toLowerCase().endsWith(".jpg") || originalFileName.toLowerCase().endsWith(".jpeg")) {
          image.setImageFormat(ImageFormat.JPG);
        } else if (originalFileName.toLowerCase().endsWith(".gif")) {
          image.setImageFormat(ImageFormat.GIF);
        } else {
          result.rejectValue("bytes", "typeMismatch");
        }

        if (image.getImageFormat() != null) {
          String contentType = multipartFile.getContentType();
          log.info("contentType: " + contentType);
          image.setContentType(contentType);

          image.setFileSize(bytes.length);
          image.setChecksumMd5(ChecksumHelper.calculateMD5(bytes));
        }
      }
    } catch (IOException e) {
      log.error(e.getMessage());
    }

    if (result.hasErrors()) {
      model.addAttribute("image", image);
      model.addAttribute("contentLicenses", ContentLicense.values());
      model.addAttribute("literacySkills", LiteracySkill.values());
      model.addAttribute("numeracySkills", NumeracySkill.values());

      model.addAttribute("imageContributionEvents", imageContributionEventDao.readAll(image));

      model.addAttribute("letters", letterDao.readAllOrdered());
      model.addAttribute("numbers", numberDao.readAllOrdered());
      model.addAttribute("words", wordDao.readAllOrdered());
      model.addAttribute("emojisByWordId", getEmojisByWordId());

      return "content/multimedia/image/edit";
    } else {
      image.setTitle(image.getTitle().toLowerCase());
      image.setRevisionNumber(image.getRevisionNumber() + 1);
      String checksumGitHub = GitHubLfsHelper.uploadImageToLfs(image, bytes);
      image.setChecksumGitHub(checksumGitHub);
      imageDao.update(image);

      ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
      imageContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      imageContributionEvent.setTimestamp(Calendar.getInstance());
      imageContributionEvent.setImage(image);
      imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
      imageContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      imageContributionEventDao.create(imageContributionEvent);

      String contentUrl = DomainHelper.getBaseUrl() + "/content/multimedia/image/edit/" + image.getId();
      String embedThumbnailUrl = image.getUrl();
      DiscordHelper.postToChannel(
          Channel.CONTENT,
          "Image edited: " + contentUrl,
          "\"" + image.getTitle() + "\"",
          "Comment: \"" + imageContributionEvent.getComment() + "\"",
          null,
          embedThumbnailUrl
      );

      return "redirect:/content/multimedia/image/list#" + image.getId();
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

  @PostMapping(value = "/add-content-label")
  @ResponseBody
  public String handleAddContentLabelRequest(
      HttpServletRequest request,
      HttpSession session,
      @PathVariable Long id) {
    log.info("handleAddContentLabelRequest");

    log.info("id: " + id);
    Image image = imageDao.read(id);

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    String letterIdParameter = request.getParameter("letterId");
    log.info("letterIdParameter: " + letterIdParameter);
    if (StringUtils.isNotBlank(letterIdParameter)) {
      Long letterId = Long.valueOf(letterIdParameter);
      Letter letter = letterDao.read(letterId);
      Set<Letter> letters = image.getLetters();
      if (!letters.contains(letter)) {
        letters.add(letter);
        image.setRevisionNumber(image.getRevisionNumber() + 1);
        imageDao.update(image);

        ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
        imageContributionEvent.setContributor(contributor);
        imageContributionEvent.setTimestamp(Calendar.getInstance());
        imageContributionEvent.setImage(image);
        imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
        imageContributionEvent.setComment("Add letter label: \"" + letter.getText() + "\" (🤖 auto-generated comment)");
        imageContributionEventDao.create(imageContributionEvent);
      }
    }

    String numberIdParameter = request.getParameter("numberId");
    log.info("numberIdParameter: " + numberIdParameter);
    if (StringUtils.isNotBlank(numberIdParameter)) {
      Long numberId = Long.valueOf(numberIdParameter);
      Number number = numberDao.read(numberId);
      Set<Number> numbers = image.getNumbers();
      if (!numbers.contains(number)) {
        numbers.add(number);
        image.setRevisionNumber(image.getRevisionNumber() + 1);
        imageDao.update(image);

        ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
        imageContributionEvent.setContributor(contributor);
        imageContributionEvent.setTimestamp(Calendar.getInstance());
        imageContributionEvent.setImage(image);
        imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
        imageContributionEvent.setComment("Add number label: " + number.getValue() + " (🤖 auto-generated comment)");
        imageContributionEventDao.create(imageContributionEvent);
      }
    }

    String wordIdParameter = request.getParameter("wordId");
    log.info("wordIdParameter: " + wordIdParameter);
    if (StringUtils.isNotBlank(wordIdParameter)) {
      Long wordId = Long.valueOf(wordIdParameter);
      Word word = wordDao.read(wordId);
      Set<Word> words = image.getWords();
      if (!words.contains(word)) {
        words.add(word);
        image.setRevisionNumber(image.getRevisionNumber() + 1);
        imageDao.update(image);

        ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
        imageContributionEvent.setContributor(contributor);
        imageContributionEvent.setTimestamp(Calendar.getInstance());
        imageContributionEvent.setImage(image);
        imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
        imageContributionEvent.setComment("Add word label: \"" + word.getText() + "\" (🤖 auto-generated comment)");
        imageContributionEventDao.create(imageContributionEvent);
      }
    }

    return "success";
  }

  @PostMapping(value = "/remove-content-label")
  @ResponseBody
  public String handleRemoveContentLabelRequest(
      HttpServletRequest request,
      HttpSession session,
      @PathVariable Long id) {
    log.info("handleRemoveContentLabelRequest");

    log.info("id: " + id);
    Image image = imageDao.read(id);

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    String letterIdParameter = request.getParameter("letterId");
    log.info("letterIdParameter: " + letterIdParameter);
    if (StringUtils.isNotBlank(letterIdParameter)) {
      Long letterId = Long.valueOf(letterIdParameter);
      Letter letter = letterDao.read(letterId);
      Set<Letter> letters = image.getLetters();
      Iterator<Letter> iterator = letters.iterator();
      while (iterator.hasNext()) {
        Letter existingLetter = iterator.next();
        if (existingLetter.getId().equals(letter.getId())) {
          iterator.remove();
        }
      }
      image.setRevisionNumber(image.getRevisionNumber() + 1);
      imageDao.update(image);

      ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
      imageContributionEvent.setContributor(contributor);
      imageContributionEvent.setTimestamp(Calendar.getInstance());
      imageContributionEvent.setImage(image);
      imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
      imageContributionEvent.setComment("Remove letter label: \"" + letter.getText() + "\" (🤖 auto-generated comment)");
      imageContributionEventDao.create(imageContributionEvent);
    }

    String numberIdParameter = request.getParameter("numberId");
    log.info("numberIdParameter: " + numberIdParameter);
    if (StringUtils.isNotBlank(numberIdParameter)) {
      Long numberId = Long.valueOf(numberIdParameter);
      Number number = numberDao.read(numberId);
      Set<Number> numbers = image.getNumbers();
      Iterator<Number> iterator = numbers.iterator();
      while (iterator.hasNext()) {
        Number existingNumber = iterator.next();
        if (existingNumber.getId().equals(number.getId())) {
          iterator.remove();
        }
      }
      image.setRevisionNumber(image.getRevisionNumber() + 1);
      imageDao.update(image);

      ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
      imageContributionEvent.setContributor(contributor);
      imageContributionEvent.setTimestamp(Calendar.getInstance());
      imageContributionEvent.setImage(image);
      imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
      imageContributionEvent.setComment("Remove number label: " + number.getValue() + " (🤖 auto-generated comment)");
      imageContributionEventDao.create(imageContributionEvent);
    }

    String wordIdParameter = request.getParameter("wordId");
    log.info("wordIdParameter: " + wordIdParameter);
    if (StringUtils.isNotBlank(wordIdParameter)) {
      Long wordId = Long.valueOf(wordIdParameter);
      Word word = wordDao.read(wordId);
      Set<Word> words = image.getWords();
      Iterator<Word> iterator = words.iterator();
      while (iterator.hasNext()) {
        Word existingWord = iterator.next();
        if (existingWord.getId().equals(word.getId())) {
          iterator.remove();
        }
      }
      image.setRevisionNumber(image.getRevisionNumber() + 1);
      imageDao.update(image);

      ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
      imageContributionEvent.setContributor(contributor);
      imageContributionEvent.setTimestamp(Calendar.getInstance());
      imageContributionEvent.setImage(image);
      imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
      imageContributionEvent.setComment("Remove word label: \"" + word.getText() + "\" (🤖 auto-generated comment)");
      imageContributionEventDao.create(imageContributionEvent);
    }

    return "success";
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
