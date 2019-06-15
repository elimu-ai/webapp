package ai.elimu.util.content.multimedia;

import ai.elimu.model.content.StoryBook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.log4j.Logger;

public class EpubToStoryBookConverter {
    
    private static final Logger logger = Logger.getLogger(EpubToStoryBookConverter.class);
    
    public static StoryBook getStoryBookFromEpub(File ePubFile) {
        logger.info("getStoryBookFromEpub");
        
        logger.info("Converting \"" + ePubFile + "\" from ePUB to StoryBook");
        
        StoryBook storyBook = new StoryBook();
        
        // Unzip ePUB
        List<File> unzippedFiles = unzipFiles(ePubFile);
        logger.info("unzippedFiles.size(): " + unzippedFiles.size());
        
        // Extract storybook metadata and cover image from the Open Package Format (OPF) file
        // TODO

        // Iterate chapters and extract images and paragraphs from the Table of Contents (TOC) file
        // TODO

        // Delete the temporary folder
        // TODO
        
        return storyBook;
    }
    
    /**
     * Unzip the contents of the ePUB file to a temporary folder.
     */
    private static List<File> unzipFiles(File ePubFile) {
        logger.info("unzipFiles");
        
        List<File> unzippedFiles = new ArrayList<>();
        
        String tmpDir = System.getProperty("java.io.tmpdir");
        logger.info("tmpDir: " + tmpDir);
        File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
        logger.info("tmpDirElimuAi: " + tmpDirElimuAi);
        logger.info("tmpDirElimuAi.mkdir(): " + tmpDirElimuAi.mkdir());
        File unzipDestinationDirectory = new File(tmpDirElimuAi, ePubFile.getName().replace(" ", "_") + "_unzipped");
        logger.info("unzipDestinationDirectory: " + unzipDestinationDirectory);
        logger.info("unzipDestinationDirectory.mkdir(): " + unzipDestinationDirectory.mkdir());
        byte[] buffer = new byte[1024];
        try {
            FileInputStream fileInputStream = new FileInputStream(ePubFile);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
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
                
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        return unzippedFiles;
    }
}
