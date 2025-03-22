package stepDefinitions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import hooks.hook;
import io.cucumber.java.en.Then;
import pageObjects.RequestLoanPage;

public class RequestLoanStepDefinition{
    private final WebDriver driver;
    static Logger loggerload = LogManager.getLogger(RequestLoanStepDefinition.class);
    RequestLoanPage requestLoanPage;

    public RequestLoanStepDefinition() {
        this.driver = hook.getDriver();   
        requestLoanPage = new RequestLoanPage(driver); 
    }

        @Then("I click request Loan")
        public void i_click_request_loan() {
            loggerload.info("Clicking on Request Loan");
            requestLoanPage.clickRequestLoan();          
        }

        @Then("I enter loan amount")
        public void i_enter_loan_amount() {
            loggerload.info("Entering loan amount");
            requestLoanPage.enterLoanAmount("10000");
        }
        @Then("I enter down payment amount")
        public void i_enter_down_payment_amount() {
            loggerload.info("Entering down payment amount");
            requestLoanPage.enterDownpaymentAmount("1000");
        }
        @Then("I select From Account")
        public void i_select_from_account() {
            loggerload.info("Selecting From Account");
            requestLoanPage.fromAccount();
        }
        @Then("I submit loan request")
        public void i_submit_loan_request() {
            
            loggerload.info("Submitting loan request");
            requestLoanPage.clickApplyNowButton();           
    }

    @Then("I should see the success message for loan approved")
    public void i_should_see_the_success_message_for_loan_approved() {
        if(requestLoanPage.loanStatus().equals("Approved")){
            loggerload.info("Loan request approved");
            requestLoanPage.getLoanMessage();
            requestLoanPage.getLoanDetails();
            requestLoanPage.loanStatus();
            requestLoanPage.loanApproved();          
        } 
        else {
            loggerload.info("Loan request denied");
            requestLoanPage.getLoanMessage();     
            requestLoanPage.getLoanDetails();
            requestLoanPage.loanStatus();
            requestLoanPage.loanApproved();    
    }         
    }
}