package ai.elimu.util.content.multimedia;

import ai.elimu.model.content.StoryBook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import org.apache.log4j.Logger;

public class EpubToStoryBookConverter {
    
    private static final Logger logger = Logger.getLogger(EpubToStoryBookConverter.class);
    
    public static StoryBook getStoryBookFromEpub(File ePubFile) {
        logger.info("getStoryBookFromEpub");
        
        logger.info("Converting \"" + ePubFile + "\" from ePUB to StoryBook");
        
        StoryBook storyBook = new StoryBook();
        
        // Read ePUB
        EpubReader epubReader = new EpubReader();
        try {
            Book book = epubReader.readEpub(new FileInputStream(ePubFile));
            logger.info("book.getTitle(): \"" + book.getTitle() + "\"");
            storyBook.setTitle(book.getTitle());
            
            List<TOCReference> tocReferences = book.getTableOfContents().getTocReferences();
            logger.info("tocReferences.size(): " + tocReferences.size());
            
            // Iterate chapters
            // TODO
            
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        return storyBook;
    }
}
