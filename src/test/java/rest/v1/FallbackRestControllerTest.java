package rest.v1;

import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import selenium.DomainHelper;

@Deprecated
public class FallbackRestControllerTest {
    
    private Logger logger = LogManager.getLogger();

    @Test
    public void testList_missingParameters() {
    	String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/asdf");
        logger.info("jsonResponse: " + jsonResponse);
        // TODO: assert error message
    }
}
