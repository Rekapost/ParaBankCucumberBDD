package hooks;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class hook {
    private static WebDriver driver;

    @Before
    public void setUp(Scenario scenario) {
        // Retrieve the browser value from system properties or set a default
        String browser = System.getProperty("browser", "chrome"); // Default to "chrome" if not provided
        driver = WebDriverFactory.getDriver(browser); // Get the WebDriver instance
        System.out.println("Starting scenario: " + scenario.getName());
    }

    // Public method to return the driver
    public static WebDriver getDriver() {
        return driver;
    }

    @After
    public void tearDown(Scenario scenario) {
        // Ensure the driver has not been quit already and it's safe to close
        if (driver != null) {
            System.out.println("Finished scenario: " + scenario.getName());
            if (scenario.isFailed()) {
                System.out.println("Scenario failed: " + scenario.getName());
            }

            // Cleanup WebDriver instance (make sure it's a thread-local driver)
            WebDriverFactory.quitDriver(); // This ensures the correct driver cleanup
            System.out.println("Teardown: Browser closed.");
        }
    }

    @AfterStep
    public void attachScreenshot(Scenario scenario) throws IOException {
        if (driver instanceof TakesScreenshot) {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Use timestamp for unique screenshot names
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotDirectory = scenario.isFailed() ? "/FailedScreenshots/" : "/Screenshots/";

            // Ensure the directories exist
            File screenshotDir = new File(System.getProperty("user.dir") + screenshotDirectory);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            String screenshotPath = System.getProperty("user.dir") + screenshotDirectory + scenario.getName() + "_" + timestamp + ".png";
            File destinationFile = new File(screenshotPath);

            // Copy the screenshot to the destination
            FileUtils.copyFile(sourceFile, destinationFile);

            System.out.println("Screenshot saved to: " + screenshotPath);
        }
    }

}
