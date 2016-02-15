package org.literacyapp.web.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import org.literacyapp.model.enums.Environment;
import org.literacyapp.util.ConfigHelper;
import org.literacyapp.util.db.migration.DbMigrationHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;

public class LiteracyAppDispatcherServlet extends DispatcherServlet {

    @Override
    protected WebApplicationContext initWebApplicationContext() {
    	logger.info("initWebApplicationContext");
    	
        WebApplicationContext wac = super.initWebApplicationContext();
        
//        if ((EnvironmentContextLoaderListener.env != Environment.DEV) && !ConfigHelper.getProperty("jpa.database").equals("HSQL")) {
            // Database migration
            logger.info("Performing database migration...");
            new DbMigrationHelper().performDatabaseMigration(wac);
//        }

        return wac;
    }
}
