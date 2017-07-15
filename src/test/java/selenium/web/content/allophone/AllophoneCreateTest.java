package selenium.web.content.allophone;

import selenium.web.content.contributor.*;
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

public class AllophoneCreateTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseUrl() + "/content/allophone/list");
    }

    @Test
    public void testSubmitEmptyForm() {
    	AllophoneListPage allophoneListPage = PageFactory.initElements(driver, AllophoneListPage.class);
        allophoneListPage.clickAddButton();
        
        AllophoneCreatePage allophoneCreatePage = PageFactory.initElements(driver, AllophoneCreatePage.class);
        allophoneCreatePage.submitForm();
        assertThat(allophoneCreatePage.isErrorMessageDisplayed(), is(true));
    }
}
