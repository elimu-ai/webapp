package ai.elimu.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

public class ImageHelper {
    
    public static final int MINIMUM_WIDTH = 640;
    
    private static Logger logger = Logger.getLogger(ImageHelper.class);
    
    /** 
     * TODO: add method detecting image type (PNG/JPG/GIF)
     */
    public static int getWidth(byte[] imageBytes) {
        logger.info("getWidth");
        
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        java.awt.Image awtImage = null;
        try {
            awtImage = ImageIO.read(byteArrayInputStream);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        return awtImage.getWidth(null);
    }
    
    /** 
     * Return null if the requested width is larger than the width of the original image
     */
    public static byte[] scaleImage(byte[] imageBytes, int width) {
        logger.info("scaleImage");
        
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        java.awt.Image awtImage = null;
        try {
            awtImage = ImageIO.read(byteArrayInputStream);
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        int originalWidth = awtImage.getWidth(null);
        int originalHeight = awtImage.getHeight(null);
        
        if (originalWidth <= width) {
            // Abort scaling if requested width is larger than original width
            return null;
        } else {
            BufferedImage bufferedImage = new BufferedImage(originalWidth, originalHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2d = bufferedImage.createGraphics();
            graphics2d.drawImage(awtImage, null, null);

            logger.debug("Scaling image to width: " + width + "px (original size: " + originalWidth + "x" + originalHeight + "px");
            BufferedImage bufferedImageScaled = Scalr.resize(bufferedImage, width);

            byte[] scaledImageBytes = null;
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImageScaled, "png", byteArrayOutputStream);
                byteArrayOutputStream.flush();
                scaledImageBytes = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
            } catch (IOException ex) {
                logger.error(null, ex);
            }

            return scaledImageBytes;
        }
    }
}
