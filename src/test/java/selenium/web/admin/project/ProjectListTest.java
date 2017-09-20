package selenium.web.admin.project;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import ai.elimu.model.enums.Role;
import selenium.DomainHelper;
import selenium.ScreenshotOnFailureRule;
import selenium.SignOnHelper;

public class ProjectListTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.ADMIN);
        driver.get(DomainHelper.getBaseUrl() + "/admin/project/list");
    }
    
    @Test
    public void testListNotEmpty() {

    	ProjectListPage projectListPage = PageFactory.initElements(driver, ProjectListPage.class);
    	Boolean isEmpty = projectListPage.checkIfListItemIsEmpty();
    	
    	assertTrue(isEmpty);
    }
}
