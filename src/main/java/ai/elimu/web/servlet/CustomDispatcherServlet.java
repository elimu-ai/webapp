package ai.elimu.web.servlet;

import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.db.DbContentImportHelper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ai.elimu.util.db.DbMigrationHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

public class CustomDispatcherServlet extends DispatcherServlet {

    @Override
    protected WebApplicationContext initWebApplicationContext() {
    	logger.info("initWebApplicationContext");
    	
        WebApplicationContext webApplicationContext = super.initWebApplicationContext();
        
        // Database migration
        logger.info("Performing database migration...");
        new DbMigrationHelper().performDatabaseMigration(webApplicationContext);
        
        if (EnvironmentContextLoaderListener.env == Environment.DEV) {
            // To ease development, pre-populate database with educational content extracted from the test server
            
            // Lookup the language of the educational content from the config file
            Language language = Language.valueOf(ConfigHelper.getProperty("content.language").toUpperCase());
            logger.info("language: " + language);
            
            // Import the educational content
            new DbContentImportHelper().performDatabaseContentImport(Environment.TEST, language, webApplicationContext);
        }

        return webApplicationContext;
    }
}
