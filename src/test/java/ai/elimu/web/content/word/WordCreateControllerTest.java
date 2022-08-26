package ai.elimu.web.content.word;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class WordCreateControllerTest {

    @Autowired
    private WordCreateController wordCreateController;
    
    private MockMvc mockMvc;
    
    @Autowired
    private WordDao wordDao;

    @Before
    public void setup() {
        assertNotNull(wordCreateController);
        mockMvc = MockMvcBuilders.standaloneSetup(wordCreateController).build();
        assertNotNull(mockMvc);
    }
    
    @Test
    public void testHandleGetRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/content/word/create");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals("content/word/create", mvcResult.getModelAndView().getViewName());
    }
    
    @Test
    public void testHandleSubmit_emptyText() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/content/word/create")
                .param("timeStart", String.valueOf(System.currentTimeMillis()))
                .param("text", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
//        assertEquals("content/word/create", mvcResult.getModelAndView().getViewName());
    }

    @Test
    public void testHandleSubmit_success() throws Exception {
        Word wordHello = wordDao.readByText("hello");
        assertThat(wordHello, is(nullValue()));

        int numberOfWordsBefore = wordDao.readAll().size();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/content/word/create")
                .param("timeStart", String.valueOf(System.currentTimeMillis()))
                .param("text", "hello")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        wordHello = wordDao.readByText("hello");
        assertThat(wordHello, not(nullValue()));
        assertThat(wordHello.getText(), is("hello"));

        int numberOfWordsAfter = wordDao.readAll().size();
        assertThat(numberOfWordsAfter, is(numberOfWordsBefore + 1));

        assertEquals(HttpStatus.MOVED_TEMPORARILY.value(), mvcResult.getResponse().getStatus());
        assertEquals("redirect:/content/word/list#" + wordHello.getId(), mvcResult.getModelAndView().getViewName());
    }

    @Test
    public void testValidateDigitsInWord() throws Exception {
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("10")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("'10'")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("\"10\"")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("[10]")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1'0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1-0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1*0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1/0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1!0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1#0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1%0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1()0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1_0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1+0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1{}0")).getAllErrors(), "WordNumbers"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("1|?><.,0")).getAllErrors(), "WordNumbers"));
        assertFalse(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("10th")).getAllErrors(), "WordNumbers"));
    }

    @Test
    public void testValidateSpacesInWord() throws Exception {
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("Test Test")).getAllErrors(), "WordSpace"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText(" Test")).getAllErrors(), "WordSpace"));
        assertTrue(containGivenErrorCode(getBindingResult(getRequestWithSpecificText("Test ")).getAllErrors(), "WordSpace"));
    }

    private RequestBuilder getRequestWithSpecificText(String text) {
        return MockMvcRequestBuilders
                .post("/content/word/create")
                .param("timeStart", String.valueOf(System.currentTimeMillis()))
                .param("text", text)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    private BindingResult getBindingResult(RequestBuilder requestBuilder) throws Exception {
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        return (BindingResult) mvcResult.getModelAndView().getModel().get("org.springframework.validation.BindingResult.word");
    }

    private boolean containGivenErrorCode(List<ObjectError> objectErrorList, String error) {
        for (ObjectError objectError : objectErrorList) {
            for (String errorCode : objectError.getCodes()) {
                if (errorCode.equals(error)) {
                    return true;
                }
            }
        }
        return false;
    }

}
