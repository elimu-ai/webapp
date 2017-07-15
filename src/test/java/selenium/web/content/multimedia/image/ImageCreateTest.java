package selenium.web.content.multimedia.image;

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

public class ImageCreateTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();
    
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        SignOnHelper.signOnRole(driver, Role.CONTRIBUTOR);
        driver.get(DomainHelper.getBaseUrl() + "/content/multimedia/image/list");
    }

    @Test
    public void testSubmitEmptyForm() {
    	ImageListPage imageListPage = PageFactory.initElements(driver, ImageListPage.class);
        imageListPage.clickAddButton();
        
        ImageCreatePage imageCreatePage = PageFactory.initElements(driver, ImageCreatePage.class);
        imageCreatePage.submitForm();
        assertThat(imageCreatePage.isErrorMessageDisplayed(), is(true));
    }
}
