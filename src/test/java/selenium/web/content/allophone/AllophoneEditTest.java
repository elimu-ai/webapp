package selenium.web.content.allophone;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
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

@Ignore
public class AllophoneEditTest {

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
    public void testEdit() {
    	AllophoneListPage allophoneListPage = PageFactory.initElements(driver, AllophoneListPage.class);
        if (allophoneListPage.getListCount() > 0) {
            allophoneListPage.clickRandomEditLink();

            AllophoneEditPage allophoneEditPage = PageFactory.initElements(driver, AllophoneEditPage.class);
            allophoneEditPage.submitForm();

            PageFactory.initElements(driver, AllophoneListPage.class);
        }
    }
}
