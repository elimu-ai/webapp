package rest.v1;

import org.apache.log4j.Logger;
import org.junit.Test;
import ai.elimu.util.JsonLoader;
import selenium.DomainHelper;

public class FallbackRestControllerTest {
    
    private Logger logger = Logger.getLogger(getClass());

    @Test
    public void testList_missingParameters() {
    	String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/asdf");
        logger.info("jsonResponse: " + jsonResponse);
        // TODO: assert error message
    }
}
