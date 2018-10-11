package ai.elimu.web.content.multimedia.image;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ai.elimu.web.content.multimedia.AbstractMultimediaController;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.content.ImageFormat;
import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.content.NumeracySkill;
import ai.elimu.util.ImageColorHelper;
import ai.elimu.util.ImageHelper;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/multimedia/image/create")
public class ImageCreateController extends AbstractMultimediaController {
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Image image = new Image();
        model.addAttribute("image", image);

        addContentLicensesLiteracySkillsNumeracySkills(model);

        return "content/multimedia/image/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            /*@Valid*/ Image image,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (StringUtils.isBlank(image.getTitle())) {
            result.rejectValue("title", "NotNull");
        } else {
            Image existingImage = imageDao.read(image.getTitle(), image.getLocale());
            if (existingImage != null) {
                result.rejectValue("title", "NonUnique");
            }
        }
        
        try {
            setFormat(image,multipartFile,result);
        } catch (IOException e) {
            logger.error(e);
        }
        
        if (result.hasErrors()) {
            addContentLicensesLiteracySkillsNumeracySkills(model);
            return "content/multimedia/image/create";
        } else {
            image.setTitle(image.getTitle().toLowerCase());
            int[] dominantColor = ImageColorHelper.getDominantColor(image.getBytes());
            image.setDominantColor("rgb(" + dominantColor[0] + "," + dominantColor[1] + "," + dominantColor[2] + ")");
            image.setTimeLastUpdate(Calendar.getInstance());
            imageDao.create(image);
            
            // TODO: store RevisionEvent
            
            // Label Image with Word of matching title
            Word matchingWord = wordDao.readByText(contributor.getLocale(), image.getTitle());
            if (matchingWord != null) {
                Set<Word> labeledWords = new HashSet<>();
                if (!labeledWords.contains(matchingWord)) {
                    labeledWords.add(matchingWord);
                    image.setWords(labeledWords);
                    imageDao.update(image);
                }
            }
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 String text = URLEncoder.encode(
                     contributor.getFirstName() + " just added a new Image:\n" + 
                     "• Language: \"" + image.getLocale().getLanguage() + "\"\n" + 
                     "• Title: \"" + image.getTitle() + "\"\n" +
                     "See ") + "http://elimu.ai/content/multimedia/image/edit/" + image.getId();
                 String imageUrl = "http://elimu.ai/image/" + image.getId() + "." + image.getImageFormat().toString().toLowerCase();
                 postMessageOnSlack(text,contributor.getImageUrl(),imageUrl);
            }
            
            return "redirect:/content/multimedia/image/list#" + image.getId();
        }
    }
    
    /**
     * See http://www.mkyong.com/spring-mvc/spring-mvc-failed-to-convert-property-value-in-file-upload-form/
     * <p></p>
     * Fixes this error message:
     * "Cannot convert value of type [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile] to required type [byte] for property 'bytes[0]'"
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
    	logger.info("initBinder");
    	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
