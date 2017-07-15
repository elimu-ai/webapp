package selenium.web.content.number;

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

public class NumberCreateTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseUrl() + "/content/number/list");
    }

    @Test
    public void testSubmitEmptyForm() {
    	NumberListPage numberListPage = PageFactory.initElements(driver, NumberListPage.class);
        numberListPage.clickAddButton();
        
        NumberCreatePage numberCreatePage = PageFactory.initElements(driver, NumberCreatePage.class);
        numberCreatePage.submitForm();
        assertThat(numberCreatePage.isErrorMessageDisplayed(), is(true));
    }
}
