package hooks;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
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

import com.aventstack.chaintest.service.ChainPluginService;

import io.github.bonigarcia.wdm.WebDriverManager;
//import utilities.ConfigReader;

public class WebDriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);
    //private static ConfigReader readConfig = new ConfigReader();

    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {
            initializeDriver(browser);
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
            
            return driver.get();
    }

    private static void initializeDriver(String browser) {
        logger.info("Initializing WebDriver for browser: " + browser);

        switch (browser.toLowerCase()) {
            case "chrome":
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
            //options.addArguments("--headless"); 
            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
                //System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
            } else {
                System.setProperty("webdriver.chrome.driver", "./src/test/resources/ChromeDriver/chromedriver.exe");
            }  
                driver.set(new ChromeDriver(options));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver.set(new FirefoxDriver(firefoxOptions));
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                driver.set(new EdgeDriver(edgeOptions));
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.get().manage().window().maximize();  
        deleteAllCookies();

    }

    private static void deleteAllCookies() {
        driver.get().manage().deleteAllCookies();
    }

    public static void closeDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logger.info("WebDriver closed.");
        }
    }
}
