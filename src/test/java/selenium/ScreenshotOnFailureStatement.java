package selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotOnFailureStatement extends Statement {

    /**
     * The URL pointing to the folder at the Jenkins server in which the target
     * folder of the project is located.
     */
    private static final String JENKINS_URL = "http://jenkins.elimu.ai:8080/job/webapp-4-regression-testing/ws/";
    
    private final Logger logger = Logger.getLogger(ScreenshotOnFailureStatement.class);

    private Statement base;
    
    private FrameworkMethod method;

    private Object target;

    public ScreenshotOnFailureStatement(Statement base, FrameworkMethod method, Object target) {
        this.base = base;
        this.method = method;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable {
        boolean testFailed = false;
        
        WebDriver driver = null;
        try {
            // Run the test
            base.evaluate();

            driver = getDriver();
        } catch (Throwable t) {
            testFailed = true;
            
            driver = getDriver();

            // Capture screenshot
            createErrorReport(driver, target.getClass(), method);

            throw t;
        } finally {
            if (driver != null) {
                boolean keepBrowserWindowOpen = testFailed && !isOnJenkinsServer();                
                if (!keepBrowserWindowOpen) {
                    driver.close();
                }
            }
        }
    }

    /**
     * This method must be called <b>after</b> {@code base.evaluate();}. If it
     * is called before, the {@WebDriver} object will not have been instantiated
     * yet.
     */
    private WebDriver getDriver() {
        WebDriver driver = null;
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == WebDriver.class) {
                field.setAccessible(true);
                try {
                    driver = (WebDriver) field.get(target);
                } catch (IllegalArgumentException ex) {
                    logger.error(null, ex);
                } catch (IllegalAccessException ex) {
                    logger.error(null, ex);
                }
            }
        }
        return driver;
    }

    /**
     * Creates a folder named {@code selenium} in the project module's
     * {@code target} folder. Here, a screenshot and HTML source code of the
     * current page are saved when an error occurs.
     *
     * @param driver The driver associated with the current browser window.
     * @param clazz The current JUnit test class.
     * @throws IOException
     */
    public static void createErrorReport(WebDriver driver, Class clazz, FrameworkMethod method) throws IOException {
        System.err.println("Test failed at " + driver.getCurrentUrl());

        // Create Selenium folder for storage of error report files
        File targetDir = new File("target");
        File seleniumDir = new File(targetDir, "selenium");
        seleniumDir.mkdir();

        // Save screenshot
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File screenshotCopy = new File(seleniumDir, clazz.getSimpleName() + "_" + method.getName() + ".png");
        FileUtils.copyFile(screenshot, screenshotCopy);
        screenshot.delete();
        if (isOnJenkinsServer()) {
            System.err.println("Screenshot: " + JENKINS_URL + seleniumDir + "/" + screenshotCopy.getName());
        } else {
            System.err.println("Screenshot was saved at " + screenshotCopy.getAbsolutePath());
        }

        // Save HTML source code
        File pageSource = new File(seleniumDir, clazz.getSimpleName() + ".html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(pageSource));
        bw.write(driver.getPageSource());
        bw.close();
        if (isOnJenkinsServer()) {
            System.err.println("Page source code: " + JENKINS_URL + seleniumDir + "/" + pageSource.getName());
        } else {
            System.err.println("Page source code was saved at " + pageSource.getAbsolutePath());
        }
    }

    private static boolean isOnJenkinsServer() {
        try {
            return "astra2263.startdedicated.com".equals(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException ex) {
            return false;
        }
    }
}
