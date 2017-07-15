package selenium.web.admin.application;

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

public class ApplicationCreateTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.ADMIN);
        driver.get(DomainHelper.getBaseUrl() + "/admin/application/list");
    }

    @Test
    public void testSubmitEmptyForm() {
    	ApplicationListPage applicationListPage = PageFactory.initElements(driver, ApplicationListPage.class);
        applicationListPage.clickAddButton();
        
        ApplicationCreatePage applicationCreatePage = PageFactory.initElements(driver, ApplicationCreatePage.class);
        applicationCreatePage.submitForm();
        assertThat(applicationCreatePage.isErrorMessageDisplayed(), is(true));
    }
}
