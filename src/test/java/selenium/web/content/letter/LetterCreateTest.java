package selenium.web.content.letter;

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

public class LetterCreateTest {

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
    public void testSubmitEmptyForm() {
    	LetterListPage letterListPage = PageFactory.initElements(driver, LetterListPage.class);
        letterListPage.clickAddButton();
        
        LetterCreatePage letterCreatePage = PageFactory.initElements(driver, LetterCreatePage.class);
        letterCreatePage.submitForm();
        assertThat(letterCreatePage.isErrorMessageDisplayed(), is(true));
    }
}
