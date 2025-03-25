package hooks;
import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class hook {
    private static WebDriver driver;  // ThreadLocal WebDriver will be used in WebDriverFactory
    File sourceFile ;
   
    /* public hook() {
    }
   */ 
  
    @Before
    public void setUp() {
        // Initialize WebDriver based on the browser passed as a parameter
        // Get the browser parameter from system properties or use default value
        //String browser = System.getProperty("browser", "chrome");  // Default to chrome if not provided
        //String browser = System.getProperty("browser");  // Get browser parameter
        //driver = WebDriverFactory.getDriver(browser); // Get the WebDriver instance based on browser
        driver = WebDriverFactory.getDriver();
        // System.out.println("Before Hook - Container State: " + picoContainer.getState());
    }
   @After
    public  void tearDown() {
        // Close the driver using WebDriverFactory to handle thread-local driver
        if (driver != null) {
            //driver.quit();  // Close the browser
            WebDriverFactory.quitDriver(); // Ensure cleanup in WebDriverFactory
            System.out.println("Teardown: Browser closed.");
        }
    }

    @AfterStep
    public void attachScreenshot(Scenario scenario) throws Exception {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
        if (scenario.isFailed()) {
            System.out.println("Test failed, attaching screenshot");
            // Take screenshot and embed it
            String screenshotPath = System.getProperty("user.dir") + "/FailedScreenshots/" + scenario.getName() + ".png";
            //File destinationFile = new File("./target/reports/Screenshots/" + testName + ".png");
            File destinationFile = new File(screenshotPath);   
            FileUtils.copyFile(sourceFile, destinationFile);
        } else {
            System.out.println("Test passed");
            sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            String screenshotPath = System.getProperty("user.dir") + "/Screenshots/" + scenario.getName() + ".png";
            //File destinationFile = new File("./target/reports/Screenshots/" + testName + ".png");
            File destinationFile = new File(screenshotPath);   
            FileUtils.copyFile(sourceFile, destinationFile);
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

/*    @AfterAll
        public void openHtmlReport(){
            // Generate HTML report here
            try { 
                File htmlReportFile = new File("/target/chaintest/Index.html");
                if(htmlReportFile.exists()){
                    Desktop.getDesktop().browse(htmlReportFile.toURI());
                }else{
                    System.out.println("Report file not found:" + htmlReportFile.getAbsolutePath());
                    }
                }catch(IOException e){
                System.out.println("Report File not found");
            }
       
            //Allure
            try{
                ProcessBuilder builder = new ProcessBuilder("/usr/local/bin/allure", "serve", "allure-results");
                builder.inheritIO();
                Process process= builder.start();
                process.waitFor();

                //allure serve cmd automatically opnes report in browser
                System.out.println("Report File found");
            }catch(Exception e){
                System.out.println("Report File not found");
            }
        }

		@BeforeAll
		public void startDockerGrid() throws IOException, InterruptedException
		{
			Runtime.getRuntime().exec("cmd /c start startDockerGrid.bat");
			Thread.sleep(15000);
		}
	
		@AfterAll
		public void stopDockerGrid() throws IOException, InterruptedException
		{
			Runtime.getRuntime().exec("cmd /c start stopDockerGrid.bat");
			Thread.sleep(15000);
			
			Runtime.getRuntime().exec("taskkill /f /im cmd.exe"); // kills all process closes command prompt
		}	
*/
}

