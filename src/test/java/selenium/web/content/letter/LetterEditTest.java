package selenium.web.content.letter;

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

public class LetterEditTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseUrl() + "/content/letter/list");
    }

    @Test
    public void testEdit() {
    	LetterListPage letterListPage = PageFactory.initElements(driver, LetterListPage.class);
        if (letterListPage.getListCount() > 0) {
            letterListPage.clickRandomEditLink();

            LetterEditPage letterEditPage = PageFactory.initElements(driver, LetterEditPage.class);
            letterEditPage.submitForm();

            PageFactory.initElements(driver, LetterListPage.class);
        }
    }
}
