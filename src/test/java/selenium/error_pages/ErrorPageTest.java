package selenium.error_pages;

import org.junit.Before;
import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import selenium.DomainHelper;
import selenium.ErrorHelper;
import selenium.ScreenshotOnFailureRule;

public class ErrorPageTest {

    @Rule
    public MethodRule methodRule = new ScreenshotOnFailureRule();

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @Test
    public void testHttp403() {
        driver.get(DomainHelper.getBaseUrl() + "/static/img");
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
        
        ErrorPage errorPage = PageFactory.initElements(driver, ErrorPage.class);
        assertTrue("The error page was not displayed", errorPage.isDisplayed());
        assertTrue("Error code was not 403", errorPage.isCode403());
    }

    @Test
    public void testHttp404() {
        driver.get(DomainHelper.getBaseUrl() + "/asdf");
        ErrorHelper.verifyNoScriptOrMarkupError(driver);
        
        ErrorPage errorPage = PageFactory.initElements(driver, ErrorPage.class);
        assertTrue("The error page was not displayed", errorPage.isDisplayed());
        assertTrue("Error code was not 404", errorPage.isCode404());
    }
}
