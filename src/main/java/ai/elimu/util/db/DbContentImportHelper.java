package ai.elimu.util.db;

import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Language;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

public class DbContentImportHelper {
    
    private Logger logger = Logger.getLogger(getClass());
    
    /**
     * Extracts educational content from the CSV files in {@code src/main/resources/db/content_TEST/<Language>/} and 
     * stores it in the database.
     * 
     * @param environment The environment from which to import the database content.
     * @param language The language to use during the import.
     * @param wac Context needed to access DAOs.
     */
    public synchronized void performDatabaseContentImport(Environment environment, Language language, WebApplicationContext wac) {
        logger.info("performDatabaseContentImport");
        
        if (!((environment == Environment.TEST) || (environment == Environment.PROD))) {
            throw new IllegalArgumentException("Database content can only be imported from the TEST environment or from the PROD environment");
        }
        
        // Extract and import Allophones
        // TODO
        
        // Extract and import Letters
        // TODO
        
        // Extract and import Words
        // TODO
        
        // Extract and import Numbers
        // TODO
        
        // Extract and import Images
        // TODO
        
        // Extract and import StoryBooks
        // TODO
    }
}
