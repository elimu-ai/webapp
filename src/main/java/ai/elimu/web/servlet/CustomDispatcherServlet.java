package ai.elimu.web.servlet;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.VideoDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.application.Application;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.content.Sound;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.content.multimedia.Video;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.enums.Role;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.model.v2.enums.content.VideoFormat;
import ai.elimu.util.ChecksumHelper;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.ImageColorHelper;
import ai.elimu.web.ConnectionProviderWeb;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ai.elimu.util.db.DbMigrationHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.HashSet;

import jakarta.persistence.Entity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@Slf4j
public class CustomDispatcherServlet extends DispatcherServlet {
    
    @Override
    protected WebApplicationContext initWebApplicationContext() {
        log.info("initWebApplicationContext");
        
        WebApplicationContext webApplicationContext = super.initWebApplicationContext();

        // Database migration
        log.info("Performing database migration...");
        new DbMigrationHelper().performDatabaseMigration(webApplicationContext);
        
        if (EnvironmentContextLoaderListener.env == Environment.DEV) {
            createJpaSchemaExport();
            populateDatabase(webApplicationContext);
        }

        return webApplicationContext;
    }
    
    /**
     * Export the JPA database schema to a file.
     */
    private void createJpaSchemaExport() {
        log.info("createJpaSchemaExport");

        ConnectionProviderWeb connectionProviderWeb = new ConnectionProviderWeb(
                ConfigHelper.getProperty("jdbc.url"),
                ConfigHelper.getProperty("jdbc.username"),
                ConfigHelper.getProperty("jdbc.password")
        );

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .configure("META-INF/jpa-persistence.xml")
                .applySetting("hibernate.dialect", org.hibernate.dialect.MySQLDialect.class.getName())
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting(AvailableSettings.CONNECTION_PROVIDER, connectionProviderWeb)
                .build();

        MetadataSources metadataSources = (MetadataSources) new MetadataSources(serviceRegistry);

        // Scan for classes annotated as JPA @Entity
        ClassPathScanningCandidateComponentProvider entityScanner = new ClassPathScanningCandidateComponentProvider(true);
        entityScanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        for (BeanDefinition beanDefinition : entityScanner.findCandidateComponents(ai.elimu.entity.BaseEntity.class.getPackageName())) {
            log.debug("beanDefinition.getBeanClassName(): " + beanDefinition.getBeanClassName());
            try {
                Class<?> annotatedClass = Class.forName(beanDefinition.getBeanClassName());
                log.debug("annotatedClass.getName(): " + annotatedClass.getName());
                metadataSources.addAnnotatedClass(annotatedClass);
            } catch (ClassNotFoundException ex) {
                log.error(ex.getMessage());
            }
        }

        Metadata metadata = metadataSources.buildMetadata();
        
        File outputFile = new File("src/main/resources/META-INF/jpa-schema-export.sql");
        if (outputFile.exists()) {
            // Delete existing file content since the SchemaExport appends to existing content.
            log.info("Deleting " + outputFile.getPath());
            outputFile.delete();
        }

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setOutputFile(outputFile.getPath());
        schemaExport.setDelimiter(";");
        schemaExport.setFormat(true);
        schemaExport.create(EnumSet.of(TargetType.SCRIPT), metadata);
    }

