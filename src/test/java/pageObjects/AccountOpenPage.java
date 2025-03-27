package pageObjects;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountOpenPage {
    public WebDriver driver;

    @FindBy(how=How.XPATH,using="//*[@id='leftPanel']/ul/li[1]/a")
	@CacheLookup
	WebElement openNewAccount;
    
    @FindBy(how=How.XPATH,using="//*[@id='leftPanel']/ul/li[2]/a")
	WebElement accountOverview;

    @FindBy(how=How.XPATH,using="//*[@id='rightPanel']//div//tbody/tr/td")
    @CacheLookup
 	List<WebElement> accountData; 


    @FindBy(how=How.XPATH,using="//div[@id='openAccountForm']//form/p[1]/b")
    @CacheLookup
    WebElement accountType;

    @FindBy(how=How.XPATH,using="//div[@id='openAccountForm']//form/p[2]/b")
    @CacheLookup
    WebElement depositAmountCondition;

    @FindBy(how=How.XPATH,using="//*[@id='fromAccountId']")
    @CacheLookup
    WebElement depositAmount;

    @FindBy(how=How.XPATH,using="//*[@id='openAccountForm']/form/div/input")
    @CacheLookup
    WebElement clickOpenNewAccount;

    @FindBy(how=How.XPATH,using="//*[@id='openAccountResult']/h1")
    @CacheLookup
    WebElement accountOpenedMessage;

    @FindBy(how=How.XPATH,using="//*[@id='openAccountResult']/p")
    @CacheLookup
    WebElement accountNumberMessage;

    @FindBy(how=How.XPATH,using="//div[@id='showOverview']//tr/td/a")
    @CacheLookup
    WebElement clickAccountDetails;

    @FindBy(how=How.XPATH,using="//div[@id='accountDetails']//tr/td")
    @CacheLookup
    List<WebElement> accountDetails;

    @FindBy(how=How.XPATH,using="//table[@id='transactionTable']//tr/td")
    @CacheLookup
    List<WebElement> TransactionDetails;

    @FindBy(how=How.XPATH,using="//form[@id='activityForm']//td/select[@id='month']/option[1]")
    @CacheLookup
    WebElement activityMonthPeriod;

    @FindBy(how=How.XPATH,using="//form[@id='activityForm']//td/select[@id='transactionType']/option[1]")
    @CacheLookup
    WebElement activityTransactionType;


    public AccountOpenPage(WebDriver driver)
		{   
			this.driver = driver;
            initializeElements();
		}

        private void initializeElements() {
            PageFactory.initElements(driver, this);
        }

        public void accountOverview() {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='leftPanel']/ul/li[2]/a")));  // Wait until element is present in the DOM
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='leftPanel']/ul/li[2]/a")));  // Wait until element is visible
        }
        
        public void clickAccountOverview(){
            accountOverview.click();   
         }
         
        public void data(){
            for(WebElement element: accountData){
                System.out.println(element.getText());  
            }
        }

        public void clickOpenNewAccount(){
             openNewAccount.click();
         }  

         public String getAccountMessage() {
            WebElement messageElement = driver.findElement(By.id("newAccountId"));
            return messageElement.getText();
        }

        public String getAccountType() {
            return accountType.getText();
        }

        public void selectAccountType(String type){
            Select selectAccountType = new Select(driver.findElement(By.id("type")));
            selectAccountType.selectByVisibleText(type);     //0 CHECKING   1 SAVINGS
        }

        public String getDepositAmountCondition() {
            return depositAmountCondition.getText();
        }

        public void inputDepositAmount(String amount){
            depositAmount.sendKeys(amount);
        }

        public void openNewAccountForm(){
            clickOpenNewAccount.click();
        }

        public String getAccountOpenedMessage() {
            return accountOpenedMessage.getText();
        }
        public String getAccountNumberMessage() {
            return accountNumberMessage.getText();
        }

        public void clickAccountDetails(){
            clickAccountDetails.click();
        }

        public void getAccountDetails(){
            for(WebElement element: accountDetails){
                System.out.println(element.getText());  
            }
        }

        public void getTransactionDetails(){
            for(WebElement element: TransactionDetails){
                System.out.println(element.getText());  
            }
        }

        public void selectActivityMonthPeriod(){
            activityMonthPeriod.click();
        }

        public void selectActivityTransactionType(){
            activityTransactionType.click();
        }
}
