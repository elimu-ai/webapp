package ai.elimu.web.content.storybook;

import ai.elimu.dao.StoryBookDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.epub.EPubMetadataExtractionHelper;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storyBookDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        StoryBook storyBook = new StoryBook();
        model.addAttribute("storyBook", storyBook);

        return "content/storybook/create-from-epub";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            StoryBook storyBook,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model,
            HttpSession session
    ) {
    	logger.info("handleSubmit");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        storyBook.setLanguage(language);
        
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
            
            try {    
                byte[] bytes = multipartFile.getBytes();
                logger.info("bytes.length: " + (bytes.length / 1024 / 1024) + "MB");
                 
                List<File> filesInEPub = unzipFiles(bytes, originalFilename);
                logger.info("filesInEPub.size(): " + filesInEPub.size());
                
                // Extract the ePUB's metadata from its OPF file
                File opfFile = null;
                for (File file : filesInEPub) {
                    if (file.getName().endsWith(".opf")) {
                        opfFile = file;
                    }
                }
                if (opfFile == null) {
                    throw new FileNotFoundException("The OPF file was not found");
                } else {
                    String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
                    logger.info("title: " + title);
                    storyBook.setTitle(title);
                    
                    String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
                    logger.info("description: " + description);
                    storyBook.setDescription(description);
                }
                
                // Extract the ePUB's chapters
                // TODO

                // Extract the ePUB's paragraphs
                // TODO
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
        
        if (result.hasErrors()) {
            return "content/storybook/create-from-epub";
        } else {
            // Save the StoryBook, StoryBookChapters and StoryBookParagraphs in the database
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storyBookDao.create(storyBook);
            
            return "redirect:/content/storybook/edit/" + storyBook.getId();
        }
    }
    
    /**
     * See http://www.mkyong.com/spring-mvc/spring-mvc-failed-to-convert-property-value-in-file-upload-form/
     * <p></p>
     * Fixes this error message:
     * "Cannot convert value of type [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile] to required type [byte] for property 'bytes[0]'"
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
    	logger.info("initBinder");
    	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
    
    /**
     * Unzip the contents of the ePUB file to a temporary folder.
     */
    private List<File> unzipFiles(byte[] bytes, String originalFilename) {
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
            InputStream inputStream = new ByteArrayInputStream(bytes);
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            ZipEntry zipEntry = null;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                logger.info("zipEntry: " + zipEntry);
                
                // Create intermediate folders.
                File metaInfDirectory = new File(unzipDestinationDirectory, "META-INF");
                logger.info("metaInfDirectory.mkdir(): " + metaInfDirectory.mkdir());
                File contentDirectory = new File(unzipDestinationDirectory, "content");
                logger.info("contentDirectory.mkdir(): " + contentDirectory.mkdir());
                
                // E.g. unzipDestinationDirectory + "/" + "META-INF/container.xml"
                File unzipDestinationFile = new File(unzipDestinationDirectory + File.separator + zipEntry.toString());
                logger.info("unzipDestinationFile: " + unzipDestinationFile);
                
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
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        return unzippedFiles;
    }
}