    /**
     * Populate the database with dummy content so that the regression tests will pass.
     */
    private void populateDatabase(WebApplicationContext webApplicationContext) {
        log.info("populateDatabase");


        ContributorDao contributorDao = (ContributorDao) webApplicationContext.getBean("contributorDao");

        Contributor contributor = new Contributor();
        contributor.setEmail("dev@elimu.ai");
        contributor.setRegistrationTime(Calendar.getInstance());
        contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
        contributorDao.create(contributor);

        
        LetterDao letterDao = (LetterDao) webApplicationContext.getBean("letterDao");
        
        Letter letterM = new Letter();
        letterM.setText("ม");
        letterDao.create(letterM);
        
        Letter letterAA = new Letter();
        letterAA.setText("า");
        letterDao.create(letterAA);

        Letter letterS = new Letter();
        letterS.setText("ส");
        letterDao.create(letterS);

        
        SoundDao soundDao = (SoundDao) webApplicationContext.getBean("soundDao");
        
        Sound soundM = new Sound();
        soundM.setValueIpa("m");
        soundM.setValueSampa("m");
        soundDao.create(soundM);
        
        Sound soundAA = new Sound();
        soundAA.setValueIpa("aː");
        soundAA.setValueSampa("a:");
        soundDao.create(soundAA);

        Sound soundS = new Sound();
        soundS.setValueIpa("s");
        soundS.setValueSampa("s");
        soundDao.create(soundS);

        
        LetterSoundDao letterSoundDao = (LetterSoundDao) webApplicationContext.getBean("letterSoundDao");
        
        LetterSound letterSoundM = new LetterSound();
        letterSoundM.setLetters(Arrays.asList(letterM));
        letterSoundM.setSounds(Arrays.asList(soundM));
        letterSoundDao.create(letterSoundM);

        LetterSound letterSoundAA = new LetterSound();
        letterSoundAA.setLetters(Arrays.asList(letterAA));
        letterSoundAA.setSounds(Arrays.asList(soundAA));
        letterSoundDao.create(letterSoundAA);

        LetterSound letterSoundS = new LetterSound();
        letterSoundS.setLetters(Arrays.asList(letterS));
        letterSoundS.setSounds(Arrays.asList(soundS));
        letterSoundDao.create(letterSoundS);

        
        WordDao wordDao = (WordDao) webApplicationContext.getBean("wordDao");

        Word wordMAA = new Word();
        wordMAA.setText("มา");
        wordMAA.setLetterSounds(Arrays.asList(letterSoundM, letterSoundAA));
        wordDao.create(wordMAA);

        Word wordSAAM = new Word();
        wordSAAM.setText("สาม");
        wordSAAM.setLetterSounds(Arrays.asList(letterSoundS, letterSoundAA, letterSoundM));
        wordDao.create(wordSAAM);


        NumberDao numberDao = (NumberDao) webApplicationContext.getBean("numberDao");
        
        Number number3 = new Number();
        number3.setValue(3);
        number3.setWords(Arrays.asList(wordSAAM));
        numberDao.create(number3);

        
        EmojiDao emojiDao = (EmojiDao) webApplicationContext.getBean("emojiDao");
        
        Emoji emoji = new Emoji();
        emoji.setGlyph("⌚");
        emoji.setUnicodeVersion(1.1);
        emoji.setUnicodeEmojiVersion(1.0);
        emojiDao.create(emoji);


        ImageDao imageDao = (ImageDao) webApplicationContext.getBean("imageDao");

        Image image = new Image();
        image.setTitle("placeholder");
        try {
            ResourceLoader resourceLoader = new ClassRelativeResourceLoader(this.getClass());
            Resource resource = resourceLoader.getResource("placeholder.png");
            byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
            image.setFileSize(bytes.length);
            image.setChecksumMd5(ChecksumHelper.calculateMD5(bytes));
            int[] dominantColor = ImageColorHelper.getDominantColor(bytes);
            image.setDominantColor("rgb(" + dominantColor[0] + "," + dominantColor[1] + "," + dominantColor[2] + ")");
        } catch (IOException e) {
            logger.error(null, e);
        }
        image.setImageFormat(ImageFormat.PNG);
        image.setContentType("image/png");
        imageDao.create(image);


        StoryBookDao storyBookDao = (StoryBookDao) webApplicationContext.getBean("storyBookDao");

        StoryBook storyBook = new StoryBook();
        storyBook.setTitle("ไปเที่ยวเสียมราฐกันเถอะ");
        storyBookDao.create(storyBook);


        StoryBookChapterDao storyBookChapterDao = (StoryBookChapterDao) webApplicationContext.getBean("storyBookChapterDao");

        StoryBookChapter storyBookChapter = new StoryBookChapter();
        storyBookChapter.setStoryBook(storyBook);
        storyBookChapter.setSortOrder(0);
        storyBookChapterDao.create(storyBookChapter);


        VideoDao videoDao = (VideoDao) webApplicationContext.getBean("videoDao");

        Video video = new Video();
        video.setTitle("placeholder");
        try {
            ResourceLoader resourceLoader = new ClassRelativeResourceLoader(this.getClass());
            Resource resource = resourceLoader.getResource("placeholder.mp4");
            byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
            video.setFileSize(bytes.length);
            video.setChecksumMd5(ChecksumHelper.calculateMD5(bytes));
            video.setChecksumGitHub("1331d7c476649f4449ec7c6663fc107ce2b4d88b");
            video.setThumbnail(Files.readAllBytes(new ClassRelativeResourceLoader(this.getClass()).getResource("placeholder.png").getFile().toPath()));
        } catch (IOException e) {
            logger.error(null, e);
        }
        video.setVideoFormat(VideoFormat.MP4);
        video.setContentType("video/mp4");
        videoDao.create(video);


        ApplicationDao applicationDao = (ApplicationDao) webApplicationContext.getBean("applicationDao");

        Application application = new Application();
        application.setPackageName("ai.elimu.soundcards");
        application.setRepoName("soundcards");
        application.setApplicationStatus(ApplicationStatus.MISSING_APK);
        application.setContributor(contributor);
        applicationDao.create(application);
    }
}
