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
}
