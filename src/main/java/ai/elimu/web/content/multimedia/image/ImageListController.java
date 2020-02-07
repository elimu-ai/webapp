package ai.elimu.web.content.multimedia.image;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.ImageFormat;
import ai.elimu.util.ImageColorHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/multimedia/image/list")
public class ImageListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate Images
        List<Image> imagesGenerated = generateImages(contributor.getLanguage());
        for (Image image : imagesGenerated) {
            String contentType = image.getContentType();
            Image existingImage = imageDao.read(image.getTitle(), image.getLanguage());
            if (existingImage == null) {
                ImageFormat imageFormat = ImageFormat.PNG;
                image.setContentType("image/png");
                if (StringUtils.isNotBlank(contentType) && "image/jpg".equals(contentType)) {
                    imageFormat = ImageFormat.JPG;
                    image.setContentType("image/jpg");
                }
                String fileName = image.getTitle() + "." + imageFormat.toString().toLowerCase();
                logger.info("Looking up file \"" + image.getTitle() + "." + imageFormat.toString().toLowerCase() + "\"...");
                URL url = getClass().getResource(fileName);
                logger.info("url.getPath(): " + url.getPath());
                try {
                    byte[] imageBytes = IOUtils.toByteArray(url);
                    image.setBytes(imageBytes);
                    image.setImageFormat(imageFormat);
                    int[] dominantColor = ImageColorHelper.getDominantColor(image.getBytes());
                    image.setDominantColor("rgb(" + dominantColor[0] + "," + dominantColor[1] + "," + dominantColor[2] + ")");
                    imageDao.create(image);
                } catch (IOException ex) {
                    logger.error(null, ex);
                }
            }
        }
        
        List<Image> images = imageDao.readAllOrdered(contributor.getLanguage());
        model.addAttribute("images", images);

        return "content/multimedia/image/list";
    }
    
    private List<Image> generateImages(Language language) {
        List<Image> images = new ArrayList<>();
        
        Image coverImage175 = new Image();
        coverImage175.setLanguage(language);
        coverImage175.setTimeLastUpdate(Calendar.getInstance());
        coverImage175.setTitle("ASP_175_I_like_to_read_Page_01_Image_0001");
        images.add(coverImage175);
        
        Image coverImage55 = new Image();
        coverImage55.setLanguage(language);
        coverImage55.setTimeLastUpdate(Calendar.getInstance());
        coverImage55.setTitle("M_ASP_55_Too_small_Page_02_Image_0001");
        images.add(coverImage55);
        
        Image coverImageGraceInSpace = new Image();
        coverImageGraceInSpace.setLanguage(language);
        coverImageGraceInSpace.setTimeLastUpdate(Calendar.getInstance());
        coverImageGraceInSpace.setTitle("39e5eb1614ea195e9e377f63f561aa8c");
        coverImageGraceInSpace.setContentType("image/jpg");
        images.add(coverImageGraceInSpace);
        
        Image coverImageWhatIf = new Image();
        coverImageWhatIf.setLanguage(language);
        coverImageWhatIf.setTimeLastUpdate(Calendar.getInstance());
        coverImageWhatIf.setTitle("badd7122aa68d2a339e359f03c03cc51");
        coverImageWhatIf.setContentType("image/jpg");
        images.add(coverImageWhatIf);
        
        Image coverImageHindiNaAkoNatatakot = new Image();
        coverImageHindiNaAkoNatatakot.setLanguage(language);
        coverImageHindiNaAkoNatatakot.setTimeLastUpdate(Calendar.getInstance());
        coverImageHindiNaAkoNatatakot.setTitle("Hindi_na_Ako_natatakot_coverImage");
        coverImageHindiNaAkoNatatakot.setContentType("image/jpg");
        images.add(coverImageHindiNaAkoNatatakot);
        
        return images;
    }
}
