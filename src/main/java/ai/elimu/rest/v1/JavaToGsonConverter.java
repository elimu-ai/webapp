package ai.elimu.rest.v1;

import java.util.ArrayList;
import java.util.List;
import ai.elimu.model.admin.Application;
import ai.elimu.model.Device;
import ai.elimu.model.Student;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.analytics.LetterLearningEvent;
import ai.elimu.model.analytics.NumberLearningEvent;
import ai.elimu.model.analytics.VideoLearningEvent;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.gson.DeviceGson;
import ai.elimu.model.gson.StudentGson;
import ai.elimu.model.gson.content.NumberGson;
import ai.elimu.model.gson.content.WordGson;
import ai.elimu.model.gson.admin.ApplicationGson;
import ai.elimu.model.gson.admin.ApplicationVersionGson;
import ai.elimu.model.gson.analytics.LetterLearningEventGson;
import ai.elimu.model.gson.analytics.NumberLearningEventGson;
import ai.elimu.model.gson.analytics.VideoLearningEventGson;
import ai.elimu.model.gson.content.AllophoneGson;
import ai.elimu.model.gson.content.LetterGson;
import ai.elimu.model.gson.content.StoryBookGson;
import ai.elimu.model.gson.content.SyllableGson;
import ai.elimu.model.gson.content.multimedia.AudioGson;
import ai.elimu.model.gson.content.multimedia.ImageGson;
import ai.elimu.model.gson.content.multimedia.VideoGson;
import ai.elimu.model.gson.project.AppCategoryGson;
import ai.elimu.model.gson.project.AppCollectionGson;
import ai.elimu.model.gson.project.AppGroupGson;
import ai.elimu.model.project.AppCategory;
import ai.elimu.model.project.AppCollection;
import ai.elimu.model.project.AppGroup;

/**
 * Convert classes from JPA/Hibernate format to POJO format, so that they can be serialized into 
 * JSON and transferred to Android applications that are connecting via the REST API.
 */
public class JavaToGsonConverter {
    
    public static AudioGson getAudioGson(Audio audio) {
        if (audio == null) {
            return null;
        } else {
            AudioGson audioGson = new AudioGson();
            
            audioGson.setId(audio.getId());
            audioGson.setLocale(audio.getLocale());
            audioGson.setTimeLastUpdate(audio.getTimeLastUpdate());
            audioGson.setRevisionNumber(audio.getRevisionNumber());
            audioGson.setContentStatus(audio.getContentStatus());
            
            audioGson.setDownloadUrl("/audio/" + audio.getId() + "." + audio.getAudioFormat().toString().toLowerCase());
            audioGson.setDownloadSize(audio.getBytes().length / 1024);
            audioGson.setContentType(audio.getContentType());
            audioGson.setLiteracySkills(audio.getLiteracySkills());
            audioGson.setNumeracySkills(audio.getNumeracySkills());
            
            List<LetterGson> letters = new ArrayList<>();
            for (Letter letter : audio.getLetters()) {
                LetterGson letterGson = getLetterGson(letter);
                letters.add(letterGson);
            }
            audioGson.setLetters(letters);
            
            List<NumberGson> numbers = new ArrayList<>();
            for (Number number : audio.getNumbers()) {
                NumberGson numberGson = getNumberGson(number);
                numbers.add(numberGson);
            }
            audioGson.setNumbers(numbers);
            
            List<WordGson> words = new ArrayList<>();
            for (Word word : audio.getWords()) {
                WordGson wordGson = getWordGson(word);
                words.add(wordGson);
            }
            audioGson.setWords(words);
            
            audioGson.setTranscription(audio.getTranscription());
            audioGson.setAudioType(audio.getAudioFormat());
            
            return audioGson;
        }
    }
    
