package ai.elimu.web.analytics.device;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.DeviceDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/device/list")
public class DeviceListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private DeviceDao deviceDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        List<Device> devices = deviceDao.readAll(contributor.getLocale());
        model.addAttribute("devices", devices);

        return "analytics/device/list";
    }
}
