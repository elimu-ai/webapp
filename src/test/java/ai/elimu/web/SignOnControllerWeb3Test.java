package ai.elimu.web;

import static org.junit.Assert.*;
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
public class SignOnControllerWeb3Test {
    
    @Autowired
    private SignOnControllerWeb3 signOnControllerWeb3;
    
    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        assertNotNull(signOnControllerWeb3);
        mockMvc = MockMvcBuilders.standaloneSetup(signOnControllerWeb3).build();
        assertNotNull(mockMvc);
    }
    
    @Test
    public void testHandleGetRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/sign-on/web3");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }
    
    @Test
    public void testHandleAuthorization_missingParameters() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sign-on/web3")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }
    
    @Test
    public void testHandleAuthorization_emptyParameters() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sign-on/web3")
                .param("address", "")
                .param("signature", "0x30755ed65396facf86c53e6217c52b4daebe72aa4941d89635409de4c9c7f9466d4e9aaec7977f05e923889b33c0d0dd27d7226b6e6f56ce737465c5cfd04be400")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.MOVED_TEMPORARILY.value(), mvcResult.getResponse().getStatus());
        
        requestBuilder = MockMvcRequestBuilders
                .post("/sign-on/web3")
                .param("address", "0x11f4d0A3c12e86B4b5F39B213F7E19D048276DAe")
                .param("signature", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.MOVED_TEMPORARILY.value(), mvcResult.getResponse().getStatus());
    }
    
    @Test
    public void testHandleAuthorization_invalidSignature() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/sign-on/web3")
                .param("address", "0x11f4d0A3c12e86B4b5F39B213F7E19D048276DAe")
                .param("signature", "0x30755ed65396facf86c53e6217c52b4daebe72aa4941d89635409de4c9c7f9466d4e9aaec7977f05e923889b33c0d0dd27d7226b6e6f56ce737465c5cfd04be400")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals(HttpStatus.MOVED_TEMPORARILY.value(), mvcResult.getResponse().getStatus());
    }
}
