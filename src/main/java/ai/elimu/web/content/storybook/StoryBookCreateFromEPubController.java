package ai.elimu.web.content.storybook;

import ai.elimu.dao.ImageContributionEventDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.ImageContributionEvent;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.model.v2.enums.ReadingLevel;
import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.service.storybook.StoryBookEPubService;
import ai.elimu.util.ChecksumHelper;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.GitHubLfsHelper;
import ai.elimu.util.ImageColorHelper;
import ai.elimu.util.epub.EPubChapterExtractionHelper;
import ai.elimu.util.epub.EPubImageExtractionHelper;
import ai.elimu.util.epub.EPubMetadataExtractionHelper;
import ai.elimu.util.epub.EPubParagraphExtractionHelper;
import ai.elimu.util.ml.ReadingLevelUtil;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
@RequestMapping("/content/storybook/create-from-epub")
@RequiredArgsConstructor
@Slf4j
public class StoryBookCreateFromEPubController {

  private final StoryBookDao storyBookDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final ImageDao imageDao;

  private final ImageContributionEventDao imageContributionEventDao;

  private final StoryBookChapterDao storyBookChapterDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final StoryBookEPubService storyBookEPubService;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    StoryBook storyBook = new StoryBook();
    model.addAttribute("storyBook", storyBook);

