package ai.elimu.util.db.migration;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import ai.elimu.dao.DbMigrationDao;
import ai.elimu.model.DbMigration;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.VersionHelper;
import org.springframework.web.context.WebApplicationContext;

public class DbMigrationHelper {
    
    private Logger logger = Logger.getLogger(getClass());

    private DbMigrationDao dbMigrationDao;
    
    public void performDatabaseMigration(WebApplicationContext wac) {
        logger.info("performDatabaseMigration");
        
        dbMigrationDao = (DbMigrationDao) wac.getBean("dbMigrationDao");
        
        String pomVersion = ConfigHelper.getProperty("application.version");
        logger.info("pomVersion: " + pomVersion);
        
        Integer pomVersionAsInteger = VersionHelper.getPomVersionAsInteger(pomVersion);
        logger.info("pomVersionAsInteger: " + pomVersionAsInteger);
        
        List<DbMigration> dbMigrations = dbMigrationDao.readAllOrderedByVersionDesc();
        logger.info("dbMigrations.size(): " + dbMigrations.size());
        if (dbMigrations.isEmpty()) {
            logger.info("Fresh database (no migration necessary)");
            
            // Store current version
            logger.info("Storing current version (" + pomVersionAsInteger + ")");
            DbMigration dbMigration = new DbMigration();
            dbMigration.setVersion(pomVersionAsInteger);
            dbMigration.setScript("-");
            dbMigration.setCalendar(Calendar.getInstance());
            dbMigrationDao.create(dbMigration);
        } else {
            // Perform missing DB migrations up until the current version
            
            DbMigration dbMigrationMostRecent = dbMigrations.get(0);
            Integer versionOfMostRecentMigration = dbMigrationMostRecent.getVersion();
            logger.info("versionOfMostRecentMigration: " + versionOfMostRecentMigration);
            
            if (versionOfMostRecentMigration < pomVersionAsInteger) {
                logger.info("Looking up pending DB migrations after version " + versionOfMostRecentMigration);
                
                // Look up SQL scripts from src/main/resources/db/migration
                for (int scriptVersion = (versionOfMostRecentMigration + 1); scriptVersion <= pomVersionAsInteger; scriptVersion++) {
                    String filePath = "db/migration/" + scriptVersion + ".sql";
                    logger.info("Looking up file \"" + filePath + "\"...");
                    URL url = getClass().getClassLoader().getResource(filePath);
                    if ((url != null) && StringUtils.isNotBlank(url.getFile())) {
                        logger.info("Migration script found for version " + scriptVersion);
                        
                        File sqlFile = new File(url.getFile());
                        
                        String script = "";
                        
                        try {
                            Scanner scanner = new Scanner(sqlFile);
                            while (scanner.hasNextLine()) {
                                String sql = scanner.nextLine();
                                if (StringUtils.isBlank(sql) || sql.startsWith("#")) {
                                    continue;
                                }
                                logger.info("Executing sql: " + sql);
                                dbMigrationDao.executeMigration(sql);
                                
                                script += sql + "\n";
                            }
                            scanner.close();
                        } catch (FileNotFoundException ex) {
                            logger.error(null, ex);
                        }
                        
                        DbMigration dbMigration = new DbMigration();
                        dbMigration.setVersion(scriptVersion);
                        dbMigration.setScript(script);
                        dbMigration.setCalendar(Calendar.getInstance());
                        dbMigrationDao.create(dbMigration);
                    }
                }
            }
        }
    }
}
