package ai.elimu.rest.v1;

import java.util.ArrayList;
import java.util.List;
import ai.elimu.model.admin.Application;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.gson.content.NumberGson;
import ai.elimu.model.gson.content.WordGson;
import ai.elimu.model.gson.admin.ApplicationGson;
import ai.elimu.model.gson.admin.ApplicationVersionGson;
import ai.elimu.model.gson.content.AllophoneGson;
import ai.elimu.model.gson.content.EmojiGson;
import ai.elimu.model.gson.content.LetterGson;
import ai.elimu.model.gson.content.StoryBookChapterGson;
import ai.elimu.model.gson.content.StoryBookGson;
import ai.elimu.model.gson.content.StoryBookParagraphGson;
import ai.elimu.model.gson.content.SyllableGson;
import ai.elimu.model.gson.content.multimedia.AudioGson;
import ai.elimu.model.gson.content.multimedia.ImageGson;
import ai.elimu.model.gson.content.multimedia.VideoGson;

/**
 * Convert classes from JPA/Hibernate format to POJO format, so that they can be serialized into 
 * JSON and transferred to Android applications that are connecting via the REST API.
 */
public class JavaToGsonConverter {
    
    public static ApplicationGson getApplicationGson(Application application) {
        if (application == null) {
            return null;
        } else {
            ApplicationGson applicationGson = new ApplicationGson();
            applicationGson.setId(application.getId());
            applicationGson.setLanguage(application.getLanguage());
            applicationGson.setPackageName(application.getPackageName());
            applicationGson.setInfrastructural(application.isInfrastructural());
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
            applicationVersionGson.setVersionName(applicationVersion.getVersionName());
            applicationVersionGson.setLabel(applicationVersion.getLabel());
            applicationVersionGson.setMinSdkVersion(applicationVersion.getMinSdkVersion());
            applicationVersionGson.setTimeUploaded(applicationVersion.getTimeUploaded());
            return applicationVersionGson;
        }
    }
    
    
    public static AllophoneGson getAllophoneGson(Allophone allophone) {
        if (allophone == null) {
            return null;
        } else {
            AllophoneGson allophoneGson = new AllophoneGson();
            
            allophoneGson.setId(allophone.getId());
            allophoneGson.setLanguage(allophone.getLanguage());
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
    
    public static LetterGson getLetterGson(Letter letter) {
        if (letter == null) {
            return null;
        } else {
            LetterGson letterGson = new LetterGson();
            
            letterGson.setId(letter.getId());
            letterGson.setLanguage(letter.getLanguage());
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
            letterGson.setUsageCount(letter.getUsageCount());
            
            return letterGson;
        }
    }
    
    public static WordGson getWordGson(Word word) {
        if (word == null) {
            return null;
        } else {
            WordGson wordGson = new WordGson();
            
            wordGson.setId(word.getId());
            wordGson.setLanguage(word.getLanguage());
            wordGson.setTimeLastUpdate(word.getTimeLastUpdate());
            wordGson.setRevisionNumber(word.getRevisionNumber());
            wordGson.setContentStatus(word.getContentStatus());
            
            wordGson.setText(word.getText());
            // TODO: setLetters
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
    
    public static NumberGson getNumberGson(Number number) {
        if (number == null) {
            return null;
        } else {
            NumberGson numberGson = new NumberGson();
            
            numberGson.setId(number.getId());
            numberGson.setLanguage(number.getLanguage());
            numberGson.setTimeLastUpdate(number.getTimeLastUpdate());
            numberGson.setRevisionNumber(number.getRevisionNumber());
            numberGson.setContentStatus(number.getContentStatus());
            
            numberGson.setValue(number.getValue());
            numberGson.setSymbol(number.getSymbol());
            
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
            syllableGson.setLanguage(syllable.getLanguage());
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
    
    public static EmojiGson getEmojiGson(Emoji emoji) {
        if (emoji == null) {
            return null;
        } else {
            EmojiGson emojiGson = new EmojiGson();
            
            emojiGson.setId(emoji.getId());
            emojiGson.setLanguage(emoji.getLanguage());
            emojiGson.setTimeLastUpdate(emoji.getTimeLastUpdate());
            emojiGson.setRevisionNumber(emoji.getRevisionNumber());
            emojiGson.setContentStatus(emoji.getContentStatus());
            
            emojiGson.setGlyph(emoji.getGlyph());
            emojiGson.setUnicodeVersion(emoji.getUnicodeVersion());
            emojiGson.setUnicodeEmojiVersion(emoji.getUnicodeEmojiVersion());
            
            List<WordGson> words = new ArrayList<>();
            for (Word word : emoji.getWords()) {
                WordGson wordGson = getWordGson(word);
                words.add(wordGson);
            }
            emojiGson.setWords(words);
            
            return emojiGson;
        }
    }
    
    public static ImageGson getImageGson(Image image) {
        if (image == null) {
            return null;
        } else {
            ImageGson imageGson = new ImageGson();
            
            imageGson.setId(image.getId());
            imageGson.setLanguage(image.getLanguage());
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
            
    public static AudioGson getAudioGson(Audio audio) {
        if (audio == null) {
            return null;
        } else {
            AudioGson audioGson = new AudioGson();
            
            audioGson.setId(audio.getId());
            audioGson.setLanguage(audio.getLanguage());
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
    
    public static StoryBookGson getStoryBookGson(StoryBook storyBook) {
        if (storyBook == null) {
            return null;
        } else {
            StoryBookGson storyBookGson = new StoryBookGson();
            
            storyBookGson.setId(storyBook.getId());
            storyBookGson.setLanguage(storyBook.getLanguage());
            storyBookGson.setTimeLastUpdate(storyBook.getTimeLastUpdate());
            storyBookGson.setRevisionNumber(storyBook.getRevisionNumber());
            storyBookGson.setContentStatus(storyBook.getContentStatus());
            
            storyBookGson.setTitle(storyBook.getTitle());
            storyBookGson.setDescription(storyBook.getDescription());
            storyBookGson.setAttributionUrl(storyBook.getAttributionUrl());
            storyBookGson.setCoverImage(getImageGson(storyBook.getCoverImage()));
            storyBookGson.setReadingLevel(storyBook.getReadingLevel());
            
            return storyBookGson;
        }
    }
    
    public static StoryBookChapterGson getStoryBookChapter(StoryBookChapter storyBookChapter) {
        if (storyBookChapter == null) {
            return null;
        } else {
            StoryBookChapterGson storyBookChapterGson = new StoryBookChapterGson();
            
            storyBookChapterGson.setId(storyBookChapter.getId());
            storyBookChapterGson.setStoryBook(getStoryBookGson(storyBookChapter.getStoryBook()));
            storyBookChapterGson.setSortOrder(storyBookChapter.getSortOrder());
            storyBookChapterGson.setImage(getImageGson(storyBookChapter.getImage()));
            
            return storyBookChapterGson;
        }
    }
    
    public static StoryBookParagraphGson getStoryBookParagraph(StoryBookParagraph storyBookParagraph) {
        if (storyBookParagraph == null) {
            return null;
        } else {
            StoryBookParagraphGson storyBookParagraphGson = new StoryBookParagraphGson();
            
            storyBookParagraphGson.setId(storyBookParagraph.getId());
            storyBookParagraphGson.setStoryBookChapter(getStoryBookChapter(storyBookParagraph.getStoryBookChapter()));
            storyBookParagraphGson.setSortOrder(storyBookParagraph.getSortOrder());
            storyBookParagraphGson.setOriginalText(storyBookParagraph.getOriginalText());
            List<WordGson> words = new ArrayList<>();
            for (Word word : storyBookParagraph.getWords()) {
                WordGson wordGson = getWordGson(word);
                words.add(wordGson);
            }
            storyBookParagraphGson.setWords(words);
            
            return storyBookParagraphGson;
        }
    }
    
    public static VideoGson getVideoGson(Video video) {
        if (video == null) {
            return null;
        } else {
            VideoGson videoGson = new VideoGson();
            
            videoGson.setId(video.getId());
            videoGson.setLanguage(video.getLanguage());
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
}
