package ai.elimu.web.content.storybook;

import ai.elimu.dao.ImageContributionEventDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.ImageContributionEvent;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.v2.enums.ReadingLevel;
import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.ImageColorHelper;
import ai.elimu.util.ImageHelper;
import ai.elimu.util.epub.EPubChapterExtractionHelper;
import ai.elimu.util.epub.EPubImageExtractionHelper;
import ai.elimu.util.epub.EPubMetadataExtractionHelper;
import ai.elimu.util.epub.EPubParagraphExtractionHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/content/storybook/create-from-epub")
public class StoryBookCreateFromEPubController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private ImageContributionEventDao imageContributionEventDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        StoryBook storyBook = new StoryBook();
        model.addAttribute("storyBook", storyBook);
        
        model.addAttribute("timeStart", System.currentTimeMillis());

        return "content/storybook/create-from-epub";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            StoryBook storyBook,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            HttpSession session
    ) throws IOException {
    	logger.info("handleSubmit");
        
        Image storyBookCoverImage = null;
        
        List<StoryBookChapter> storyBookChapters = new ArrayList<>();
        
        List<StoryBookParagraph> storyBookParagraphs = new ArrayList<>();
        
        if (multipartFile.isEmpty()) {
            result.rejectValue("bytes", "NotNull");
        } else {
            String contentType = multipartFile.getContentType();
            logger.info("contentType: " + contentType);
            
            String name = multipartFile.getName();
            logger.info("name: " + name);
            
            String originalFilename = multipartFile.getOriginalFilename();
            logger.info("originalFilename: " + originalFilename);
            
            long size = multipartFile.getSize();
            logger.info("size: " + size + " (" + (size / 1024) + "kB)");
            
            byte[] ePubBytes = multipartFile.getBytes();
            logger.info("ePubBytes.length: " + (ePubBytes.length / 1024 / 1024) + "MB");

            List<File> filesInEPub = unzipFiles(ePubBytes, originalFilename);
            logger.info("filesInEPub.size(): " + filesInEPub.size());

            // Extract the ePUB's metadata from its OPF file
            File opfFile = null;
            for (File file : filesInEPub) {
                if (file.getName().endsWith(".opf")) {
                    opfFile = file;
                }
            }
            logger.info("opfFile: \"" + opfFile + "\"");
            if (opfFile == null) {
                throw new IllegalArgumentException("The OPF file was not found");
            } else {
                String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
                logger.info("title: \"" + title + "\"");
                storyBook.setTitle(title);

                String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
                logger.info("description: \"" + description + "\"");
                if (StringUtils.isNotBlank(description)) {
                    logger.info("description.length(): " + description.length());
                    if (description.length() > 1024) {
                        description = description.substring(0, 1023);
                    }
                    storyBook.setDescription(description);
                }

                storyBookCoverImage = new Image();
                String coverImageReference = EPubMetadataExtractionHelper.extractCoverImageReferenceFromOpfFile(opfFile);
                logger.info("coverImageReference: " + coverImageReference);
                File coverImageFile = new File(opfFile.getParent(), coverImageReference);
                logger.info("coverImageFile: " + coverImageFile);
                logger.info("coverImageFile.exists(): " + coverImageFile.exists());
                URI coverImageUri = coverImageFile.toURI();
                logger.info("coverImageUri: " + coverImageUri);
                byte[] coverImageBytes = IOUtils.toByteArray(coverImageUri);
                storyBookCoverImage.setBytes(coverImageBytes);
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
                if (storyBookCoverImage.getImageFormat() != ImageFormat.GIF) {
                    // Reduce size if large image
                    int imageWidth = ImageHelper.getWidth(coverImageBytes);
                    logger.info("imageWidth: " + imageWidth + "px");
                    if (imageWidth > ImageHelper.MINIMUM_WIDTH) {
                        coverImageBytes = ImageHelper.scaleImage(coverImageBytes, ImageHelper.MINIMUM_WIDTH);
                        storyBookCoverImage.setBytes(coverImageBytes);
                    }
                }
            }

            // Extract the ePUB's chapters
            File tableOfContentsFile = null;
            for (File file : filesInEPub) {
                logger.info("file: " + file);
                if (file.getName().startsWith("toc.")
                        || file.getName().startsWith("nav.")) {
                    tableOfContentsFile = file;
                }
            }
            logger.info("tableOfContentsFile: \"" + tableOfContentsFile + "\"");
            if (tableOfContentsFile == null) {
                throw new IllegalArgumentException("The TOC file was not found");
            } else {
                List<String> chapterReferences = null;
                if (tableOfContentsFile.getName().endsWith(".xhtml")) {
                    // StoryBookProvider#GLOBAL_DIGITAL_LIBRARY or StoryBookProvider#LETS_READ_ASIA
                    chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFile(tableOfContentsFile);
                } else if (tableOfContentsFile.getName().endsWith(".ncx")) {
                    // StoryBookProvider#STORYWEAVER
                    chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFileNcx(tableOfContentsFile);
                }
                logger.info("chapterReferences.size(): " + chapterReferences.size());

                // Extract each chapter's image (if any) and paragraphs
                for (String chapterReference : chapterReferences) {
                    logger.info("chapterReference: \"" + chapterReference + "\"");
                    File chapterFile = new File(opfFile.getParent(), chapterReference);
                    logger.info("chapterFile: \"" + chapterFile + "\"");
                    StoryBookChapter storyBookChapter = new StoryBookChapter();
                    storyBookChapter.setSortOrder(storyBookChapters.size());
                    storyBookChapters.add(storyBookChapter);

                    String chapterImageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(chapterFile);
                    logger.info("chapterImageReference: " + chapterImageReference);
                    if (StringUtils.isNotBlank(chapterImageReference)) {
                        File chapterImageFile = null;
                        if (chapterImageReference.startsWith("http://") || chapterImageReference.startsWith("https://")) {
                            // Download the file
                            
                            URL sourceUrl = new URL(chapterImageReference);
                            
                            String tmpDir = System.getProperty("java.io.tmpdir");
                            logger.info("tmpDir: " + tmpDir);
                            File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
                            logger.info("tmpDirElimuAi: " + tmpDirElimuAi);
                            logger.info("tmpDirElimuAi.mkdir(): " + tmpDirElimuAi.mkdir());
                            chapterImageFile = new File(tmpDirElimuAi, "chapter-image");
                            
                            logger.warn("Downloading image from " + sourceUrl + " and storing at " + chapterImageFile);
                            int connectionTimeout = 1000 * 10; // 1000 milliseconds x 10
                            int readTimeout = 1000 * 10; // 1000 milliseconds x 10
                            FileUtils.copyURLToFile(sourceUrl, chapterImageFile, connectionTimeout, readTimeout);
                        } else {
                            chapterImageFile = new File(chapterFile.getParent(), chapterImageReference);
                        }
                        logger.info("chapterImageFile: " + chapterImageFile);
                        logger.info("chapterImageFile.exists(): " + chapterImageFile.exists());
                        URI chapterImageUri = chapterImageFile.toURI();
                        logger.info("chapterImageUri: " + chapterImageUri);
                        byte[] chapterImageBytes = IOUtils.toByteArray(chapterImageUri);
                        Image chapterImage = new Image();
                        chapterImage.setBytes(chapterImageBytes);
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
                        if (chapterImage.getImageFormat() != ImageFormat.GIF) {
                            // Reduce size if large image
                            int imageWidth = ImageHelper.getWidth(chapterImageBytes);
                            logger.info("imageWidth: " + imageWidth + "px");
                            if (imageWidth > ImageHelper.MINIMUM_WIDTH) {
                                chapterImageBytes = ImageHelper.scaleImage(chapterImageBytes, ImageHelper.MINIMUM_WIDTH);
                                chapterImage.setBytes(chapterImageBytes);
                            }
                        }
                        storyBookChapter.setImage(chapterImage);
                    }

                    List<String> paragraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(chapterFile);
                    logger.info("paragraphs.size(): " + paragraphs.size());
                    for (int i = 0; i < paragraphs.size(); i++) {
                        String paragraph = paragraphs.get(i);
                        logger.info("paragraph: \"" + paragraph + "\"");
                        logger.info("paragraph.length(): " + paragraph.length());

                        StoryBookParagraph storyBookParagraph = new StoryBookParagraph();
                        storyBookParagraph.setStoryBookChapter(storyBookChapter);
                        storyBookParagraph.setSortOrder(i);

                        if (paragraph.length() > 1024) {
                            logger.warn("Reducing the length of the paragraph to its initial 1,024 characters.");
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
            storyBookContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            storyBookContributionEventDao.create(storyBookContributionEvent);
            
            // Store the StoryBook's cover image in the database, and assign it to the StoryBook
            storyBookCoverImage.setTitle("storybook-" + storyBook.getId() + "-cover");
            imageDao.create(storyBookCoverImage);
            storeImageContributionEvent(storyBookCoverImage, session, request);
            storyBook.setCoverImage(storyBookCoverImage);
            storyBookDao.update(storyBook);
            
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
                logger.info("storyBookParagraphsAssociatedWithChapter.size(): " + storyBookParagraphsAssociatedWithChapter.size());
                
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
            logger.info("predictedReadingLevel: " + predictedReadingLevel);
            storyBook.setReadingLevel(predictedReadingLevel);
            storyBookDao.update(storyBook);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
                String embedThumbnailUrl = null;
                if (storyBook.getCoverImage() != null) {
                    embedThumbnailUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/image/" + storyBook.getCoverImage().getId() + "_r" + storyBook.getCoverImage().getRevisionNumber() + "." + storyBook.getCoverImage().getImageFormat().toString().toLowerCase();
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
     * Fixes this error message:
     * "Cannot convert value of type [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile] to required type [byte] for property 'ePubBytes[0]'"
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
    	logger.info("initBinder");
    	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
    
    /**
     * Unzip the contents of the ePUB file to a temporary folder.
     */
    private List<File> unzipFiles(byte[] ePubBytes, String originalFilename) {
        logger.info("unzipFiles");
        
        List<File> unzippedFiles = new ArrayList<>();
        
        String tmpDir = System.getProperty("java.io.tmpdir");
        logger.info("tmpDir: " + tmpDir);
        File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
        logger.info("tmpDirElimuAi: " + tmpDirElimuAi);
        logger.info("tmpDirElimuAi.mkdir(): " + tmpDirElimuAi.mkdir());
        File unzipDestinationDirectory = new File(tmpDirElimuAi, originalFilename.replace(" ", "_") + "_unzipped");
        logger.info("unzipDestinationDirectory: " + unzipDestinationDirectory);
        logger.info("unzipDestinationDirectory.mkdir(): " + unzipDestinationDirectory.mkdir());
        byte[] buffer = new byte[1024];
        try {
            InputStream inputStream = new ByteArrayInputStream(ePubBytes);
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            ZipEntry zipEntry = null;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                logger.info("zipEntry: " + zipEntry);
                
                // E.g. unzipDestinationDirectory + "/" + "META-INF/container.xml"
                File unzipDestinationFile = new File(unzipDestinationDirectory + File.separator + zipEntry.toString());
                logger.info("unzipDestinationFile: " + unzipDestinationFile);
                
                // Create intermediate folders if they do not already exist, e.g. "META-INF/", "content/" or "OEBPS/"
                File parentDirectory = unzipDestinationFile.getParentFile();
                logger.info("parentDirectory: " + parentDirectory);
                if (!parentDirectory.exists()) {
                    boolean parentDirectoriesWereCreated = parentDirectory.mkdirs();
                    logger.info("parentDirectory.mkdirs(): " + parentDirectoriesWereCreated);
                }
                
                // Write file to disk
                FileOutputStream fileOutputStream = new FileOutputStream(unzipDestinationFile);
                int length;
                while ((length = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                fileOutputStream.close();
                
                logger.info("unzipDestinationFile.exists(): " + unzipDestinationFile.exists());
                unzippedFiles.add(unzipDestinationFile);
            }
            zipInputStream.close();
            inputStream.close();
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return unzippedFiles;
    }
    
    private void storeImageContributionEvent(Image image, HttpSession session, HttpServletRequest request) {
        logger.info("storeImageContributionEvent");
        
        ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
        imageContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
        imageContributionEvent.setTimestamp(Calendar.getInstance());
        imageContributionEvent.setImage(image);
        imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
        imageContributionEvent.setComment("Extracted from ePUB file (🤖 auto-generated comment)");
        imageContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
        imageContributionEventDao.create(imageContributionEvent);

        if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
            String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/multimedia/image/edit/" + image.getId();
            String embedThumbnailUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/image/" + image.getId() + "_r" + image.getRevisionNumber() + "." + image.getImageFormat().toString().toLowerCase();
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
        logger.info("predictReadingLevel");

        // Load the machine learning model (https://github.com/elimu-ai/ml-storybook-reading-level)
        String modelFilePath = getClass().getResource("step2_2_model.pmml").getFile();
        logger.info("modelFilePath: " + modelFilePath);
        org.pmml4s.model.Model model = org.pmml4s.model.Model.fromFile(modelFilePath);
        logger.info("model: " + model);

        // Prepare values (features) to pass to the model
        Map<String, Double> values = Map.of(
            "chapter_count", Double.valueOf(chapterCount),
            "paragraph_count", Double.valueOf(paragraphCount),
            "word_count", Double.valueOf(wordCount)
        );
        logger.info("values: " + values);

        // Make prediction
        logger.info("Arrays.toString(model.inputNames()): " + Arrays.toString(model.inputNames()));
        Object[] valuesMap = Arrays.stream(model.inputNames())
                                   .map(values::get)
                                   .toArray();
        logger.info("valuesMap: " + valuesMap);
        Object[] results = model.predict(valuesMap);
        logger.info("results: " + results);
        logger.info("Arrays.toString(results): " + Arrays.toString(results));
        Object result = results[0];
        logger.info("result: " + result);
        logger.info("result.getClass().getSimpleName(): " + result.getClass().getSimpleName());
        Double resultAsDouble = (Double) result;
        logger.info("resultAsDouble: " + resultAsDouble);
        Integer resultAsInteger = resultAsDouble.intValue();
        logger.info("resultAsInteger: " + resultAsInteger);

        // Convert from number to ReadingLevel enum (e.g. "LEVEL2")
        String readingLevelAsString = "LEVEL" + resultAsInteger;
        logger.info("readingLevelAsString: " + readingLevelAsString);
        ReadingLevel readingLevel = ReadingLevel.valueOf(readingLevelAsString);
        logger.info("readingLevel: " + readingLevel);
        return readingLevel;
    }
}
