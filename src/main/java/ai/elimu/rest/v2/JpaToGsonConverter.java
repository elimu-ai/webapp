package ai.elimu.rest.v2;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.gson.v2.content.ImageGson;
import ai.elimu.model.gson.v2.content.StoryBookChapterGson;
import ai.elimu.model.gson.v2.content.StoryBookGson;
import ai.elimu.model.gson.v2.content.StoryBookParagraphGson;
import ai.elimu.model.gson.v2.content.WordGson;
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
            
            wordGson.setId(word.getId());
            
            wordGson.setRevisionNumber(word.getRevisionNumber());
            
            wordGson.setText(word.getText());
            // TODO: setLetters
            
            return wordGson;
        }
    }
    
    public static ImageGson getImageGson(Image image) {
        if (image == null) {
            return null;
        } else {
            ImageGson imageGson = new ImageGson();
            
            imageGson.setId(image.getId());
            
            imageGson.setRevisionNumber(image.getRevisionNumber());
            
            imageGson.setTitle(image.getTitle());
            imageGson.setImageFormat(image.getImageFormat());
            imageGson.setDownloadUrl("/image/" + image.getId() + "." + image.getImageFormat().toString().toLowerCase());
            imageGson.setDownloadSize(image.getBytes().length / 1024);
            
            return imageGson;
        }
    }
    
    public static StoryBookGson getStoryBookGson(StoryBook storyBook) {
        if (storyBook == null) {
            return null;
        } else {
            StoryBookGson storyBookGson = new StoryBookGson();
            
            storyBookGson.setId(storyBook.getId());
            
            storyBookGson.setRevisionNumber(storyBook.getRevisionNumber());
            
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
            
            storyBookChapterGson.setId(storyBookChapter.getId());
            
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
            
            storyBookParagraphGson.setId(storyBookParagraph.getId());
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
 