    public static AllophoneGson getAllophoneGson(Allophone allophone) {
        if (allophone == null) {
            return null;
        } else {
            AllophoneGson allophoneGson = new AllophoneGson();
            
            allophoneGson.setId(allophone.getId());
            allophoneGson.setLocale(allophone.getLocale());
            allophoneGson.setTimeLastUpdate(allophone.getTimeLastUpdate());
            allophoneGson.setRevisionNumber(allophone.getRevisionNumber());
            allophoneGson.setContentStatus(allophone.getContentStatus());
            
            allophoneGson.setValueIpa(allophone.getValueIpa());
            allophoneGson.setValueSampa(allophone.getValueSampa());
            allophoneGson.setAudio(getAudioGson(allophone.getAudio()));
            allophoneGson.setDiacritic(allophone.isDiacritic());
            allophoneGson.setSoundType(allophone.getSoundType());
            allophoneGson.setUsageCount(allophone.getUsageCount());
            
            return allophoneGson;
        }
    }
    
    public static AppCollectionGson getAppCollectionGson(AppCollection appCollection) {
        if (appCollection == null) {
            return null;
        } else {
            AppCollectionGson appCollectionGson = new AppCollectionGson();
            appCollectionGson.setId(appCollection.getId());
            List<AppCategoryGson> appCategories = new ArrayList<>();
            for (AppCategory appCategory : appCollection.getAppCategories()) {
                AppCategoryGson appCategoryGson = getAppCategoryGson(appCategory);
                appCategories.add(appCategoryGson);
            }
            appCollectionGson.setAppCategories(appCategories);
            return appCollectionGson;
        }
    }
    
    public static AppCategoryGson getAppCategoryGson(AppCategory appCategory) {
        if (appCategory == null) {
            return null;
        } else {
            AppCategoryGson appCategoryGson = new AppCategoryGson();
            appCategoryGson.setId(appCategory.getId());
            appCategoryGson.setName(appCategory.getName());
            List<AppGroupGson> appGroups = new ArrayList<>();
            for (AppGroup appGroup : appCategory.getAppGroups()) {
                AppGroupGson appGroupGson = getAppGroupGson(appGroup);
                appGroups.add(appGroupGson);
            }
            appCategoryGson.setAppGroups(appGroups);
            return appCategoryGson;
        }
    }
    
    public static AppGroupGson getAppGroupGson(AppGroup appGroup) {
        if (appGroup == null) {
            return null;
        } else {
            AppGroupGson appGroupGson = new AppGroupGson();
            appGroupGson.setId(appGroup.getId());
            List<ApplicationGson> applications = new ArrayList<>();
            for (Application application : appGroup.getApplications()) {
                ApplicationGson applicationGson = getApplicationGson(application);
                applications.add(applicationGson);
            }
            appGroupGson.setApplications(applications);
            return appGroupGson;
        }
    }
    
    public static ApplicationGson getApplicationGson(Application application) {
        if (application == null) {
            return null;
        } else {
            ApplicationGson applicationGson = new ApplicationGson();
            applicationGson.setId(application.getId());
            applicationGson.setLocale(application.getLocale());
            applicationGson.setPackageName(application.getPackageName());
            applicationGson.setLiteracySkills(application.getLiteracySkills());
            applicationGson.setNumeracySkills(application.getNumeracySkills());
            applicationGson.setApplicationStatus(application.getApplicationStatus());
            return applicationGson;
        }
    }
    
    public static ApplicationVersionGson getApplicationVersionGson(ApplicationVersion applicationVersion) {
        if (applicationVersion == null) {
            return null;
        } else {
            ApplicationVersionGson applicationVersionGson = new ApplicationVersionGson();
            applicationVersionGson.setId(applicationVersion.getId());
            applicationVersionGson.setApplication(getApplicationGson(applicationVersion.getApplication()));
            applicationVersionGson.setFileSizeInKb(applicationVersion.getFileSizeInKb());
            applicationVersionGson.setFileUrl("/apk/" + applicationVersion.getApplication().getPackageName() + "-" + applicationVersion.getVersionCode() + ".apk");
            applicationVersionGson.setChecksumMd5(applicationVersion.getChecksumMd5());
            applicationVersionGson.setContentType(applicationVersion.getContentType());
            applicationVersionGson.setVersionCode(applicationVersion.getVersionCode());
            applicationVersionGson.setStartCommand(applicationVersion.getStartCommand());
            applicationVersionGson.setTimeUploaded(applicationVersion.getTimeUploaded());
            return applicationVersionGson;
        }
    }
    
