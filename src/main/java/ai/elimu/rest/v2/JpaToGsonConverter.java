package ai.elimu.rest.v2;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.v2.gson.content.ImageGson;
import ai.elimu.model.v2.gson.content.StoryBookChapterGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.StoryBookParagraphGson;
import ai.elimu.model.v2.gson.content.WordGson;
import java.util.ArrayList;
import java.util.List;

/**
 * Convert classes from JPA/Hibernate format to POJO format, so that they can be serialized into 
 * JSON and transferred to Android applications that are connecting via the REST API.
 */
public class JpaToGsonConverter {
    
    public static WordGson getWordGson(Word word) {
        if (word == null) {
            return null;
        } else {
            WordGson wordGson = new WordGson();
            
            // BaseEntity
            wordGson.setId(word.getId());
            
            // Content
            wordGson.setRevisionNumber(word.getRevisionNumber());
            wordGson.setUsageCount(word.getUsageCount());
            
            // Word
            wordGson.setText(word.getText());
            // TODO: setLetters
            wordGson.setWordType(word.getWordType());
            
            return wordGson;
        }
    }
    
    public static ImageGson getImageGson(Image image) {
        if (image == null) {
            return null;
        } else {
            ImageGson imageGson = new ImageGson();
            
            // BaseEntity
            imageGson.setId(image.getId());
            
            // Content
            imageGson.setRevisionNumber(image.getRevisionNumber());
            imageGson.setUsageCount(image.getUsageCount());
            
            // Image
            imageGson.setTitle(image.getTitle());
            imageGson.setImageFormat(image.getImageFormat());
            imageGson.setDownloadUrl("/image/" + image.getId() + "_r" + image.getRevisionNumber() + "." + image.getImageFormat().toString().toLowerCase());
            imageGson.setDownloadSize(image.getBytes().length / 1024);
            
            return imageGson;
        }
    }
    
    public static StoryBookGson getStoryBookGson(StoryBook storyBook) {
        if (storyBook == null) {
            return null;
        } else {
            StoryBookGson storyBookGson = new StoryBookGson();
            
            // BaseEntity
            storyBookGson.setId(storyBook.getId());
            
            // Content
            storyBookGson.setRevisionNumber(storyBook.getRevisionNumber());
            storyBookGson.setUsageCount(storyBook.getUsageCount());
            
            // StoryBook
            storyBookGson.setTitle(storyBook.getTitle());
            storyBookGson.setDescription(storyBook.getDescription());
            storyBookGson.setCoverImage(getImageGson(storyBook.getCoverImage()));
            storyBookGson.setReadingLevel(storyBook.getReadingLevel());
            
            
            return storyBookGson;
        }
    }
    
    public static StoryBookChapterGson getStoryBookChapterGson(StoryBookChapter storyBookChapter) {
        if (storyBookChapter == null) {
            return null;
        } else {
            StoryBookChapterGson storyBookChapterGson = new StoryBookChapterGson();
            
            // BaseEntity
            storyBookChapterGson.setId(storyBookChapter.getId());
            
            // StoryBookChapter
            storyBookChapterGson.setSortOrder(storyBookChapter.getSortOrder());
            storyBookChapterGson.setImage(getImageGson(storyBookChapter.getImage()));
            
            return storyBookChapterGson;
        }
    }
    
    public static StoryBookParagraphGson getStoryBookParagraphGson(StoryBookParagraph storyBookParagraph) {
        if (storyBookParagraph == null) {
            return null;
        } else {
            StoryBookParagraphGson storyBookParagraphGson = new StoryBookParagraphGson();
            
            // BaseEntity
            storyBookParagraphGson.setId(storyBookParagraph.getId());
            
            // StoryBookParagraph
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
}
 