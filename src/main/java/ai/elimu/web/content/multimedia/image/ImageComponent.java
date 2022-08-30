package ai.elimu.web.content.multimedia.image;

import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.util.ImageHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

import static ai.elimu.util.ImageHelper.MAX_BYTE_SIZE;

@Component
public class ImageComponent {

    private final Logger logger = LogManager.getLogger();

    public void validImageTypeAndSize(MultipartFile multipartFile, BindingResult result, Image image) {
        try {
            byte[] bytes = multipartFile.getBytes();
            if (multipartFile.isEmpty() || (bytes == null) || (bytes.length == 0)) {
                result.rejectValue("bytes", "NotNull");
            } else {
                String originalFileName = multipartFile.getOriginalFilename();
                logger.info("originalFileName: " + originalFileName);

                byte[] headerBytes = Arrays.copyOfRange(bytes, 0, 6);
                byte[] gifHeader87a = {71, 73, 70, 56, 55, 97}; // "GIF87a"
                byte[] gifHeader89a = {71, 73, 70, 56, 57, 97}; // "GIF89a"
                if (Arrays.equals(gifHeader87a, headerBytes) || Arrays.equals(gifHeader89a, headerBytes)) {
                    image.setImageFormat(ImageFormat.GIF);
                } else if (originalFileName.toLowerCase().endsWith(".png")) {
                    image.setImageFormat(ImageFormat.PNG);
                } else if (originalFileName.toLowerCase().endsWith(".jpg") || originalFileName.toLowerCase().endsWith(".jpeg")) {
                    image.setImageFormat(ImageFormat.JPG);
                } else if (originalFileName.toLowerCase().endsWith(".gif")) {
                    image.setImageFormat(ImageFormat.GIF);
                } else {
                    result.rejectValue("bytes", "typeMismatch");
                }

                if (image.getImageFormat() != null) {
                    String contentType = multipartFile.getContentType();
                    logger.info("contentType: " + contentType);
                    image.setContentType(contentType);

                    image.setBytes(bytes);

                    if (image.getImageFormat() != ImageFormat.GIF) {
                        int width = ImageHelper.getWidth(bytes);
                        logger.info("width: " + width + "px");

                        if (width < ImageHelper.MINIMUM_WIDTH) {
                            result.rejectValue("bytes", "image.too.small");
                            image.setBytes(null);
                        } else if (width > ImageHelper.MINIMUM_WIDTH){
                            bytes = ImageHelper.scaleImage(bytes, ImageHelper.MINIMUM_WIDTH);
                            image.setBytes(bytes);
                        }
                    }
                }

                if (bytes.length > MAX_BYTE_SIZE) {
                    result.rejectValue("bytes", "file.size.too.big");
                }
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }

}
