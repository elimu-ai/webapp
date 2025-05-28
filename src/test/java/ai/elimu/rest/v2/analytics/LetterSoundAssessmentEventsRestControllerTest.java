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

public class LetterSoundAssessmentEventsRestControllerTest {

    @InjectMocks
    private LetterSoundAssessmentEventsRestController letterSoundAssessmentEventsRestController;

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
        String jsonResponse = letterSoundAssessmentEventsRestController.handleUploadCsvRequest(multipartFile, response);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertEquals("error", jsonObject.getString("result"));
        assertEquals("Unexpected filename", jsonObject.getString("errorMessage"));
        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testHandleUploadCsvRequest_emptyFile() {
        MultipartFile multipartFile = new MockMultipartFile(
                "file",
                "7161a85a0e4751cd_3002023_letter-sound-assessment-events_2025-05-28.csv",
                "text/csv",
                "".getBytes()
        );
        String jsonResponse = letterSoundAssessmentEventsRestController.handleUploadCsvRequest(multipartFile, response);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertEquals("error", jsonObject.getString("result"));
        assertEquals("Empty file", jsonObject.getString("errorMessage"));
        verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