    return "content/storybook/create-from-epub";
  }

  @PostMapping
  public String handleSubmit(
      StoryBook storyBook,
      @RequestParam("bytes") MultipartFile multipartFile,
      BindingResult result,
      Model model,
      HttpServletRequest request,
      HttpSession session
  ) throws IOException {
    log.info("handleSubmit");

    Image storyBookCoverImage = null;

    List<StoryBookChapter> storyBookChapters = new ArrayList<>();

    List<StoryBookParagraph> storyBookParagraphs = new ArrayList<>();

    if (multipartFile.isEmpty()) {
      result.rejectValue("bytes", "NotNull");
    } else {
      String contentType = multipartFile.getContentType();
      log.info("contentType: " + contentType);

      String name = multipartFile.getName();
      log.info("name: " + name);

      String originalFilename = multipartFile.getOriginalFilename();
      log.info("originalFilename: " + originalFilename);

      long size = multipartFile.getSize();
      log.info("size: " + size + " (" + (size / 1024) + "kB)");

      byte[] ePubBytes = multipartFile.getBytes();
      log.info("ePubBytes.length: " + (ePubBytes.length / 1024 / 1024) + "MB");

      List<File> filesInEPub = unzipFiles(ePubBytes, originalFilename);
      log.info("filesInEPub.size(): " + filesInEPub.size());

      // Extract the ePUB's metadata from its OPF file
      File opfFile = null;
      for (File file : filesInEPub) {
        if (file.getName().endsWith(".opf")) {
          opfFile = file;
        }
      }
      log.info("opfFile: \"" + opfFile + "\"");
      if (opfFile == null) {
        throw new IllegalArgumentException("The OPF file was not found");
      } else {
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        log.info("title: \"" + title + "\"");
        storyBook.setTitle(title);

        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        log.info("description: \"" + description + "\"");
        if (StringUtils.isNotBlank(description)) {
          log.info("description.length(): " + description.length());
          if (description.length() > 1024) {
            description = description.substring(0, 1023);
          }
          storyBook.setDescription(description);
        }

        storyBookCoverImage = new Image();
        String coverImageReference = EPubMetadataExtractionHelper.extractCoverImageReferenceFromOpfFile(opfFile);
        log.info("coverImageReference: " + coverImageReference);
        File coverImageFile = new File(opfFile.getParent(), coverImageReference);
        log.info("coverImageFile: " + coverImageFile);
        log.info("coverImageFile.exists(): " + coverImageFile.exists());
        URI coverImageUri = coverImageFile.toURI();
        log.info("coverImageUri: " + coverImageUri);
        byte[] coverImageBytes = IOUtils.toByteArray(coverImageUri);
        storyBookCoverImage.setBytes(coverImageBytes);
        storyBookCoverImage.setFileSize(coverImageBytes.length);
        storyBookCoverImage.setChecksumMd5(ChecksumHelper.calculateMD5(coverImageBytes));
        byte[] headerBytes = Arrays.copyOfRange(coverImageBytes, 0, 6);
        byte[] gifHeader87a = {71, 73, 70, 56, 55, 97}; // "GIF87a"
        byte[] gifHeader89a = {71, 73, 70, 56, 57, 97}; // "GIF89a"
        if (Arrays.equals(gifHeader87a, headerBytes) || Arrays.equals(gifHeader89a, headerBytes)) {
          storyBookCoverImage.setContentType("image/gif");
          storyBookCoverImage.setImageFormat(ImageFormat.GIF);
        } else if (coverImageFile.getName().toLowerCase().endsWith(".png")) {
          storyBookCoverImage.setContentType("image/png");
          storyBookCoverImage.setImageFormat(ImageFormat.PNG);
        } else if (coverImageFile.getName().toLowerCase().endsWith(".jpg") || coverImageFile.getName().toLowerCase().endsWith(".jpeg")) {
          storyBookCoverImage.setContentType("image/jpg");
          storyBookCoverImage.setImageFormat(ImageFormat.JPG);
        } else if (coverImageFile.getName().toLowerCase().endsWith(".gif")) {
          storyBookCoverImage.setContentType("image/gif");
          storyBookCoverImage.setImageFormat(ImageFormat.GIF);
        }
        try {
          int[] dominantColor = ImageColorHelper.getDominantColor(storyBookCoverImage.getBytes());
          storyBookCoverImage.setDominantColor("rgb(" + dominantColor[0] + "," + dominantColor[1] + "," + dominantColor[2] + ")");
        } catch (NullPointerException ex) {
          // javax.imageio.IIOException: Unsupported Image Type
        }
      }

      // Extract the ePUB's chapters
      File tableOfContentsFile = null;
      for (File file : filesInEPub) {
        log.info("file: " + file);
        if (file.getName().startsWith("toc.")
            || file.getName().startsWith("nav.")) {
          tableOfContentsFile = file;
        }
      }
      log.info("tableOfContentsFile: \"" + tableOfContentsFile + "\"");
      if (tableOfContentsFile == null) {
        throw new IllegalArgumentException("The TOC file was not found");
      } else {
        List<String> chapterReferences = null;
        if (storyBookEPubService.isTableOfContentsFileHtmlLike(tableOfContentsFile.getName())) {
          // StoryBookProvider#GLOBAL_DIGITAL_LIBRARY or StoryBookProvider#LETS_READ_ASIA
          chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFile(tableOfContentsFile);
        } else if (tableOfContentsFile.getName().endsWith(".ncx")) {
          // StoryBookProvider#STORYWEAVER
          chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFileNcx(tableOfContentsFile);
        }
        log.info("chapterReferences.size(): " + chapterReferences.size());

        // Extract each chapter's image (if any) and paragraphs
        for (String chapterReference : chapterReferences) {
          log.info("chapterReference: \"" + chapterReference + "\"");
          File chapterFile = new File(opfFile.getParent(), chapterReference);
          log.info("chapterFile: \"" + chapterFile + "\"");
          StoryBookChapter storyBookChapter = new StoryBookChapter();
          storyBookChapter.setSortOrder(storyBookChapters.size());
          storyBookChapters.add(storyBookChapter);

          String chapterImageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(chapterFile);
          log.info("chapterImageReference: " + chapterImageReference);
          if (StringUtils.isNotBlank(chapterImageReference)) {
            File chapterImageFile = null;
            if (chapterImageReference.startsWith("http://") || chapterImageReference.startsWith("https://")) {
              // Download the file

              URL sourceUrl = new URL(chapterImageReference);

              String tmpDir = System.getProperty("java.io.tmpdir");
              log.info("tmpDir: " + tmpDir);
              File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
              log.info("tmpDirElimuAi: " + tmpDirElimuAi);
              log.info("tmpDirElimuAi.mkdir(): " + tmpDirElimuAi.mkdir());
              chapterImageFile = new File(tmpDirElimuAi, "chapter-image");

              log.warn("Downloading image from " + sourceUrl + " and storing at " + chapterImageFile);
              int connectionTimeout = 1000 * 10; // 1000 milliseconds x 10
              int readTimeout = 1000 * 10; // 1000 milliseconds x 10
              FileUtils.copyURLToFile(sourceUrl, chapterImageFile, connectionTimeout, readTimeout);
            } else {
              chapterImageFile = new File(chapterFile.getParent(), chapterImageReference);
            }
            log.info("chapterImageFile: " + chapterImageFile);
            log.info("chapterImageFile.exists(): " + chapterImageFile.exists());
            URI chapterImageUri = chapterImageFile.toURI();
            log.info("chapterImageUri: " + chapterImageUri);
            byte[] chapterImageBytes = IOUtils.toByteArray(chapterImageUri);
            Image chapterImage = new Image();
            chapterImage.setBytes(chapterImageBytes);
            chapterImage.setFileSize(chapterImageBytes.length);
            chapterImage.setChecksumMd5(ChecksumHelper.calculateMD5(chapterImageBytes));
            byte[] headerBytes = Arrays.copyOfRange(chapterImageBytes, 0, 6);
            byte[] gifHeader87a = {71, 73, 70, 56, 55, 97}; // "GIF87a"
            byte[] gifHeader89a = {71, 73, 70, 56, 57, 97}; // "GIF89a"
            if (Arrays.equals(gifHeader87a, headerBytes) || Arrays.equals(gifHeader89a, headerBytes)) {
              chapterImage.setContentType("image/gif");
              chapterImage.setImageFormat(ImageFormat.GIF);
            } else if (chapterImageFile.getName().toLowerCase().endsWith(".png")) {
              chapterImage.setContentType("image/png");
              chapterImage.setImageFormat(ImageFormat.PNG);
            } else if (chapterImageFile.getName().toLowerCase().endsWith(".jpg") || chapterImageFile.getName().toLowerCase().endsWith(".jpeg")) {
              chapterImage.setContentType("image/jpg");
              chapterImage.setImageFormat(ImageFormat.JPG);
            } else if (chapterImageFile.getName().toLowerCase().endsWith(".gif")) {
              chapterImage.setContentType("image/gif");
              chapterImage.setImageFormat(ImageFormat.GIF);
            }
            try {
              int[] dominantColor = ImageColorHelper.getDominantColor(chapterImage.getBytes());
              chapterImage.setDominantColor("rgb(" + dominantColor[0] + "," + dominantColor[1] + "," + dominantColor[2] + ")");
            } catch (NullPointerException ex) {
              // javax.imageio.IIOException: Unsupported Image Type
            }
            storyBookChapter.setImage(chapterImage);
          }

          List<String> paragraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(chapterFile);
          log.info("paragraphs.size(): " + paragraphs.size());
          for (int i = 0; i < paragraphs.size(); i++) {
            String paragraph = paragraphs.get(i);
            log.info("paragraph: \"" + paragraph + "\"");
            log.info("paragraph.length(): " + paragraph.length());

            StoryBookParagraph storyBookParagraph = new StoryBookParagraph();
            storyBookParagraph.setStoryBookChapter(storyBookChapter);
            storyBookParagraph.setSortOrder(i);

            if (paragraph.length() > 1024) {
              log.warn("Reducing the length of the paragraph to its initial 1,024 characters.");
              paragraph = paragraph.substring(0, 1023);
            }
            storyBookParagraph.setOriginalText(paragraph);

            // Note: updating the paragraph's list of Words is handled by the ParagraphWordScheduler

            storyBookParagraphs.add(storyBookParagraph);
          }
        }
      }
    }

    if (result.hasErrors()) {
      return "content/storybook/create-from-epub";
    } else {
      // Store the StoryBook in the database
      storyBook.setTimeLastUpdate(Calendar.getInstance());
      storyBookDao.create(storyBook);

      StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
      storyBookContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      storyBookContributionEvent.setTimestamp(Calendar.getInstance());
      storyBookContributionEvent.setStoryBook(storyBook);
      storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
      storyBookContributionEvent.setComment("Uploaded ePUB file (🤖 auto-generated comment)");
      storyBookContributionEventDao.create(storyBookContributionEvent);

      // Store the StoryBook's cover image in the database, and assign it to the StoryBook
      storyBookCoverImage.setTitle("storybook-" + storyBook.getId() + "-cover");
      imageDao.create(storyBookCoverImage);
      storyBook.setCoverImage(storyBookCoverImage);
      storyBookDao.update(storyBook);

      String gitHubHash = GitHubLfsHelper.uploadImageToLfs(storyBookCoverImage);
      storyBookCoverImage.setCid(gitHubHash);
      imageDao.update(storyBookCoverImage);

      storeImageContributionEvent(storyBookCoverImage, session, request);

      // Store the StoryBookChapters in the database
      int chapterSortOrder = 0;
      for (StoryBookChapter storyBookChapter : storyBookChapters) {
        storyBookChapter.setStoryBook(storyBook);

        // Get the paragraphs associated with this chapter
        List<StoryBookParagraph> storyBookParagraphsAssociatedWithChapter = new ArrayList<>();
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
          if (storyBookParagraph.getStoryBookChapter().getSortOrder().equals(storyBookChapter.getSortOrder())) {
            storyBookParagraphsAssociatedWithChapter.add(storyBookParagraph);
          }
        }
        log.info("storyBookParagraphsAssociatedWithChapter.size(): " + storyBookParagraphsAssociatedWithChapter.size());

        // Exclude chapters containing book metadata
        boolean isMetadata = false;
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphsAssociatedWithChapter) {
          String originalTextLowerCase = storyBookParagraph.getOriginalText().toLowerCase();
          if (
              originalTextLowerCase.contains("author: ")
                  || originalTextLowerCase.contains("illustrator: ")
                  || originalTextLowerCase.contains("translator: ")
                  || originalTextLowerCase.contains("creative commons")
                  || originalTextLowerCase.contains("pratham books")
                  || originalTextLowerCase.contains("storyweaver")
                  || originalTextLowerCase.contains("copyright page")
                  || originalTextLowerCase.contains("by smart axiata") // StoryBookProvider#LETS_READ_ASIA
                  || originalTextLowerCase.contains("the asia foundation") // StoryBookProvider#LETS_READ_ASIA
                  || originalTextLowerCase.contains("এশিয়া ফাউন্ডেশনের") // StoryBookProvider#LETS_READ_ASIA
          ) {
            isMetadata = true;
            break;
          }
        }
        if (isMetadata) {
          continue;
        }

        // Update the chapter's sort order (in case any of the previous chapters were excluded)
        storyBookChapter.setSortOrder(chapterSortOrder);

        // Store the chapter's image (if any)
        Image chapterImage = storyBookChapter.getImage();
        if (chapterImage != null) {
          chapterImage.setTitle("storybook-" + storyBook.getId() + "-ch-" + (storyBookChapter.getSortOrder() + 1));
          imageDao.create(chapterImage);

          gitHubHash = GitHubLfsHelper.uploadImageToLfs(chapterImage);
          chapterImage.setCid(gitHubHash);
          imageDao.update(chapterImage);

          storeImageContributionEvent(chapterImage, session, request);
        }

        // Only store the chapter if it has an image or at least one paragraph
        if ((chapterImage != null) || (!storyBookParagraphsAssociatedWithChapter.isEmpty())) {
          storyBookChapterDao.create(storyBookChapter);
          chapterSortOrder++;
        }

        // Store the chapter's paragraphs in the database
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphsAssociatedWithChapter) {
          storyBookParagraph.setStoryBookChapter(storyBookChapter);
          storyBookParagraphDao.create(storyBookParagraph);
        }
      }

      List<StoryBookChapter> chapters = storyBookChapterDao.readAll(storyBook);
      int chapterCount = chapters.size();
      int paragraphCount = 0;
      int wordCount = 0;
      for (StoryBookChapter chapter : chapters) {
        List<StoryBookParagraph> paragraphs = storyBookParagraphDao.readAll(chapter);
        paragraphCount += paragraphs.size();
        for (StoryBookParagraph paragraph : paragraphs) {
          wordCount += paragraph.getOriginalText().split(" ").length;
        }
      }
      ReadingLevel predictedReadingLevel = predictReadingLevel(chapterCount, paragraphCount, wordCount);
      log.info("predictedReadingLevel: " + predictedReadingLevel);
      storyBook.setReadingLevel(predictedReadingLevel);
      storyBookDao.update(storyBook);

      if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
        String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
        String embedThumbnailUrl = null;
        if (storyBook.getCoverImage() != null) {
          embedThumbnailUrl = storyBook.getCoverImage().getUrl();
        }
        DiscordHelper.sendChannelMessage(
            "Storybook created (imported from ePUB): " + contentUrl,
            "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
            "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
            null,
            embedThumbnailUrl
        );
      }

      return "redirect:/content/storybook/edit/" + storyBook.getId();
    }
  }

  /**
   * See http://www.mkyong.com/spring-mvc/spring-mvc-failed-to-convert-property-value-in-file-upload-form/
   * <p></p>
   * Fixes this error message: "Cannot convert value of type [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile] to required type [byte] for property
   * 'ePubBytes[0]'"
   */
  @InitBinder
  protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
    log.info("initBinder");
    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
  }

  /**
   * Unzip the contents of the ePUB file to a temporary folder.
   */
  private List<File> unzipFiles(byte[] ePubBytes, String originalFilename) {
    log.info("unzipFiles");

    List<File> unzippedFiles = new ArrayList<>();

    String tmpDir = System.getProperty("java.io.tmpdir");
    log.info("tmpDir: " + tmpDir);
    File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
    log.info("tmpDirElimuAi: " + tmpDirElimuAi);
    log.info("tmpDirElimuAi.mkdir(): " + tmpDirElimuAi.mkdir());
    File unzipDestinationDirectory = new File(tmpDirElimuAi, originalFilename.replace(" ", "_") + "_unzipped");
    log.info("unzipDestinationDirectory: " + unzipDestinationDirectory);
    log.info("unzipDestinationDirectory.mkdir(): " + unzipDestinationDirectory.mkdir());
    byte[] buffer = new byte[1024];
    try {
      InputStream inputStream = new ByteArrayInputStream(ePubBytes);
      ZipInputStream zipInputStream = new ZipInputStream(inputStream);
      ZipEntry zipEntry = null;
      while ((zipEntry = zipInputStream.getNextEntry()) != null) {
        log.info("zipEntry: " + zipEntry);

        // E.g. unzipDestinationDirectory + "/" + "META-INF/container.xml"
        File unzipDestinationFile = new File(unzipDestinationDirectory + File.separator + zipEntry.toString());
        log.info("unzipDestinationFile: " + unzipDestinationFile);

        // Create intermediate folders if they do not already exist, e.g. "META-INF/", "content/" or "OEBPS/"
        File parentDirectory = unzipDestinationFile.getParentFile();
        log.info("parentDirectory: " + parentDirectory);
        if (!parentDirectory.exists()) {
          boolean parentDirectoriesWereCreated = parentDirectory.mkdirs();
          log.info("parentDirectory.mkdirs(): " + parentDirectoriesWereCreated);
        }

        // Write file to disk
        FileOutputStream fileOutputStream = new FileOutputStream(unzipDestinationFile);
        int length;
        while ((length = zipInputStream.read(buffer)) > 0) {
          fileOutputStream.write(buffer, 0, length);
        }
        fileOutputStream.close();

        log.info("unzipDestinationFile.exists(): " + unzipDestinationFile.exists());
        unzippedFiles.add(unzipDestinationFile);
      }
      zipInputStream.close();
      inputStream.close();
    } catch (FileNotFoundException ex) {
      log.error(ex.getMessage());
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }

    return unzippedFiles;
  }

  private void storeImageContributionEvent(Image image, HttpSession session, HttpServletRequest request) {
    log.info("storeImageContributionEvent");

    ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
    imageContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
    imageContributionEvent.setTimestamp(Calendar.getInstance());
    imageContributionEvent.setImage(image);
    imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
    imageContributionEvent.setComment("Extracted from ePUB file (🤖 auto-generated comment)");
    imageContributionEventDao.create(imageContributionEvent);

    if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
      String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/multimedia/image/edit/" + image.getId();
      String embedThumbnailUrl = image.getUrl();
      DiscordHelper.sendChannelMessage(
          "Image created: " + contentUrl,
          "\"" + image.getTitle() + "\"",
          "Comment: \"" + imageContributionEvent.getComment() + "\"",
          null,
          embedThumbnailUrl
      );
    }
  }

  private ReadingLevel predictReadingLevel(int chapterCount, int paragraphCount, int wordCount) {

    // Load the machine learning model (https://github.com/elimu-ai/ml-storybook-reading-level)

    log.info(
        "Predicting reading level for chapter: {}, paragraph: {}, word: {} ",
        chapterCount, paragraphCount, wordCount
    );

    ReadingLevel readingLevel = ReadingLevelUtil.predictReadingLevel(chapterCount, paragraphCount, wordCount);
    log.info("Predicted reading level: {}", readingLevel);

    return readingLevel;
  }
}
