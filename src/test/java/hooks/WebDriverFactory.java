package hooks;

import java.time.Duration;
import java.util.logging.Level;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import io.github.bonigarcia.wdm.WebDriverManager;
import testRunner.TestNgRunner;

public class WebDriverFactory {
    // Use ThreadLocal to ensure that each thread gets its own WebDriver instance
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);

    //public static WebDriver getDriver(String browser) {
        public static WebDriver getDriver() {
        // Check if the current thread already has a WebDriver instance
        /*if (driver.get() == null) {
            // Initialize the WebDriver if it's not already initialized for this thread
            initializeDriver(browser);
        }*/
         if (driver.get() == null) {
            if (TestNgRunner.browser == null || TestNgRunner.browser.isEmpty()) {
                throw new IllegalArgumentException("Browser type must be provided in TestNgRunner.browser.");
            } 
            try { 
                // Attempt to initialize the WebDriver based on the browser passed from TestNgRunner
                initializeDriver(TestNgRunner.browser);  
            } catch (Exception e) {
                // Log the error with a message containing the exception details
                logger.error("Error initializing WebDriver for browser: " + TestNgRunner.browser + ". Error message: " + e.getMessage(), e);
            
                // Re-throw the exception to fail the test setup and avoid proceeding with invalid WebDriver
                throw new RuntimeException("WebDriver initialization failed for browser: " + TestNgRunner.browser, e);
            }
        }
        
        // ChainTest Configuration
        //    ChainPluginService.getInstance().addSystemInfo("Build#", "1.0");
        //    ChainPluginService.getInstance().addSystemInfo("Owner Name#", "Reka");    
        
            if(logger==null){
            // Initialize the Log4j2 logger 
            PropertyConfigurator.configure("src/test/resources/log4j2.properties");
            logger.info("Log4j2 Logger initialized.");
            logger.info("Logger initialized and logging preferences set.");             
            }
            
            // Enabling Browser Logging in Selenium
            LoggingPreferences logs = new LoggingPreferences();
            logs.enable(LogType.BROWSER, Level.ALL);
            driver.get().manage().logs().get(LogType.BROWSER).forEach(logEntry -> {
                logger.info(logEntry.getMessage());
            });
            // Log initialization details
            //logger.info("Using WebDriver: " + browser);
            return driver.get();
    }

    private static void initializeDriver(String browser) {
        logger.info("Initializing WebDriver for browser: " + browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                initializeChromeDriver();
                break;
            case "firefox":
                initializeFirefoxDriver();
                break;
            case "edge":
                initializeEdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.get().manage().window().maximize();
        deleteAllCookies();
    }
    
    private static void initializeChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Safari/537.36");
        options.addArguments("--disable-logging");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        
        WebDriverManager.chromedriver().setup();
        driver.set(new ChromeDriver(options));
    }
    
    private static void initializeFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--disable-logging");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        WebDriverManager.firefoxdriver().setup();
        driver.set(new FirefoxDriver(options));
    }
    
    private static void initializeEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-logging");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        WebDriverManager.edgedriver().setup();
        driver.set(new EdgeDriver(options));
    }
    

    private static void deleteAllCookies() {
        driver.get().manage().deleteAllCookies();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();  // Remove the thread-local driver reference
            logger.info("WebDriver initialized: " + driver.get());
            logger.info("WebDriver quit: " + driver.get());
            logger.info("WebDriver closed.");
        }
    }
}
