package stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import hooks.hook;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.AccountOpenPage;

public class AccountStepDefinition { 
    private final WebDriver driver;
    static Logger loggerload = LogManager.getLogger(AccountStepDefinition.class);
    
    AccountOpenPage newAccountOpenPage;
    public String username;
    public String password;
    public String confPassword;

    public AccountStepDefinition() {
        this.driver = hook.getDriver();  
        newAccountOpenPage = new AccountOpenPage(driver);
    }
@Given("In front page I click {string}")
public void in_front_page_i_click(String string) {
        loggerload.info("Clicking on: " + string);
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
        @When("I click open new account")
        public void i_click_open_new_account() {
            loggerload.info("Opening a new bank account");
        
            // Click on "Open New Account" button
            newAccountOpenPage.clickOpenNewAccount();
            loggerload.info("clicked open new account"); 
        }   

        @Then("I select account type")
        public void i_select_account_type() {
            newAccountOpenPage.getAccountType();
            newAccountOpenPage.selectAccountType("SAVINGS");
            loggerload.info("Selected account type: Savings");
        }
        @Then("I enter deposit ammount")
        public void i_enter_deposit_ammount() {
                newAccountOpenPage.getDepositAmountCondition();
                newAccountOpenPage.inputDepositAmount("1000");
                loggerload.info("Input deposit amount: 1000");
        }
        @Then("I create new account")
        public void i_create_new_account() {
                newAccountOpenPage.openNewAccountForm();
                loggerload.info("Opened new account form");
                Assert.assertEquals(driver.getTitle(), "ParaBank | Open Account");
                loggerload.info("Account created successfully");
        }
        @Then("I get success message for account creation")
        public void i_get_success_message_for_account_creation() {
                newAccountOpenPage.getAccountOpenedMessage();
                newAccountOpenPage.getAccountNumberMessage();
                loggerload.info("Account opened successfully");
        }

}