    public static DeviceGson getDeviceGson(Device device) {
        if (device == null) {
            return null;
        } else {
            DeviceGson deviceGson = new DeviceGson();
            
            deviceGson.setId(device.getId());
            deviceGson.setDeviceId(device.getDeviceId());
//            deviceGson.setDeviceModel(device.getDeviceModel());
//            deviceGson.setTimeRegistered(device.getTimeRegistered());
//            deviceGson.setOsVersion(device.getOsVersion());
//            deviceGson.setLocale(device.getLocale());
//            
//            Set<DeviceGson> devicesNearby = new HashSet<DeviceGson>();
//            for (Device deviceNearby : device.getDevicesNearby()) {
//                DeviceGson deviceJsonNearby = getDeviceGson(deviceNearby);
//                if (deviceJsonNearby != null) {
//                    devicesNearby.add(deviceJsonNearby);
//                }
//            }
//            if (!devicesNearby.isEmpty()) {
//                deviceGson.setDevicesNearby(devicesNearby);
//            }
            
            return deviceGson;
        }
    }
    
    public static StudentGson getStudentGson(Student student) {
        if (student == null) {
            return null;
        } else {
            StudentGson studentGson = new StudentGson();
            
            studentGson.setUniqueId(student.getUniqueId());
            studentGson.setTimeCreated(student.getTimeCreated());
//            studentGson.setAvatar(null);
//            studentGson.setDevices(null);
            
            return studentGson;
        }
    }
    
    public static ImageGson getImageGson(Image image) {
        if (image == null) {
            return null;
        } else {
            ImageGson imageGson = new ImageGson();
            
            imageGson.setId(image.getId());
            imageGson.setLocale(image.getLocale());
            imageGson.setTimeLastUpdate(image.getTimeLastUpdate());
            imageGson.setRevisionNumber(image.getRevisionNumber());
            imageGson.setContentStatus(image.getContentStatus());
            
            imageGson.setDownloadUrl("/image/" + image.getId() + "." + image.getImageFormat().toString().toLowerCase());
            imageGson.setDownloadSize(image.getBytes().length / 1024);
            imageGson.setContentType(image.getContentType());
            imageGson.setLiteracySkills(image.getLiteracySkills());
            imageGson.setNumeracySkills(image.getNumeracySkills());
            
            List<LetterGson> letters = new ArrayList<>();
            for (Letter letter : image.getLetters()) {
                LetterGson letterGson = getLetterGson(letter);
                letters.add(letterGson);
            }
            imageGson.setLetters(letters);
            
            List<NumberGson> numbers = new ArrayList<>();
            for (Number number : image.getNumbers()) {
                NumberGson numberGson = getNumberGson(number);
                numbers.add(numberGson);
            }
            imageGson.setNumbers(numbers);
            
            List<WordGson> words = new ArrayList<>();
            for (Word word : image.getWords()) {
                WordGson wordGson = getWordGson(word);
                words.add(wordGson);
            }
            imageGson.setWords(words);
            
            imageGson.setTitle(image.getTitle());
            imageGson.setImageFormat(image.getImageFormat());
            imageGson.setDominantColor(image.getDominantColor());
            
            return imageGson;
        }
    }

    public static LetterGson getLetterGson(Letter letter) {
        if (letter == null) {
            return null;
        } else {
            LetterGson letterGson = new LetterGson();
            
            letterGson.setId(letter.getId());
            letterGson.setLocale(letter.getLocale());
            letterGson.setTimeLastUpdate(letter.getTimeLastUpdate());
            letterGson.setRevisionNumber(letter.getRevisionNumber());
            letterGson.setContentStatus(letter.getContentStatus());
            
            letterGson.setText(letter.getText());
            List<AllophoneGson> allophones = new ArrayList<>();
            for (Allophone allophone : letter.getAllophones()) {
                AllophoneGson allophoneGson = getAllophoneGson(allophone);
                allophones.add(allophoneGson);
            }
            letterGson.setAllophones(allophones);
            letterGson.setBraille(letter.getBraille());
            letterGson.setUsageCount(letter.getUsageCount());
            
            return letterGson;
        }
    }
    
