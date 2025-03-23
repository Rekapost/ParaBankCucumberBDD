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
    //private static ConfigReader readConfig = new ConfigReader();

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
        WebDriver localDriver = null;
        switch (browser.toLowerCase()) {
            case "chrome":
            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
                //System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
            } else {
                System.setProperty("webdriver.chrome.driver", "./src/test/resources/ChromeDriver/chromedriver.exe");
            } 
            ChromeOptions options = new ChromeOptions();
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Safari/537.36");
            options.addArguments("--disable-logging");
            //chromeOptions.addArguments("--remote-debugging-port=9223"); // Use a custom port
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            //chromeOptions.addArguments("--log-level=3");
            options.addArguments("--remote-allow-origins=*"); 
            options.addArguments("--disable-extensions"); 
            options.addArguments("--disable-gpu");
            options.addArguments("--headless"); 
            driver.set(new ChromeDriver(options));
            //localDriver = new ChromeDriver(options);
            break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Safari/537.36");
                firefoxOptions.addArguments("--disable-logging");
                //firefoxOptions.addArguments("--remote-debugging-port=9223"); // Use a custom port
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                firefoxOptions.addArguments("--no-sandbox");
                //firefoxOptions.addArguments("--log-level=3");
                firefoxOptions.addArguments("--remote-allow-origins=*"); 
                firefoxOptions.addArguments("--disable-extensions"); 
                firefoxOptions.addArguments("--disable-gpu");
                firefoxOptions.addArguments("--headless");
                //System.setProperty("webdriver.gecko.driver", "./src/test/resources/FirefoxDriver/geckodriver.exe");
                driver.set(new FirefoxDriver(firefoxOptions));
                //localDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup(); //  Automatically downloads the correct version
                EdgeOptions edgeOptions = new EdgeOptions();
                //edgeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Safari/537.36");
                edgeOptions.addArguments("--disable-logging");
                //edgeOptions.addArguments("--remote-debugging-port=9223"); // Use a custom port
                //edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--no-sandbox");
                //edgeOptions.addArguments("--log-level=3");
                //edgeOptions.addArguments("--remote-allow-origins=*"); 
                edgeOptions.addArguments("--disable-extensions"); 
                //edgeOptions.addArguments("--disable-gpu");
                edgeOptions.addArguments("--headless");
                //System.setProperty("webdriver.edge.driver", "./src/test/resources/EdgeDriver/msedgedriver.exe");              
                //driver.quitDriver();
                //driver.set(new EdgeDriver());
                driver.set(new EdgeDriver(edgeOptions));
                //localDriver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Set the WebDriver instance for the current thread
        //driver.set(localDriver);
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.get().manage().window().maximize();  
        deleteAllCookies();
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
