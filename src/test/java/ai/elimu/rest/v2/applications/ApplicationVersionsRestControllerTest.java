package ai.elimu.rest.v2.applications;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@Slf4j
public class ApplicationVersionsRestControllerTest {

    @InjectMocks
    private ApplicationVersionsRestController applicationVersionsRestController;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandlePutRequest_emptyJsonBody() {
        String packageName = "ai.elimu.soundcards";
        String versionName = "2.1.2";
        String jsonData = "{}";
        String jsonResponse = applicationVersionsRestController.handlePutRequest(packageName, versionName, jsonData, response);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertEquals("error", jsonObject.getString("result"));
        assertEquals("class java.lang.IllegalArgumentException: fileUrl.missing", jsonObject.getString("errorMessage"));
        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testHandlePutRequest_emptyFileUrl() {
        String packageName = "ai.elimu.soundcards";
        String versionName = "2.1.2";
        String jsonData = "{ \"fileUrl\": \"\" }";
        String jsonResponse = applicationVersionsRestController.handlePutRequest(packageName, versionName, jsonData, response);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertEquals("error", jsonObject.getString("result"));
        assertEquals("class java.lang.IllegalArgumentException: fileUrl.empty", jsonObject.getString("errorMessage"));
        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
