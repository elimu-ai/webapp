package ai.elimu.web.content.sound;

import java.util.Calendar;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.logging.log4j.Logger;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.v2.enums.content.allophone.SoundType;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.SoundDao;

@Controller
@RequestMapping("/content/sound/create")
public class SoundCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private SoundDao soundDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Allophone sound = new Allophone();
        model.addAttribute("sound", sound);
        
        model.addAttribute("soundTypes", SoundType.values());

        return "content/sound/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Allophone sound,
            BindingResult result,
            Model model,
            HttpSession session
    ) {
    	logger.info("handleSubmit");
        
        if (StringUtils.isNotBlank(sound.getValueIpa())) {
            Allophone existingSound = soundDao.readByValueIpa(sound.getValueIpa());
            if (existingSound != null) {
                result.rejectValue("valueIpa", "NonUnique");
            }
        }
        
        if (StringUtils.isNotBlank(sound.getValueSampa())) {
            Allophone existingSound = soundDao.readByValueSampa(sound.getValueSampa());
            if (existingSound != null) {
                result.rejectValue("valueSampa", "NonUnique");
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("sound", sound);
            model.addAttribute("soundTypes", SoundType.values());
            return "content/sound/create";
        } else {
            sound.setTimeLastUpdate(Calendar.getInstance());
            soundDao.create(sound);
            return "redirect:/content/sound/list#" + sound.getId();
        }
    }
}
