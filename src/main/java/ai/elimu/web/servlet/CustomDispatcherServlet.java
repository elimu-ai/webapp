package ai.elimu.web.servlet;

import ai.elimu.model.v2.enums.Environment;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.db.DbContentImportHelper;
import ai.elimu.web.ConnectionProviderWeb;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ai.elimu.util.db.DbMigrationHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.io.File;
import java.util.EnumSet;
import javax.persistence.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class CustomDispatcherServlet extends DispatcherServlet {
    
    private final Logger logger = LogManager.getLogger();

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
            Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
            logger.info("language: " + language);
            
            // Import the educational content
            logger.info("Performing database content import...");
            new DbContentImportHelper().performDatabaseContentImport(Environment.TEST, language, webApplicationContext);
            
            createJpaSchemaExport();
        }

        return webApplicationContext;
    }
    
    /**
     * Export the JPA database schema to a file.
     */
    private void createJpaSchemaExport() {
        logger.info("createJpaSchemaExport");

        ConnectionProviderWeb connectionProviderWeb = new ConnectionProviderWeb(
                ConfigHelper.getProperty("jdbc.url"),
                ConfigHelper.getProperty("jdbc.username"),
                ConfigHelper.getProperty("jdbc.password")
        );

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .configure("META-INF/jpa-persistence.xml")
                .applySetting("hibernate.dialect", org.hibernate.dialect.MySQL5Dialect.class.getName())
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting(AvailableSettings.CONNECTION_PROVIDER, connectionProviderWeb)
                .build();

        MetadataSources metadataSources = (MetadataSources) new MetadataSources(serviceRegistry);

        // Scan for classes annotated as JPA @Entity
        ClassPathScanningCandidateComponentProvider entityScanner = new ClassPathScanningCandidateComponentProvider(true);
        entityScanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        for (BeanDefinition beanDefinition : entityScanner.findCandidateComponents(ai.elimu.model.BaseEntity.class.getPackageName())) {
            logger.info("beanDefinition.getBeanClassName(): " + beanDefinition.getBeanClassName());
            try {
                Class<?> annotatedClass = Class.forName(beanDefinition.getBeanClassName());
                logger.info("annotatedClass.getName(): " + annotatedClass.getName());
                metadataSources.addAnnotatedClass(annotatedClass);
            } catch (ClassNotFoundException ex) {
                logger.error(ex);
            }
        }

        Metadata metadata = metadataSources.buildMetadata();
        
        File outputFile = new File("src/main/resources/META-INF/jpa-schema-export.sql");
        if (outputFile.exists()) {
            // Delete existing file content since the SchemaExport appends to existing content.
            logger.info("Deleting " + outputFile.getPath());
            outputFile.delete();
        }

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setOutputFile(outputFile.getPath());
        schemaExport.setDelimiter(";");
        schemaExport.setFormat(true);
        schemaExport.create(EnumSet.of(TargetType.SCRIPT), metadata);
    }
}
