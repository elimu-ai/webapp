package selenium.web.content.number;

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

public class NumberEditTest {

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
    public void testEdit() {
    	NumberListPage numberListPage = PageFactory.initElements(driver, NumberListPage.class);
        if (numberListPage.getListCount() > 0) {
            numberListPage.clickRandomEditLink();

            NumberEditPage numberEditPage = PageFactory.initElements(driver, NumberEditPage.class);
            numberEditPage.submitForm();

            PageFactory.initElements(driver, NumberListPage.class);
        }
    }
}
