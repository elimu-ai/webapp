package selenium.web.content.contributor;

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

public class EditTeamsTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseUrl() + "/content/contributor/edit-teams");
    }

    @Test
    public void testEmptySelectionError() {
    	EditTeamsPage editTeamsPage = PageFactory.initElements(driver, EditTeamsPage.class);
        editTeamsPage.clearSelection();
        editTeamsPage.submitForm();
        assertThat(editTeamsPage.isErrorMessageDisplayed(), is(true));
    }
}
