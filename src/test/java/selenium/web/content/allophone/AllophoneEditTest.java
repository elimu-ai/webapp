package selenium.web.content.allophone;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.literacyapp.model.enums.Role;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import selenium.DomainHelper;

import selenium.ScreenshotOnFailureRule;
import selenium.SignOnHelper;

public class AllophoneEditTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseDomain() + "/content/allophone/list");
    }

    @Test
    public void testEditAllophone() {
    	AllophoneListPage allophoneListPage = PageFactory.initElements(driver, AllophoneListPage.class);
        allophoneListPage.clickEditLinkRandomAllophone();
        
        AllophoneEditPage allophoneEditPage = PageFactory.initElements(driver, AllophoneEditPage.class);
        allophoneEditPage.submitForm();
        
        PageFactory.initElements(driver, AllophoneListPage.class);
    }
}
