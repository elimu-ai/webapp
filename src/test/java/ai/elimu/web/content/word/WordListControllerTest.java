package ai.elimu.web.content.word;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class WordListControllerTest {

  private final WordListController wordListController;

  @Autowired
  public WordListControllerTest(WordListController wordListController) {
    this.wordListController = wordListController;
  }

  private MockMvc mockMvc;

  @BeforeEach
  public void setup() {
    assertNotNull(wordListController);
    mockMvc = MockMvcBuilders.standaloneSetup(wordListController).build();
    assertNotNull(mockMvc);
  }

  @Test
  public void testHandleGetRequest() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/content/word/list");
    MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals("content/word/list", mvcResult.getModelAndView().getViewName());
  }
}
