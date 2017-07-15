package selenium.web.content.multimedia.audio;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import ai.elimu.model.enums.Role;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.DomainHelper;

import selenium.ScreenshotOnFailureRule;
import selenium.SignOnHelper;

public class AudioEditTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseUrl() + "/content/multimedia/audio/list");
    }

    @Test
    public void testEdit() {
    	AudioListPage audioListPage = PageFactory.initElements(driver, AudioListPage.class);
        if (audioListPage.getListCount() > 0) {
            audioListPage.clickRandomEditLink();

            AudioEditPage audioEditPage = PageFactory.initElements(driver, AudioEditPage.class);
            audioEditPage.submitForm();

            // TODO
//            PageFactory.initElements(driver, AudioListPage.class);
        }
    }
    
    @Test
    public void testAddAndRemoveWordLabel() {
    	AudioListPage audioListPage = PageFactory.initElements(driver, AudioListPage.class);
        if (audioListPage.getListCount() > 0) {
            audioListPage.clickRandomEditLink();

            AudioEditPage audioEditPage = PageFactory.initElements(driver, AudioEditPage.class);
            audioEditPage.addWordLabel("apple");
            
            // Refresh current page
            driver.navigate().refresh();
            
            PageFactory.initElements(driver, AudioEditPage.class);
            audioEditPage.removeWordLabel("apple");
        }
    }
}
