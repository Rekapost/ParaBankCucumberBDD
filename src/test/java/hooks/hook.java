package hooks;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.io.File;
import org.openqa.selenium.TakesScreenshot;

public class hook {
    public static WebDriver driver;
    File sourceFile ;
    public hook() {
    }
    
    @Before
    public void setUp() {
        driver = WebDriverFactory.getDriver("chrome");
        }

/*   @After
    public  void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Teardown: Browser closed.");
        }
    }
*/

    @AfterStep
    public void attachScreenshot(Scenario scenario) throws Exception {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
        if (scenario.isFailed()) {
            System.out.println("Test failed, attaching screenshot");
            // Take screenshot and embed it
            String screenshotPath = System.getProperty("user.dir") + "/FailedScreenshots/" + scenario + ".png";
            //File destinationFile = new File("./target/reports/Screenshots/" + testName + ".png");
            File destinationFile = new File(screenshotPath);   
            FileUtils.copyFile(sourceFile, destinationFile);
        } else {
            System.out.println("Test passed");
            sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            String screenshotPath = System.getProperty("user.dir") + "/Screenshots/" + scenario + ".png";
            //File destinationFile = new File("./target/reports/Screenshots/" + testName + ".png");
            File destinationFile = new File(screenshotPath);   
            FileUtils.copyFile(sourceFile, destinationFile);
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }
}

