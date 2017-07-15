package selenium.web.content.word;

import selenium.web.content.word.*;
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

public class WordEditTest {

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
    public void testEdit() {
    	WordListPage wordListPage = PageFactory.initElements(driver, WordListPage.class);
        if (wordListPage.getListCount() > 0) {
            wordListPage.clickRandomEditLink();

            WordEditPage wordEditPage = PageFactory.initElements(driver, WordEditPage.class);
            wordEditPage.submitForm();

            PageFactory.initElements(driver, WordListPage.class);
        }
    }
}
