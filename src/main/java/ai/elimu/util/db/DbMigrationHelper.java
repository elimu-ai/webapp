package ai.elimu.util.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.DbMigrationDao;
import ai.elimu.entity.DbMigration;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.VersionHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
public class DbMigrationHelper {

    private DbMigrationDao dbMigrationDao;
    
    public synchronized void performDatabaseMigration(WebApplicationContext webApplicationContext) {
        log.info("performDatabaseMigration");
        
        dbMigrationDao = (DbMigrationDao) webApplicationContext.getBean("dbMigrationDao");
        
        String pomVersion = ConfigHelper.getProperty("pom.version");
        log.info("pomVersion: " + pomVersion);
        
        Integer pomVersionAsInteger = VersionHelper.getPomVersionAsInteger(pomVersion);
        log.info("pomVersionAsInteger: " + pomVersionAsInteger);
        
        List<DbMigration> dbMigrations = dbMigrationDao.readAllOrderedByVersionDesc();
        log.info("dbMigrations.size(): " + dbMigrations.size());
        if (dbMigrations.isEmpty()) {
            log.info("Fresh database (no migration necessary)");
            
            // Store current version
            log.info("Storing current version (" + pomVersionAsInteger + ")");
            DbMigration dbMigration = new DbMigration();
            dbMigration.setVersion(pomVersionAsInteger);
            dbMigration.setScript("-");
            dbMigration.setCalendar(Calendar.getInstance());
            dbMigrationDao.create(dbMigration);
        } else {
            // Perform missing DB migrations up until the current version
            
            DbMigration dbMigrationMostRecent = dbMigrations.get(0);
            Integer versionOfMostRecentMigration = dbMigrationMostRecent.getVersion();
            log.info("versionOfMostRecentMigration: " + versionOfMostRecentMigration);
            
            if (versionOfMostRecentMigration < pomVersionAsInteger) {
                log.info("Looking up pending DB migrations after version " + versionOfMostRecentMigration);
                
                // Look up SQL scripts from src/main/resources/db/
                for (int scriptVersion = (versionOfMostRecentMigration + 1); scriptVersion <= pomVersionAsInteger; scriptVersion++) {
                    String filePath = "db/migration/" + scriptVersion + ".sql";
                    log.info("Looking up file \"" + filePath + "\"...");
                    URL url = getClass().getClassLoader().getResource(filePath);
                    if ((url != null) && StringUtils.isNotBlank(url.getFile())) {
                        log.info("Migration script found for version " + scriptVersion);
                        
                        File sqlFile = new File(url.getFile());
                        
                        String script = "";
                        
                        try {
                            Scanner scanner = new Scanner(sqlFile);
                            while (scanner.hasNextLine()) {
                                String sql = scanner.nextLine();
                                if (StringUtils.isBlank(sql) || sql.startsWith("#")) {
                                    continue;
                                }
                                log.info("Executing sql: " + sql);
                                dbMigrationDao.executeMigration(sql);
                                
                                script += sql + "\n";
                            }
                            scanner.close();
                        } catch (FileNotFoundException ex) {
                            log.error(ex.getMessage());
                        }
                        
                        // Update current version
                        log.info("Updating current version (" + scriptVersion + ")");
                        DbMigration dbMigration = new DbMigration();
                        dbMigration.setVersion(scriptVersion);
                        dbMigration.setScript(script);
                        dbMigration.setCalendar(Calendar.getInstance());
                        dbMigrationDao.create(dbMigration);
                        
                        log.info("Database migration complete!");
                    }
                }
            }
        }
    }
}
