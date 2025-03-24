package testRunner;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import hooks.WebDriverFactory;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import utilities.RetryAnalyzer;

@Test(retryAnalyzer = RetryAnalyzer.class)  // Adding RetryAnalyzer for retrying failed tests
@CucumberOptions(
        features = "src/test/resources/features/",
        tags = "not @multiplelogin",  // Correct tag for filtering scenarios
        glue = {"stepDefinitions", "hooks"},
        dryRun = false,
        monochrome = true,
        plugin = {"pretty",
                  "html:target/cucumber-reports.html",
                  "json:target/cucumber-reports.json",
                  "junit:target/cucumber-reports.xml",
                  "com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
                  "timeline:test-output-thread/"}
)

public class TestNgRunner extends AbstractTestNGCucumberTests {

  private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
  private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);
  
    @Parameters("browser")
    @BeforeClass
    public void setUp(String browser) {
        WebDriver driver = WebDriverFactory.getDriver(browser);
        driverThreadLocal.set(driver);  // Set the WebDriver for this thread
    }

    @AfterClass
    public void tearDown() {
        WebDriver driver = driverThreadLocal.get(); // Get the WebDriver for this thread
        if (driver != null) {
            driver.quit(); // Quit the WebDriver instance for this thread
        }
        WebDriverFactory.quitDriver(); // Call quitDriver for cleanup
    }
 
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        Object[][] data = super.scenarios();
        
        // Debugging to check if data is coming in as expected
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("No scenarios were provided by the DataProvider");
        }
        
        // Log each scenario to confirm it's valid
        for (Object[] scenario : data) {
            if (scenario == null || scenario.length != 2) {
                throw new IllegalArgumentException("Scenario data is malformed");
            }
            PickleWrapper pickleWrapper = (PickleWrapper) scenario[0];
            FeatureWrapper featureWrapper = (FeatureWrapper) scenario[1];
            if (pickleWrapper == null || featureWrapper == null) {
                throw new IllegalArgumentException("PickleWrapper or FeatureWrapper is null in the DataProvider");
            }
            System.out.println("DataProvider scenario: " + pickleWrapper.getPickle().getName());
        }
    
        return data;
    }
    @Override
    @Test(dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        //super.runScenario(pickleWrapper, featureWrapper);
    }

    // Get WebDriver instance for the current thread
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}
/*public class TestNgRunner extends AbstractTestNGCucumberTests {

    // ThreadLocal to ensure each thread (test) has its own WebDriver instance.
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    @Parameters("browser")
    @BeforeClass
    public void setUp(String browser) {
		System.out.println("Initializing WebDriver for browser: " + browser);
        WebDriver driver = WebDriverFactory.getDriver(browser);
        driverThreadLocal.set(driver);  // Set the WebDriver for this thread
    }

    @AfterClass
    public void tearDown() {
        WebDriver driver = driverThreadLocal.get(); // Get the WebDriver for this thread
        if (driver != null) {
            driver.quit(); // Quit the WebDriver instance for this thread
        }
        WebDriverFactory.quitDriver(); // Call quitDriver for cleanup
    }

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		Object[][] data = super.scenarios();
		
		// Debugging to check if data is coming in as expected
		if (data == null || data.length == 0) {
			throw new IllegalArgumentException("No scenarios were provided by the DataProvider");
		}
		
		// Log each scenario to confirm it's valid
		for (Object[] scenario : data) {
			if (scenario == null || scenario.length != 2) {
				throw new IllegalArgumentException("Scenario data is malformed");
			}
			PickleWrapper pickleWrapper = (PickleWrapper) scenario[0];
			FeatureWrapper featureWrapper = (FeatureWrapper) scenario[1];
			if (pickleWrapper == null || featureWrapper == null) {
				throw new IllegalArgumentException("PickleWrapper or FeatureWrapper is null in the DataProvider");
			}
			System.out.println("DataProvider scenario: " + pickleWrapper.getPickle().getName());
		}
	
		return data;
	}	

@Override
@Test(dataProvider = "scenarios")
public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
    // Add null checks and logs for debugging
    if (pickleWrapper == null) {
        System.out.println("PickleWrapper is null for scenario: " + featureWrapper.toString());
        throw new IllegalArgumentException("PickleWrapper is null");
    }
    if (featureWrapper == null) {
        System.out.println("FeatureWrapper is null for scenario: " + pickleWrapper.getPickle().getName());
        throw new IllegalArgumentException("FeatureWrapper is null");
    }

    // Log to confirm data is present
    System.out.println("Running scenario: " + pickleWrapper.getPickle().getName() + " in feature: " + featureWrapper.toString());

    //super.runScenario(pickleWrapper, featureWrapper); // Proceed to run the scenario if inputs are valid
}

    // Get WebDriver instance for the current thread
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}
*/
/*
 * @Listeners(TestNgRunner.TestListener.class)
public class TestNgRunner extends AbstractTestNGCucumberTests {

    // Custom TestNG listener to log invocation details
    public static class TestListener implements IInvokedMethodListener {
        @Override
        public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
            System.out.println("Before invocation: " + method.getTestMethod().getMethodName());
        }

        @Override
        public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
            System.out.println("After invocation: " + method.getTestMethod().getMethodName());
        }
    }
}
 */