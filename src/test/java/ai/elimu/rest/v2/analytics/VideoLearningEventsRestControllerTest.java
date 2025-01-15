package ai.elimu.rest.v2.analytics;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class VideoLearningEventsRestControllerTest {

    @InjectMocks
    private VideoLearningEventsRestController videoLearningEventsRestController;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleUploadCsvRequest_invalidFilename() {
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "invalid_filename.csv",
                "text/csv",
                "test content".getBytes()
        );
        String jsonResponse = videoLearningEventsRestController.handleUploadCsvRequest(multipartFile, response);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertEquals("error", jsonObject.getString("result"));
        assertEquals("Unexpected filename", jsonObject.getString("errorMessage"));
        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testHandleUploadCsvRequest_emptyFile() {
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "7161a85a0e4751cd_3001012_video-learning-events_2020-04-23.csv",
                "text/csv",
                "".getBytes()
        );
        String jsonResponse = videoLearningEventsRestController.handleUploadCsvRequest(multipartFile, response);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertEquals("error", jsonObject.getString("result"));
        assertEquals("Empty file", jsonObject.getString("errorMessage"));
        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
