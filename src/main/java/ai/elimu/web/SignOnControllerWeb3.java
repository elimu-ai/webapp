package ai.elimu.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignOnControllerWeb3 {
	
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping("/sign-on/web3")
    public String handleGetRequest(HttpServletRequest request) throws IOException {
        logger.info("handleGetRequest");
		
        return "sign-on-web3";
    }
}