    public static NumberGson getNumberGson(Number number) {
        if (number == null) {
            return null;
        } else {
            NumberGson numberGson = new NumberGson();
            
            numberGson.setId(number.getId());
            numberGson.setLocale(number.getLocale());
            numberGson.setTimeLastUpdate(number.getTimeLastUpdate());
            numberGson.setRevisionNumber(number.getRevisionNumber());
            numberGson.setContentStatus(number.getContentStatus());
            
            numberGson.setValue(number.getValue());
            numberGson.setSymbol(number.getSymbol());
            numberGson.setWord(getWordGson(number.getWord()));
            
            List<WordGson> words = new ArrayList<>();
            for (Word word : number.getWords()) {
                WordGson wordGson = getWordGson(word);
                words.add(wordGson);
            }
            numberGson.setWords(words);
            
            return numberGson;
        }
    }
    
    public static SyllableGson getSyllableGson(Syllable syllable) {
        if (syllable == null) {
            return null;
        } else {
            SyllableGson syllableGson = new SyllableGson();
            
            syllableGson.setId(syllable.getId());
            syllableGson.setLocale(syllable.getLocale());
            syllableGson.setTimeLastUpdate(syllable.getTimeLastUpdate());
            syllableGson.setRevisionNumber(syllable.getRevisionNumber());
            syllableGson.setContentStatus(syllable.getContentStatus());
            
            syllableGson.setText(syllable.getText());
            List<AllophoneGson> allophones = new ArrayList<>();
            for (Allophone allophone : syllable.getAllophones()) {
                AllophoneGson allophoneGson = getAllophoneGson(allophone);
                allophones.add(allophoneGson);
            }
            syllableGson.setAllophones(allophones);
            syllableGson.setUsageCount(syllable.getUsageCount());
            
            return syllableGson;
        }
    }
    
    public static WordGson getWordGson(Word word) {
        if (word == null) {
            return null;
        } else {
            WordGson wordGson = new WordGson();
            
            wordGson.setId(word.getId());
            wordGson.setLocale(word.getLocale());
            wordGson.setTimeLastUpdate(word.getTimeLastUpdate());
            wordGson.setRevisionNumber(word.getRevisionNumber());
            wordGson.setContentStatus(word.getContentStatus());
            
            wordGson.setText(word.getText());
            wordGson.setPhonetics(word.getPhonetics());
            List<AllophoneGson> allophones = new ArrayList<>();
            for (Allophone allophone : word.getAllophones()) {
                AllophoneGson allophoneGson = getAllophoneGson(allophone);
                allophones.add(allophoneGson);
            }
            wordGson.setAllophones(allophones);
            wordGson.setUsageCount(word.getUsageCount());
            wordGson.setWordType(word.getWordType());
            wordGson.setSpellingConsistency(word.getSpellingConsistency());
            
            return wordGson;
        }
    }
    
    public static StoryBookGson getStoryBookGson(StoryBook storyBook) {
        if (storyBook == null) {
            return null;
        } else {
            StoryBookGson storyBookGson = new StoryBookGson();
            
            storyBookGson.setId(storyBook.getId());
            storyBookGson.setLocale(storyBook.getLocale());
            storyBookGson.setTimeLastUpdate(storyBook.getTimeLastUpdate());
            storyBookGson.setRevisionNumber(storyBook.getRevisionNumber());
            storyBookGson.setContentStatus(storyBook.getContentStatus());
            
            storyBookGson.setTitle(storyBook.getTitle());
            storyBookGson.setCoverImage(getImageGson(storyBook.getCoverImage()));
            storyBookGson.setGradeLevel(storyBook.getGradeLevel());
            
            // TODO: add pages
            
            return storyBookGson;
        }
    }
    
