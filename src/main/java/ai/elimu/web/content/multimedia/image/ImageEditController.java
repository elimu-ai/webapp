package ai.elimu.web.content.multimedia.image;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ai.elimu.model.content.multimedia.Multimedia;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.enums.Locale;
import ai.elimu.web.content.multimedia.AbstractMultimediaEditController;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.content.ImageFormat;
import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.content.NumeracySkill;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/multimedia/image/edit")
public class ImageEditController  extends AbstractMultimediaEditController<Image> {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private AudioDao audioDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            HttpSession session,
            Model model, 
            @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        Image image = imageDao.read(id);
        model.addAttribute("image", image);

        return setModel(model,image,contributor.getLocale());
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            Image image,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (StringUtils.isBlank(image.getTitle())) {
            result.rejectValue("title", "NotNull");
        } else {
            Image existingImage = imageDao.read(image.getTitle(), image.getLocale());
            if ((existingImage != null) && !existingImage.getId().equals(image.getId())) {
                result.rejectValue("title", "NonUnique");
            }
        }
        
        try {
            setFormat(image,multipartFile,result);
        } catch (IOException e) {
            logger.error(e);
        }
        
        if (result.hasErrors()) {
            return setModel(model,image,contributor.getLocale());
        } else {
            image.setTitle(image.getTitle().toLowerCase());
            image.setTimeLastUpdate(Calendar.getInstance());
            image.setRevisionNumber(image.getRevisionNumber() + 1);
            imageDao.update(image);
            
            // TODO: store RevisionEvent
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 String text = URLEncoder.encode(
                     contributor.getFirstName() + " just edited an Image:\n" + 
                     "• Language: \"" + image.getLocale().getLanguage() + "\"\n" + 
                     "• Title: \"" + image.getTitle() + "\"\n" + 
                     "• Revision number: #" + image.getRevisionNumber() + "\n" + 
                     "See ") + "http://elimu.ai/content/multimedia/image/edit/" + image.getId();

                 String iconUrl = contributor.getImageUrl();
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

    protected String setModel(Model model,Image image, Locale locale){
        model.addAttribute("image", image);
        addContentLicensesLiteracySkillsNumeracySkills(model);
        addLettersNumbersWords(model,locale);
        Audio audio = audioDao.read(image.getTitle(),locale);
        model.addAttribute("audio", audio);
        return "content/multimedia/image/edit";
    }

}
