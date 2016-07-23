package rest.content.allophone;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.literacyapp.util.JsonLoader;
import selenium.DomainHelper;

public class AllophoneListTest {
    
    private Logger logger = Logger.getLogger(getClass());

    @Test
    public void testList_missingParameters() {
    	String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrl() + "/content/allophone/list");
        logger.info("jsonResponse: " + jsonResponse);
        // TODO: assert error message
    }
}
