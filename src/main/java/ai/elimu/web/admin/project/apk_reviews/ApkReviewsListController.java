package ai.elimu.web.admin.project.apk_reviews;

import org.apache.log4j.Logger;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.enums.admin.ApplicationVersionStatus;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/project/apk-reviews")
public class ApkReviewsListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationVersionDao applicationVersionDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            Model model
    ) {
    	logger.info("handleRequest");
        
        List<ApplicationVersion> applicationVersions = applicationVersionDao.readAllByStatus(ApplicationVersionStatus.PENDING_APPROVAL);
        model.addAttribute("applicationVersions", applicationVersions);

        return "admin/project/apk-reviews/list";
    }
}
