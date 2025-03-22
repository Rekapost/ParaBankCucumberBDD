package stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import hooks.hook;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pageObjects.AccountOpenPage;
import pageObjects.TransferFundPage;

public class TransferFundsStepDefinition {
    private final WebDriver driver;
    static Logger loggerload = LogManager.getLogger(TransferFundsStepDefinition.class);
    AccountOpenPage newAccountOpenPage;
    TransferFundPage transferFundPage;

    public TransferFundsStepDefinition() {
        this.driver = hook.getDriver();  
        transferFundPage = new TransferFundPage(driver);  
        newAccountOpenPage = new AccountOpenPage(driver);    
    }
    @Given("I click TransferFunds")
    public void i_click_transfer_funds() {
        loggerload.info("Clicking on Transfer Funds");
        transferFundPage.clickTransferFunds();
    }
    @Then("I enter amount to transfer")
    public void i_enter_amount_to_transfer() {
        loggerload.info("Enter amount to transfer");
        transferFundPage.enterAmount("800");
    }
    @Then("I select From account and To account")
    public void i_select_from_account_and_to_account() {
        loggerload.info("Selecting From and To account");
        transferFundPage.selectFromAccount();
        transferFundPage.selectToAccount();
    }
    @Then("I submit Transfer button")
    public void i_submit_transfer_button() {
        loggerload.info("Clicking on Transfer button");
        transferFundPage.clickTransferButton();
    }  
    @Then("I should see the success message")
    public void i_should_see_the_success_message() {
        loggerload.info("Verifying Transfer success message");
        String transferMessage = transferFundPage.getTransferMessage();
        String transferDetails = transferFundPage.getTransferDetails();
        System.out.println("Transfer Message: " + transferMessage);
        System.out.println("Transfer Details: " + transferDetails);
    }
    @And("I should see the new balance in the account")
    public void i_should_see_the_new_balance_in_the_account(){
            newAccountOpenPage.accountOverview();
            newAccountOpenPage.clickAccountOverview();
            newAccountOpenPage.data();
            newAccountOpenPage.clickAccountDetails();
            newAccountOpenPage.getAccountDetails();
            newAccountOpenPage.getTransactionDetails();
           //newAccountOpnePage.selectActivityMonthPeriod();
           //newAccountOpenPage.selectActivityTransactionType();
           // newAccountOpenPage.getTransactionDetails(); //verifying the transaction details after the transfer operation
        }
}
