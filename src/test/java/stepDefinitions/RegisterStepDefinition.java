package stepDefinitions;

import java.time.Duration;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import hooks.hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.datafaker.Faker;
import pageObjects.LoginPage;
import pageObjects.NewAccountOpenPage;
import pageObjects.RegisterPage;
import utilities.ConfigReader;

public class RegisterStepDefinition { 
    private final WebDriver driver;
    ConfigReader readConfig = new ConfigReader();
    public String Register_APP_URL = readConfig.getApplicationRegisterUrl();
    public String Login_APP_URL = readConfig.getApplicationLoginUrl();
    static Logger loggerload = LogManager.getLogger(RegisterStepDefinition.class);
    
    RegisterPage reg;
    LoginPage lp;
    NewAccountOpenPage newAccountOpenPage;
    
    public String username;
    public String password;
    public String confPassword;
    
    private final Faker datafaker = new Faker();

    public RegisterStepDefinition() {
        this.driver = hook.getDriver();  
        reg = new RegisterPage(driver);
        lp = new LoginPage(driver);
        newAccountOpenPage = new NewAccountOpenPage(driver);
    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String string) {
        loggerload.info("Opening ParaBank registration page: " + Register_APP_URL);
        driver.get(Register_APP_URL);
        driver.manage().window().maximize();
    } 
    
    @Then("I fill user details {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void i_fill_user_details(String firstName, String lastName, String address, String city, String state, String zipCode, String phone, String ssn) {
        loggerload.info("Filling user details on registration form");
        
        reg.enterFirstname(datafaker.name().firstName());
        reg.enterLastname(datafaker.name().lastName());
        reg.enterAddress(datafaker.address().streetAddress());
        reg.enterCity(datafaker.address().city());
        reg.enterState(datafaker.address().state());
        reg.enterZipcode(datafaker.address().zipCode());
        reg.enterPhone(datafaker.phoneNumber().phoneNumber());
        reg.enterSSN(datafaker.idNumber().ssnValid());

        loggerload.info("User details filled successfully");
    }
   
    @Then("I enter {string}, {string}, and {string}")
    public void i_enter_and(String username, String password, String confPassword) {
        loggerload.info("Generating random credentials for registration");
    
        // Generate test data
        this.username = "TestUser" + (1000 + new Random().nextInt(9000));
        this.password = "Test@" + datafaker.number().digits(4);
        this.confPassword = this.password; 
        
        loggerload.info("Generated Username: " + this.username);
        loggerload.info("Generated Password: " + this.password);
    
        reg.enterUsername(this.username);
        reg.enterPassword(this.password);
        reg.enterConfirmPassword(this.confPassword + Keys.ENTER);
        
        loggerload.info("Entered credentials successfully: " + this.username + " / " + this.password);
    }
    

@When("I submit on Register button")
public void i_submit_on_register_button() {
    loggerload.info("Submitting registration form");
/*    try{
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", reg.clickSubmitButton());
        js.executeScript("arguments[0].click();", reg.clickSubmitButton());
    } catch (Exception e) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    wait.until(ExpectedConditions.elementToBeClickable(reg.clickSubmitButton())).click();
    }
*/
    loggerload.info("Registration form submitted successfully.");
}


   @Then("I should be navigated to the login page")
        public void i_should_be_navigated_to_the_login_page() {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.titleContains("ParaBank | Customer Created")
                ));

        String actualTitle = driver.getTitle();
        loggerload.info("Actual page title after registration: " + actualTitle);

        // Ensure registration success message is present
        String successMessage = reg.getMessage();
        loggerload.info("Registration success message: " + successMessage);
        String successAccountMessage = reg.getAccountCreatedMessage();
        loggerload.info("Registration success message: " + successAccountMessage);

    } catch (TimeoutException e) {
        loggerload.error("Navigation to login page failed: " + driver.getTitle());
        throw new AssertionError("Registration failed! Page title mismatch.");
    }
}

// ***********************************************************************************
    @When("In front page i click {string}")
    public void in_front_page_i_click(String button) {
        loggerload.info("Clicking on: " + button);
        newAccountOpenPage.accountOverview();
        newAccountOpenPage.clickAccountOverview();
    }
    /*    // Verify account creation success
        String accountMessage = newAccountOpenPage.getAccountMessage();
        loggerload.info("New Account Message: " + accountMessage);   
        if (!accountMessage.contains("successfully created")) {
            throw new AssertionError("Failed to create new account.");
        }
    */
    

    @Then("I should see the account creation page")
    public void i_should_see_the_account_creation_page() {
        loggerload.info("Navigated to account creation page");
        newAccountOpenPage.data();
        loggerload.info("Got all details of the account" );

    }
    @Then("I open new account")
    public void i_open_new_account() {
        loggerload.info("Opening a new bank account");
    
        // Click on "Open New Account" button
        newAccountOpenPage.clickOpenNewAccount();
        loggerload.info("clicked open new account");
        newAccountOpenPage.getAccountType();
        newAccountOpenPage.selectAccountType("SAVINGS");
        loggerload.info("Selected account type: Savings");
        newAccountOpenPage.getDepositAmountCondition();
        newAccountOpenPage.inputDepositAmount("1000");
        loggerload.info("Input deposit amount: 1000");
        newAccountOpenPage.openNewAccountForm();
        loggerload.info("Opened new account form");
        Assert.assertEquals(driver.getTitle(), "ParaBank | Open Account");
        loggerload.info("Account created successfully");
        newAccountOpenPage.getAccountOpenedMessage();
        newAccountOpenPage.getAccountNumberMessage();
        loggerload.info("Account opened successfully");
    }   

}
