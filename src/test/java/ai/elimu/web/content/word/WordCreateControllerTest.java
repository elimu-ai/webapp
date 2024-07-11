package ai.elimu.web.content.word;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class WordCreateControllerTest {

    @Autowired
    private WordCreateController wordCreateController;
    
    private MockMvc mockMvc;
    
    @Autowired
    private WordDao wordDao;
    
    @BeforeEach
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
        assertNull(wordHello);
        
        int numberOfWordsBefore = wordDao.readAll().size();
        
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/content/word/create")
                .param("timeStart", String.valueOf(System.currentTimeMillis()))
                .param("text", "hello")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        
        wordHello = wordDao.readByText("hello");
        assertNotNull(wordHello);
        assertEquals("hello", wordHello.getText());
        
        int numberOfWordsAfter = wordDao.readAll().size();
        assertEquals(numberOfWordsBefore + 1, numberOfWordsAfter);
        
        assertEquals(HttpStatus.MOVED_TEMPORARILY.value(), mvcResult.getResponse().getStatus());
        assertEquals("redirect:/content/word/list#" + wordHello.getId(), mvcResult.getModelAndView().getViewName());
    }
}
