package ai.elimu.utilTest;

import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.v2.enums.content.ImageFormat;

import java.util.Set;

public class ImageUtil {

    public static Image getImage(String title) {
        Image image = new Image();
        image.setImageFormat(ImageFormat.GIF);
        image.setTitle(title);
        image.setBytes(new byte[1]);
        image.setContentType("contentType");
        return image;
    }

    public static Image getImage(String title, Set<Word> words) {
        Image image = getImage(title);
        image.setWords(words);
        return image;
    }

}
