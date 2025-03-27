package hooks;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import testRunner.TestNgRunner;

public class WebDriverFactory {
    // Use ThreadLocal to ensure that each thread gets its own WebDriver instance
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);
   
    // Static block for logger initialization
    static {
        PropertyConfigurator.configure("src/test/resources/log4j2.properties");
    }

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
            logger.info("WebDriver initialized: " + driver.get().getClass().getSimpleName());
            //logger.info("Using WebDriver: " + browser);
            return driver.get();
    }

    private static void initializeDriver(String browser) {
        logger.info("Initializing WebDriver for browser: " + browser);
        //WebDriver localDriver = null;
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true")); // Default to true if not set
        
        switch (browser.toLowerCase()) {
            case "chrome":
                initializeChromeDriver(isHeadless);
                break;
    
            case "firefox":
                initializeFirefoxDriver(isHeadless);
                break;
    
            case "edge":
                initializeEdgeDriver(isHeadless);
                break;
    
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        //driver.set(localDriver);
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.get().manage().window().maximize();  
        deleteAllCookies();
    }
    
    private static void initializeChromeDriver(boolean isHeadless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Safari/537.36");
        options.addArguments("--disable-logging");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        
        // Enable headless mode if flag is set
        if (isHeadless) {
            options.addArguments("--headless");
        }
    
            // Detect OS and set the ChromeDriver path accordingly
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            // For Linux, use the system-installed chromedriver
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        } else {
            // For Windows, use the local driver in the specified path
            System.setProperty("webdriver.chrome.driver", "./src/test/resources/ChromeDriver/chromedriver.exe");
        }

        // Selenium Grid Configuration
        if (isGridExecution()) {
            try {
                String gridUrl = System.getProperty("selenium.grid.url", "http://localhost:5555/wd/hub");
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setCapability("platformName", "LINUX");
    
                // Initialize RemoteWebDriver for Grid execution
                driver.set(new RemoteWebDriver(new URL(gridUrl), capabilities));
            } catch (Exception e) {
                logger.error("Error initializing RemoteWebDriver for Selenium Grid", e);
                throw new RuntimeException("Selenium Grid setup failed", e);
            }
        } 
        // LambdaTest Configuration
            else if (isLambdaExecution()) {
                //String ltUsername = System.getenv("LT_USERNAME");
                //String ltAccessKey = System.getenv("LT_ACCESS_KEY");           
                // Step 1: LambdaTest Capabilities
                Map<String, Object> ltOptions = new HashMap<>();
                ltOptions.put("username", "rekaharisri");
                ltOptions.put("accessKey", "0UV2Eyfkmupm6epnxh6RK6UDtMOebAibFwtZO1WxuPqeySA0zW");
                //ltOptions.put("user", ltUsername); // Fetch from environment
                //ltOptions.put("accessKey", ltAccessKey);
                //ltOptions.put("username", System.getenv("LT_USERNAME"));
                //ltOptions.put("accessKey", System.getenv("LT_ACCESS_KEY"));       
                ltOptions.put("build", "Parabank-BDD-Cucumber-Framework");
                ltOptions.put("name", "Parabank Functions");
                ltOptions.put("platformName", "Windows 10");
                ltOptions.put("seCdp", true);
                ltOptions.put("selenium_version", "4.23.0");
                ltOptions.put("geoLocation", "US");

            // Step 2: Add LambdaTest capabilities inside ChromeOptions
            options.setCapability("LT:Options", ltOptions);
            options.setCapability("browserVersion", "latest");
            options.setCapability("platformName", "Windows 10");
            options.setCapability("acceptInsecureCerts", true);
       
            // Initialize RemoteWebDriver with authentication in URL
            //String lambdaUrl = "https://" + ltUsername + ":" + ltAccessKey + "@hub.lambdatest.com/wd/hub";
            //driver = new RemoteWebDriver(new URL(lambdaUrl), options);
            // Initialize RemoteWebDriver with LambdaTest options
                try {
                    driver.set(new RemoteWebDriver(new URL("https://hub.lambdatest.com/wd/hub"), options));
                } catch (Exception e) {
                    logger.error("Error initializing RemoteWebDriver for LambdaTest", e);
                    throw new RuntimeException("LambdaTest setup failed", e);
                }           
            }
        // Local WebDriver Configuration
        else {
            WebDriverManager.chromedriver().setup();
            driver.set(new ChromeDriver(options));
        }
    }
    
    private static boolean isGridExecution() {
        // Checks if the execution is on a Selenium Grid
        return Boolean.parseBoolean(System.getProperty("selenium.grid.enabled", "false"));
    }

    
    private static boolean isLambdaExecution() {
        // Checks if the execution is on LambdaTest
        return Boolean.parseBoolean(System.getProperty("selenium.lambdatest.enabled", "false"));
        //return true; // Always return true to enforce LambdaTest execution
    }
    

    private static void initializeFirefoxDriver(boolean isHeadless) {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--disable-logging");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        WebDriverManager.firefoxdriver().setup();
        //System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\FireFoxDriver\\geckodriver.exe");
        driver.set(new FirefoxDriver(options));
    }
 
    private static void initializeEdgeDriver(boolean isHeadless) {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-logging");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--headless");
        WebDriverManager.edgedriver().setup();
        //System.setProperty("webdriver.edge.driver", "src\\test\\resources\\EdgeDriver\\msedgedriver.exe");
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
