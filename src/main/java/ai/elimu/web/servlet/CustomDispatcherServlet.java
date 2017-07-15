package ai.elimu.web.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ai.elimu.util.db.migration.DbMigrationHelper;

public class CustomDispatcherServlet extends DispatcherServlet {

    @Override
    protected WebApplicationContext initWebApplicationContext() {
    	logger.info("initWebApplicationContext");
    	
        WebApplicationContext wac = super.initWebApplicationContext();
        
        // Database migration
        logger.info("Performing database migration...");
        new DbMigrationHelper().performDatabaseMigration(wac);

        return wac;
    }
}
