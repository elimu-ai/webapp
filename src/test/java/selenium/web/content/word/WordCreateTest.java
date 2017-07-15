package selenium.web.content.word;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import ai.elimu.model.enums.Role;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import selenium.DomainHelper;

import selenium.ScreenshotOnFailureRule;
import selenium.SignOnHelper;

public class WordCreateTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseUrl() + "/content/word/list");
    }

    @Test
    public void testSubmitEmptyForm() {
    	WordListPage wordListPage = PageFactory.initElements(driver, WordListPage.class);
        wordListPage.clickAddButton();
        
        WordCreatePage wordCreatePage = PageFactory.initElements(driver, WordCreatePage.class);
        wordCreatePage.submitForm();
        assertThat(wordCreatePage.isErrorMessageDisplayed(), is(true));
    }
}
