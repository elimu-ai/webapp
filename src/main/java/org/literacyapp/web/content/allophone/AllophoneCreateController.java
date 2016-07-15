package org.literacyapp.web.content.allophone;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.dao.ApplicationVersionDao;
import org.literacyapp.model.Allophone;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.admin.ApplicationVersion;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.admin.application.ApplicationStatus;
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
@RequestMapping("/content/allophone/create")
public class AllophoneCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Allophone allophone = new Allophone();
        model.addAttribute("allophone", allophone);

        return "content/allophone/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Allophone allophone,
            BindingResult result,
            Model model,
            HttpSession session
    ) {
    	logger.info("handleSubmit");
        
        if (StringUtils.isNotBlank(allophone.getValueIpa())) {
            if (allophone.getValueIpa().length() > 1) {
                result.rejectValue("valueIpa", "TooLong");
            }
            
            Allophone existingAllophone = allophoneDao.readByValueIpa(allophone.getLocale(), allophone.getValueIpa());
            if (existingAllophone != null) {
                result.rejectValue("valueIpa", "NonUnique");
            }
        }
        
        if (StringUtils.isNotBlank(allophone.getValueSampa())) {
            if (allophone.getValueSampa().length() > 1) {
                result.rejectValue("valueSampa", "TooLong");
            }

            Allophone existingAllophone = allophoneDao.readByValueSampa(allophone.getLocale(), allophone.getValueSampa());
            if (existingAllophone != null) {
                result.rejectValue("valueSampa", "NonUnique");
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("allophone", allophone);
            return "content/allophone/create";
        } else {
            allophone.setCalendar(Calendar.getInstance());
            allophoneDao.create(allophone);
            return "redirect:/content/allophone/list";
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
