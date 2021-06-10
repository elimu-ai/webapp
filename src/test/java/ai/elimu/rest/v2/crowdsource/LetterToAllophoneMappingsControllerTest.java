package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.LetterToAllophoneMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LetterToAllophoneMappingsControllerTest {

    @Mock
    LetterToAllophoneMappingDao letterToAllophoneMappingDao;

    @InjectMocks
    LetterToAllophoneMappingsController letterToAllophoneMappingsController = new LetterToAllophoneMappingsController();

    @Autowired
    private MockMvc mockMvc;

    private Logger logger = LogManager.getLogger();

    @Before
    public void setup() throws JsonProcessingException {
        JSONObject tesJson = new JSONObject("{\n" +
                "    \"allophones\": [\n" +
                "        {\n" +
                "            \"revisionNumber\": 2,\n" +
                "            \"diacritic\": false,\n" +
                "            \"valueIpa\": \"æ\",\n" +
                "            \"id\": 5,\n" +
                "            \"soundType\": \"VOWEL\",\n" +
                "            \"usageCount\": 770\n" +
                "        }\n" +
                "    ],\n" +
                "    \"id\": 2,\n" +
                "    \"letters\": [\n" +
                "        {\n" +
                "            \"revisionNumber\": 18,\n" +
                "            \"diacritic\": false,\n" +
                "            \"text\": \"a\",\n" +
                "            \"id\": 1,\n" +
                "            \"usageCount\": 914\n" +
                "        }\n" +
                "    ],\n" +
                "    \"usageCount\": 77\n" +
                "}");
        ObjectMapper mapper = new ObjectMapper();
        LetterToAllophoneMapping letterToAllophoneMapping =
                mapper.readValue(tesJson.toString(), LetterToAllophoneMapping.class);

        List<LetterToAllophoneMapping> testList = new ArrayList<>();
        testList.add(letterToAllophoneMapping);
        Mockito.when(letterToAllophoneMappingDao.readAllOrderedByUsage()).thenReturn(testList);
        mockMvc = MockMvcBuilders.standaloneSetup(letterToAllophoneMappingsController).build();
    }

    @Test
    public void testGetRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/rest/v2/crowdsource/letter-to-allophone-mappings").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String jsonResponse = result.getResponse().getContentAsString();

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
