package stepDefinitions;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import hooks.hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.LoginPage;
import utilities.ConfigReader;
import utilities.TestData;

public class LoginStepDefinition {
    private final WebDriver driver;
    ConfigReader readConfig = new ConfigReader();
    public String Login_APP_URL = readConfig.getApplicationLoginUrl();
    static Logger loggerload = LogManager.getLogger(LoginStepDefinition.class);
    LoginPage lp;
    public LoginStepDefinition() {
        this.driver = hook.getDriver();  
        lp = new LoginPage(driver);      
    }

    @Given("I am in LoginPage")
    public void i_am_in_login_page() {
        lp.logout();
        System.out.println("In Login Page");
        loggerload.info("I am in Login Page");
        driver.get(Login_APP_URL);
    }

    @When("I enter the registered username {string} and password {string}")
    public void i_enter_the_registered_username_and_password(String string, String string2) {
    // Retrieve the username and password from the shared TestData class
    String username = TestData.username;
    String password = TestData.password;

    // Log the credentials for reference
    loggerload.info("Entering credentials: " + username + " / " + password);

    // Use the credentials to log in
    lp.username(username);
    lp.password(password);

    loggerload.info("Entered credentials successfully.");
}
    @When("Click login")
    public void click_login() {
        loggerload.info("click login");
        if (!driver.getTitle().contains("Accounts Overview")) {  // Check if already logged in
            loggerload.info("Check if already logged in, if not, then login");
            lp.login();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.titleContains("Accounts Overview"));
            loggerload.info("Successfully logged in and navigated to Accounts Overview.");
        } else {
            System.out.println("User is already logged in, skipping login.");
            loggerload.info("User is already logged in, skipping login.");
        }
    }
    @When("User is logged in to the website")
    public void user_is_logged_in_to_the_website() {
        loggerload.info("logged in to Para Bank");     
        System.out.println("logged in to Para Bank:");
        System.out.println(driver.getTitle());
    }
    @Then("I validate the credentials")
    public void i_validate_the_credentials() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.titleContains("Accounts Overview"));

        if (driver.getTitle().equals("ParaBank | Accounts Overview")) {
            System.out.println("Successful login");
            loggerload.info("Successful login");
        } else {
            loggerload.error("Failed to login, page title does not match!");
            Assert.fail("Login failed, incorrect page title: " + driver.getTitle());
        }
    }  
}