    public static VideoGson getVideoGson(Video video) {
        if (video == null) {
            return null;
        } else {
            VideoGson videoGson = new VideoGson();
            
            videoGson.setId(video.getId());
            videoGson.setLocale(video.getLocale());
            videoGson.setTimeLastUpdate(video.getTimeLastUpdate());
            videoGson.setRevisionNumber(video.getRevisionNumber());
            videoGson.setContentStatus(video.getContentStatus());
            
            videoGson.setDownloadUrl("/video/" + video.getId() + "." + video.getVideoFormat().toString().toLowerCase());
            videoGson.setDownloadSize(video.getBytes().length / 1024);
            videoGson.setContentType(video.getContentType());
            videoGson.setLiteracySkills(video.getLiteracySkills());
            videoGson.setNumeracySkills(video.getNumeracySkills());
            
            List<LetterGson> letters = new ArrayList<>();
            for (Letter letter : video.getLetters()) {
                LetterGson letterGson = getLetterGson(letter);
                letters.add(letterGson);
            }
            videoGson.setLetters(letters);
            
            List<NumberGson> numbers = new ArrayList<>();
            for (Number number : video.getNumbers()) {
                NumberGson numberGson = getNumberGson(number);
                numbers.add(numberGson);
            }
            videoGson.setNumbers(numbers);
            
            List<WordGson> words = new ArrayList<>();
            for (Word word : video.getWords()) {
                WordGson wordGson = getWordGson(word);
                words.add(wordGson);
            }
            videoGson.setWords(words);
            
            videoGson.setTitle(video.getTitle());
            videoGson.setVideoFormat(video.getVideoFormat());
            videoGson.setThumbnailDownloadUrl("/video/" + video.getId() + "/thumbnail.png");
            
            return videoGson;
        }
    }
    
    
    public static LetterLearningEventGson getLetterLearningEventGson(LetterLearningEvent letterLearningEvent) {
        if (letterLearningEvent == null) {
            return null;
        } else {
            LetterLearningEventGson letterLearningEventGson = new LetterLearningEventGson();
            
            letterLearningEventGson.setId(letterLearningEvent.getId());
            letterLearningEventGson.setDevice(getDeviceGson(letterLearningEvent.getDevice()));
            letterLearningEventGson.setTime(letterLearningEventGson.getTime());
            
            letterLearningEventGson.setApplication(getApplicationGson(letterLearningEvent.getApplication()));
            letterLearningEventGson.setStudent(getStudentGson(letterLearningEvent.getStudent()));
            
            letterLearningEventGson.setLetter(getLetterGson(letterLearningEvent.getLetter()));
            
            return letterLearningEventGson;
        }
    }
    
    public static NumberLearningEventGson getNumberLearningEventGson(NumberLearningEvent numberLearningEvent) {
        if (numberLearningEvent == null) {
            return null;
        } else {
            NumberLearningEventGson numberLearningEventGson = new NumberLearningEventGson();
            
            numberLearningEventGson.setId(numberLearningEvent.getId());
            numberLearningEventGson.setDevice(getDeviceGson(numberLearningEvent.getDevice()));
            numberLearningEventGson.setTime(numberLearningEventGson.getTime());
            
            numberLearningEventGson.setApplication(getApplicationGson(numberLearningEvent.getApplication()));
            numberLearningEventGson.setStudent(getStudentGson(numberLearningEvent.getStudent()));
            
            numberLearningEventGson.setNumber(getNumberGson(numberLearningEvent.getNumber()));
            
            return numberLearningEventGson;
        }
    }
    
    public static VideoLearningEventGson getVideoLearningEventGson(VideoLearningEvent videoLearningEvent) {
        if (videoLearningEvent == null) {
            return null;
        } else {
            VideoLearningEventGson videoLearningEventGson = new VideoLearningEventGson();
            
            videoLearningEventGson.setId(videoLearningEvent.getId());
            videoLearningEventGson.setDevice(getDeviceGson(videoLearningEvent.getDevice()));
            videoLearningEventGson.setTime(videoLearningEventGson.getTime());
            
            videoLearningEventGson.setApplication(getApplicationGson(videoLearningEvent.getApplication()));
            videoLearningEventGson.setStudent(getStudentGson(videoLearningEvent.getStudent()));
            
            videoLearningEventGson.setVideo(getVideoGson(videoLearningEvent.getVideo()));
            
            return videoLearningEventGson;
        }
    }
}
