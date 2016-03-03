package org.literacyapp.web.content.image;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.model.Image;
import org.literacyapp.model.enums.ImageType;
import org.literacyapp.model.enums.Language;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/image/list")
public class ImageListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, @RequestParam(defaultValue = "ENGLISH") Language language) {
    	logger.info("handleRequest");
        
        logger.info("language: " + language);
        model.addAttribute("language", language);
        
        List<Image> images = imageDao.readLatest(language);
        model.addAttribute("images", images);

        return "content/image/list";
    }
}
