package pageObjects;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// Page Object for RequestLoanPage
public class RequestLoanPage {
    public WebDriver driver;
    static Logger loggerload = LogManager.getLogger(RequestLoanPage.class);
    
    @FindBy(how=How.XPATH,using="//ul/li/a[text()='Request Loan']")
    @CacheLookup
    WebElement clickRequestLoan;

    @FindBy(how=How.XPATH,using="//form//tr/td//input[@id='amount']")
    @CacheLookup
    WebElement loanAmount;

    @FindBy(how=How.XPATH,using="//form//tr/td//input[@id='downPayment']")
    @CacheLookup
    WebElement downPayment;

    @FindBy(how=How.XPATH,using="//form//tr/td//select[@id='fromAccountId']/option")
    @CacheLookup
    List<WebElement> fromAccount;

    @FindBy(how=How.XPATH,using="//form//tr/td//input[@type='button']")
    @CacheLookup
    WebElement applyButton;

    @FindBy(how=How.XPATH,using="//div[@id='requestLoanResult']/h1")
    @CacheLookup
    WebElement message;

    @FindBy(how=How.XPATH,using="//div[@id='requestLoanResult']//tr/td")
    @CacheLookup
    List<WebElement> loanDetails;

    @FindBy(how=How.XPATH,using="//div[@id='loanRequestApproved']/p")
    @CacheLookup
    List<WebElement> loanApproved;
    
    @FindBy(how=How.XPATH,using="//p[@class='error']")
    @CacheLookup
    WebElement errorMessage;

    @FindBy(how=How.XPATH,using="//div[@id='requestLoanResult']//tr/td[@id='loanStatus']")
    @CacheLookup
    WebElement loanStatus;

    public RequestLoanPage(WebDriver driver){   
        this.driver = driver;
        initializeElements();
    }

    private void initializeElements() {
        PageFactory.initElements(driver, this);
    }

    public void clickRequestLoan(){
        clickRequestLoan.click();
        loggerload.info("Clicked on Request Loan");
    }

    public void enterLoanAmount(String amount){
        loanAmount.sendKeys(amount);
        loggerload.info("Entered loan amount : "+ amount);
    }

    public void enterDownpaymentAmount(String amount){
        downPayment.sendKeys(amount);
        loggerload.info("Entered down payment amount : "+ amount);
    }

    public void fromAccount(){   
        // Print the options
        System.out.println("Available options:");
        /*for (WebElement option : fromAccount)) {
            System.out.println(option.getText());  // Prints the text of the option
        }
        */
        // Click on the first option
        if (!fromAccount.isEmpty()) {
            WebElement firstOption = fromAccount.get(0);
            firstOption.click();  // Click the first option
            System.out.println("Clicked on the first option: " + firstOption.getText());
            loggerload.info("Clicked on the first option: " + firstOption.getText());
        } else {
            System.out.println("No options found.");
            loggerload.info("No options found.");
        }

                /* if(account.getText().equals("800")){
                        account.click();
                        break;
                    } */
        }

    public void clickApplyNowButton(){
        applyButton.click();
        loggerload.info("Clicked on Apply Now button");
    }

    public void getLoanMessage(){    
        System.out.println("Loan Message: " + message.getText());
        loggerload.info("Loan Message: " + message.getText());
    }

    public void getLoanDetails(){
        for(WebElement element: loanDetails){
            System.out.println(element.getText());  
            loggerload.info(element.getText());
        }
    }

    public void loanApproved(){
        for(WebElement element: loanApproved){
            String info= element.getText();
            System.out.println(info);
            loggerload.info(info);
        }
    }
    
    public void errorMessage(){
        System.out.println("Error Message: " + errorMessage.getText());
        loggerload.info("Error Message: " + errorMessage.getText());
    }

    public String loanStatus(){
        System.out.println("Loan Status: " + loanStatus.getText());
        loggerload.info("Loan Status: " + loanStatus.getText()); 
        if(loanStatus.getText().equals("Approved")){
            System.out.println("Loan Approved");
            loggerload.info("Loan Approved");
        }
        else{
            System.out.println("Loan Denied");
            loggerload.info("Loan Denied");
        }
        return loanStatus.getText();
    }
}
