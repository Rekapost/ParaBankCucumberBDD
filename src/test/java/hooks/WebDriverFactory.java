package hooks;

import java.net.URL;
import java.time.Duration;
import java.util.logging.Level;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.service.ChainPluginService;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);
  
    @Parameters({"browser"})
    public static WebDriver getDriver(String browser) {
        if (driver.get() == null) {
            initializeDriver(browser);
        }
        return driver.get();
    }
    
    private static void initializeDriver(String browser) {
        logger.info("Initializing WebDriver for browser: " + browser);
        if (driver == null) {
            // ChainTest Configuration
            ChainPluginService.getInstance().addSystemInfo("Build#", "1.0");
            ChainPluginService.getInstance().addSystemInfo("Owner Name#", "Reka");    
            
        if(logger==null){
            // Initialize the Log4j logger 
            PropertyConfigurator.configure("src/test/resources/log4j.properties");
            logger.info("Log4j 1.x Logger initialized.");
            // Log4j2 Configuration
            logger.info("Logger initialized and logging preferences set.");             
            }
            
            // Enabling Browser Logging in Selenium
            LoggingPreferences logs = new LoggingPreferences();
            logs.enable(LogType.BROWSER, Level.ALL);
        
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    //WebDriverManager.chromedriver().setup();
                    if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
                        //System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
                    } else {
                        System.setProperty("webdriver.chrome.driver", "./src/test/resources/ChromeDriver/chromedriver.exe");
                    }
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.0.0 Safari/537.36");
                    chromeOptions.addArguments("--disable-logging");
                    //chromeOptions.addArguments("--remote-debugging-port=9223"); // Use a custom port
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--no-sandbox");
                    //chromeOptions.addArguments("--log-level=3");
                    chromeOptions.addArguments("--remote-allow-origins=*"); 
                    chromeOptions.addArguments("--disable-extensions"); 
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--headless"); 
                    
            // Connect to Selenium Grid
            // Set browser options (Example: Chrome)
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");
            capabilities.setCapability("platformName", "LINUX");
            // Merge Options with Capabilities
            capabilities.merge(chromeOptions);
             // Read the selenium.grid.url property from the command line (set by Maven)
            String gridUrl = System.getProperty("selenium.grid.url", "http://localhost:5555/wd/hub");
            // Initialize RemoteWebDriver with the grid URL and Chrome options
            driver.set(new RemoteWebDriver(new URL(gridUrl), capabilities));
            //driver = new RemoteWebDriver(new URL("http://localhost:5555/wd/hub"), capabilities);   


                    //driver.set(new ChromeDriver(chromeOptions));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--disable-logging");
                    firefoxOptions.addArguments("--disable-dev-shm-usage");
                    firefoxOptions.addArguments("--no-sandbox");
                    firefoxOptions.addArguments("--disable-extensions");
                    firefoxOptions.addArguments("--disable-gpu");
                    firefoxOptions.addArguments("disable-software-rasterizer");
                    firefoxOptions.addArguments("disable-features=VizDisplayCompositor");
                    firefoxOptions.addArguments("--headless");
                    //firefoxOptions.addArguments("--remote-allow-origins*=true");                
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--disable-logging");
                    edgeOptions.addArguments("--disable-dev-shm-usage");
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--disable-extensions");
                    edgeOptions.addArguments("--disable-gpu");
                    edgeOptions.addArguments("disable-software-rasterizer");
                    edgeOptions.addArguments("disable-features=VizDisplayCompositor");
                    edgeOptions.addArguments("--headless");
                    //edgeOptions.addArguments("--remote-allow-origins*=true");            
                    driver.set(new EdgeDriver(edgeOptions));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
            driver.get().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
            driver.get().manage().window().maximize();
            deleteAllCookies();
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver for " + browser, e);
            throw new RuntimeException("Failed to initialize WebDriver for " + browser, e);
        }
      }
    }
    private static void deleteAllCookies() {
        driver.get().manage().deleteAllCookies();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logger.info("WebDriver quit and removed.");
        }
    }
}