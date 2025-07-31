package ai.elimu.web.servlet;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.dao.NumberAssessmentEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.NumberLearningEventDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.dao.VideoDao;
import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.WordAssessmentEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.entity.analytics.LetterSoundLearningEvent;
import ai.elimu.entity.analytics.NumberAssessmentEvent;
import ai.elimu.entity.analytics.NumberLearningEvent;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.analytics.VideoLearningEvent;
import ai.elimu.entity.analytics.WordAssessmentEvent;
import ai.elimu.entity.analytics.WordLearningEvent;
import ai.elimu.entity.analytics.students.Student;
import ai.elimu.entity.application.Application;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.content.Sound;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.content.multimedia.Video;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.enums.Role;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;
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
import java.util.List;

import jakarta.persistence.Entity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.json.JSONObject;
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
                .applySetting("hibernate.dialect", org.hibernate.dialect.MariaDBDialect.class.getName())
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
        soundM.setUsageCount(9_962);
        soundDao.create(soundM);
        
        Sound soundAA = new Sound();
        soundAA.setValueIpa("aː");
        soundAA.setValueSampa("a:");
        soundAA.setUsageCount(30_315);
        soundDao.create(soundAA);

        Sound soundS = new Sound();
        soundS.setValueIpa("s");
        soundS.setValueSampa("s");
        soundS.setUsageCount(1_526);
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
            image.setDominantColor(dominantColor[0] + "," + dominantColor[1] + "," + dominantColor[2]);
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


        StoryBookParagraphDao storyBookParagraphDao = (StoryBookParagraphDao) webApplicationContext.getBean("storyBookParagraphDao");

        StoryBookParagraph storyBookParagraph1 = new StoryBookParagraph();
        storyBookParagraph1.setStoryBookChapter(storyBookChapter);
        storyBookParagraph1.setSortOrder(0);
        storyBookParagraph1.setOriginalText("ฉัน ชื่อ ซารูน");
        storyBookParagraphDao.create(storyBookParagraph1);

        StoryBookParagraph storyBookParagraph2 = new StoryBookParagraph();
        storyBookParagraph2.setStoryBookChapter(storyBookChapter);
        storyBookParagraph2.setSortOrder(1);
        storyBookParagraph2.setOriginalText("ฉัน มา จาก จังหวัด ตาแก้ว");
        storyBookParagraphDao.create(storyBookParagraph2);


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
        application.setRepoName("sound-cards");
        application.setApplicationStatus(ApplicationStatus.MISSING_APK);
        application.setContributor(contributor);
        applicationDao.create(application);


        StudentDao studentDao = (StudentDao) webApplicationContext.getBean("studentDao");

        Student student1 = new Student();
        student1.setAndroidId("e387e38700000001");
        studentDao.create(student1);

        Student student2 = new Student();
        student2.setAndroidId("e387e38700000002");
        studentDao.create(student2);

        Student student3 = new Student();
        student3.setAndroidId("e387e38700000003");
        studentDao.create(student3);


        // Generate weekly events from 6 months ago until now
        Calendar calendar6MonthsAgo = Calendar.getInstance();
        calendar6MonthsAgo.add(Calendar.MONTH, -6);
        Calendar calendarNow = Calendar.getInstance();
        Calendar week = (Calendar) calendar6MonthsAgo.clone();
        LetterSoundLearningEventDao letterSoundLearningEventDao = (LetterSoundLearningEventDao) webApplicationContext.getBean("letterSoundLearningEventDao");
        WordAssessmentEventDao wordAssessmentEventDao = (WordAssessmentEventDao) webApplicationContext.getBean("wordAssessmentEventDao");
        WordLearningEventDao wordLearningEventDao = (WordLearningEventDao) webApplicationContext.getBean("wordLearningEventDao");
        NumberAssessmentEventDao numberAssessmentEventDao = (NumberAssessmentEventDao) webApplicationContext.getBean("numberAssessmentEventDao");
        NumberLearningEventDao numberLearningEventDao = (NumberLearningEventDao) webApplicationContext.getBean("numberLearningEventDao");
        StoryBookLearningEventDao storyBookLearningEventDao = (StoryBookLearningEventDao) webApplicationContext.getBean("storyBookLearningEventDao");
        VideoLearningEventDao videoLearningEventDao = (VideoLearningEventDao) webApplicationContext.getBean("videoLearningEventDao");
        int weekCount = 1;
        while (!week.after(calendarNow)) {
            List<Student> students = studentDao.readAll();
            for (Student student : students) {
                int randomNumberOfLetterSoundLearningEvents = (int) (Math.random() * 10);
                for (int i = 0; i < randomNumberOfLetterSoundLearningEvents; i++) {
                    LetterSoundLearningEvent letterSoundLearningEvent = new LetterSoundLearningEvent();
                    Calendar randomWeekday = (Calendar) week.clone();
                    randomWeekday.add(Calendar.HOUR_OF_DAY, -(int) (Math.random() * 24 * 7));
                    letterSoundLearningEvent.setTimestamp(randomWeekday);
                    letterSoundLearningEvent.setAndroidId(student.getAndroidId());
                    letterSoundLearningEvent.setPackageName("ai.elimu.herufi");
                    if (weekCount > 26/2) {
                        letterSoundLearningEvent.setResearchExperiment(ResearchExperiment.EXP_0_WORD_EMOJIS);
                        letterSoundLearningEvent.setExperimentGroup(ExperimentGroup.values()[(int) (Math.random() * 2)]);
                    }
                    letterSoundLearningEvent.setLetterSoundLetters(letterSoundM.getLetters().stream().map(Letter::getText).toList());
                    letterSoundLearningEvent.setLetterSoundSounds(letterSoundM.getSounds().stream().map(Sound::getValueIpa).toList());
                    letterSoundLearningEvent.setLetterSoundId(letterSoundM.getId());
                    letterSoundLearningEventDao.create(letterSoundLearningEvent);
                }

                int randomNumberOfWordAssessmentEvents = (int) (Math.random() * 10);
                for (int i = 0; i < randomNumberOfWordAssessmentEvents; i++) {
                    WordAssessmentEvent wordAssessmentEvent = new WordAssessmentEvent();
                    wordAssessmentEvent.setTimestamp(week);
                    wordAssessmentEvent.setAndroidId(student.getAndroidId());
                    wordAssessmentEvent.setPackageName("ai.elimu.kukariri");
                    wordAssessmentEvent.setMasteryScore((float) (int) (Math.random() * 2));
                    wordAssessmentEvent.setTimeSpentMs((long) (Math.random() * 20_000));
                    if (weekCount > 26/2) {
                        wordAssessmentEvent.setResearchExperiment(ResearchExperiment.EXP_0_WORD_EMOJIS);
                        wordAssessmentEvent.setExperimentGroup(ExperimentGroup.values()[(int) (Math.random() * 2)]);
                    }
                    wordAssessmentEvent.setWordText(wordMAA.getText());
                    wordAssessmentEvent.setWordId(wordMAA.getId());
                    wordAssessmentEventDao.create(wordAssessmentEvent);
                }

                int randomNumberOfWordLearningEvents = (int) (Math.random() * 10);
                for (int i = 0; i < randomNumberOfWordLearningEvents; i++) {
                    WordLearningEvent wordLearningEvent = new WordLearningEvent();
                    wordLearningEvent.setTimestamp(week);
                    wordLearningEvent.setAndroidId(student.getAndroidId());
                    wordLearningEvent.setPackageName("ai.elimu.maneno");
                    if (weekCount > 26/2) {
                        wordLearningEvent.setResearchExperiment(ResearchExperiment.EXP_0_WORD_EMOJIS);
                        wordLearningEvent.setExperimentGroup(ExperimentGroup.values()[(int) (Math.random() * 2)]);
                    }
                    if (Math.random() > 0.5) {
                        wordLearningEvent.setWordText(wordMAA.getText());
                        wordLearningEvent.setWordId(wordMAA.getId());
                    } else {
                        wordLearningEvent.setWordText(wordSAAM.getText());
                        wordLearningEvent.setWordId(wordSAAM.getId());
                    }
                    wordLearningEventDao.create(wordLearningEvent);
                }

                int randomNumberOfNumberAssessmentEvents = (int) (Math.random() * 10);
                for (int i = 0; i < randomNumberOfNumberAssessmentEvents; i++) {
                    NumberAssessmentEvent numberAssessmentEvent = new NumberAssessmentEvent();
                    numberAssessmentEvent.setTimestamp(week);
                    numberAssessmentEvent.setAndroidId(student.getAndroidId());
                    numberAssessmentEvent.setPackageName("ai.elimu.learndigits");
                    numberAssessmentEvent.setMasteryScore((float) (int) (Math.random() * 2));
                    numberAssessmentEvent.setTimeSpentMs((long) (Math.random() * 10_000));
                    if (weekCount > 26/2) {
                        numberAssessmentEvent.setResearchExperiment(ResearchExperiment.EXP_0_WORD_EMOJIS);
                        numberAssessmentEvent.setExperimentGroup(ExperimentGroup.values()[(int) (Math.random() * 2)]);
                    }
                    numberAssessmentEvent.setNumberValue(number3.getValue());
                    numberAssessmentEvent.setNumberId(number3.getId());
                    numberAssessmentEventDao.create(numberAssessmentEvent);
                }

                int randomNumberOfNumberLearningEvents = (int) (Math.random() * 10);
                for (int i = 0; i < randomNumberOfNumberLearningEvents; i++) {
                    NumberLearningEvent numberLearningEvent = new NumberLearningEvent();
                    numberLearningEvent.setTimestamp(week);
                    numberLearningEvent.setAndroidId(student.getAndroidId());
                    numberLearningEvent.setPackageName("ai.elimu.calculator");
                    if (weekCount > 26/2) {
                        numberLearningEvent.setResearchExperiment(ResearchExperiment.EXP_0_WORD_EMOJIS);
                        numberLearningEvent.setExperimentGroup(ExperimentGroup.values()[(int) (Math.random() * 2)]);
                    }
                    numberLearningEvent.setNumberValue(number3.getValue());
                    numberLearningEvent.setNumberSymbol(number3.getSymbol());
                    numberLearningEvent.setNumberId(number3.getId());
                    numberLearningEventDao.create(numberLearningEvent);
                }

                int randomNumberOfStoryBookLearningEvents = (int) (Math.random() * 10);
                for (int i = 0; i < randomNumberOfStoryBookLearningEvents; i++) {
                    StoryBookLearningEvent storyBookLearningEvent = new StoryBookLearningEvent();
                    storyBookLearningEvent.setTimestamp(week);
                    storyBookLearningEvent.setAndroidId(student.getAndroidId());
                    storyBookLearningEvent.setPackageName("ai.elimu.vitabu");
                    if (weekCount > 26/2) {
                        storyBookLearningEvent.setResearchExperiment(ResearchExperiment.EXP_0_WORD_EMOJIS);
                        storyBookLearningEvent.setExperimentGroup(ExperimentGroup.values()[(int) (Math.random() * 2)]);
                    }
                    JSONObject additionalData = new JSONObject();
                    if (storyBookLearningEvent.getExperimentGroup() == ExperimentGroup.CONTROL) {
                        additionalData.put("seconds_spent_per_chapter", "[" + (int) (Math.pow(0.98, weekCount) * Math.random() * 30) + ", " + (int) (Math.pow(0.98, weekCount) * Math.random() * 60) + "]");
                    } else {
                        additionalData.put("seconds_spent_per_chapter", "[" + (int) (Math.pow(0.96, weekCount) * Math.random() * 30) + ", " + (int) (Math.pow(0.96, weekCount) * Math.random() * 60) + "]");
                    }
                    storyBookLearningEvent.setAdditionalData(additionalData.toString());
                    storyBookLearningEvent.setStoryBookTitle(storyBook.getTitle());
                    storyBookLearningEvent.setStoryBookId(storyBook.getId());
                    storyBookLearningEvent.setStoryBook(storyBook);
                    storyBookLearningEventDao.create(storyBookLearningEvent);
                }

                int randomNumberOfVideoLearningEvents = (int) (Math.random() * 10);
                for (int i = 0; i < randomNumberOfVideoLearningEvents; i++) {
                    VideoLearningEvent videoLearningEvent = new VideoLearningEvent();
                    videoLearningEvent.setTimestamp(week);
                    videoLearningEvent.setAndroidId(student.getAndroidId());
                    videoLearningEvent.setPackageName("ai.elimu.filamu");
                    if (weekCount > 26/2) {
                        videoLearningEvent.setResearchExperiment(ResearchExperiment.EXP_0_WORD_EMOJIS);
                        videoLearningEvent.setExperimentGroup(ExperimentGroup.values()[(int) (Math.random() * 2)]);
                    }
                    videoLearningEvent.setVideoTitle(video.getTitle());
                    videoLearningEvent.setVideoId(video.getId());
                    videoLearningEventDao.create(videoLearningEvent);
                }
            }
            week.add(Calendar.WEEK_OF_YEAR, 1);
            weekCount++;
        }
    }
}
