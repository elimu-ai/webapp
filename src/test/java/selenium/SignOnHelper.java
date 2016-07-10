package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import selenium.web.content.MainContentPage;

public class SignOnHelper {
    
    public static void signOnRoleAdmin(WebDriver driver) {
        driver.get(DomainHelper.getBaseDomain() + "/sign-on/test/role-admin");
        
        MainContentPage mainContentPage = PageFactory.initElements(driver, MainContentPage.class);
    }
    
    public static void signOnRoleAnalyst(WebDriver driver) {
        driver.get(DomainHelper.getBaseDomain() + "/sign-on/test/role-analyst");
        
        MainContentPage mainContentPage = PageFactory.initElements(driver, MainContentPage.class);
    }
    
    public static void signOnRoleContributor(WebDriver driver) {
        driver.get(DomainHelper.getBaseDomain() + "/sign-on/test/role-contributor");
        
        MainContentPage mainContentPage = PageFactory.initElements(driver, MainContentPage.class);
    }
}
