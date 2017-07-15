package selenium.web.content.multimedia.video;

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

public class VideoEditTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseUrl() + "/content/multimedia/video/list");
    }

    @Test
    public void testEdit() {
    	VideoListPage videoListPage = PageFactory.initElements(driver, VideoListPage.class);
        if (videoListPage.getListCount() > 0) {
            videoListPage.clickRandomEditLink();

            VideoEditPage videoEditPage = PageFactory.initElements(driver, VideoEditPage.class);
            videoEditPage.submitForm();

            // TODO
//            PageFactory.initElements(driver, VideoListPage.class);
        }
    }
}
