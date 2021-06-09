package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.LetterToAllophoneMapping;
import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import selenium.DomainHelper;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class LetterToAllophoneMappingsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Logger logger = LogManager.getLogger();

    LetterToAllophoneMapping exampleLetterToAllophoneMapping = new LetterToAllophoneMapping();

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new LetterToAllophoneMappingsController()).build();
    }

    @Test
    public void testGetRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/crowdsource/letter-to-allophone-mappings").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        logger.info("sdsdsdsdsd");
        logger.info(result.getResponse().getContentAsString());

        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() +
                "/crowdsource/letter-to-allophone-mappings");

        logger.info("jsonResponse: " + jsonResponse);

        JSONArray letterToAllophoneMappingsJSONArray = new JSONArray(jsonResponse);
        logger.info("letterToAllophoneMappingsJSONArray.length(): " + letterToAllophoneMappingsJSONArray.length());
        logger.info(letterToAllophoneMappingsJSONArray.get(0));
        assertThat(letterToAllophoneMappingsJSONArray.length() > 0, is(true));

        JSONObject letterToAllophoneMappingJsonObject = letterToAllophoneMappingsJSONArray.getJSONObject(0);
        assertThat(letterToAllophoneMappingJsonObject.getLong("id"), not(nullValue()));
        assertThat(letterToAllophoneMappingJsonObject.has("letters"), is(true));
        assertThat(letterToAllophoneMappingJsonObject.has("allophones"), is(true));
    }
}
