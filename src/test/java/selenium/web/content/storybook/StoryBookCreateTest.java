package selenium.web.content.storybook;

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

public class StoryBookCreateTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseUrl() + "/content/storybook/list");
    }

    @Test
    public void testSubmitEmptyForm() {
    	StoryBookListPage storyBookListPage = PageFactory.initElements(driver, StoryBookListPage.class);
        storyBookListPage.clickAddButton();
        
        StoryBookCreatePage storyBookCreatePage = PageFactory.initElements(driver, StoryBookCreatePage.class);
        storyBookCreatePage.submitForm();
        assertThat(storyBookCreatePage.isErrorMessageDisplayed(), is(true));
    }
}
