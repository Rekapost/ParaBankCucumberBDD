package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class TransferFundPage {

public WebDriver driver;
WebElement secondOption;

@FindBy(how=How.XPATH,using="//ul//li/a[contains(text(),'Transfer Funds')]")
@CacheLookup
WebElement transferFunds;

@FindBy(how=How.XPATH,using="//form[@id='transferForm']//input[@id='amount']")
@CacheLookup
WebElement amount;

@FindBy(how=How.XPATH,using="//form[@id='transferForm']//select[@id='fromAccountId']")
@CacheLookup
WebElement fromAccount;

@FindBy(how=How.XPATH,using="//form[@id='transferForm']//select[@id='fromAccountId']/option")
@CacheLookup
List<WebElement> fromAccountOption;

@FindBy(how=How.XPATH,using="//form[@id='transferForm']//select[@id='toAccountId']")
@CacheLookup
WebElement toAccount;

@FindBy(how=How.XPATH,using="//form[@id='transferForm']//select[@id='toAccountId']/option")
@CacheLookup
List<WebElement> toAccountOption;

@FindBy(how=How.XPATH,using="//form[@id='transferForm']//input[@type='submit']")
@CacheLookup
WebElement transferButton;

        public TransferFundPage(WebDriver driver)
		{   
			this.driver = driver;
            initializeElements();
		}

        private void initializeElements() {
            PageFactory.initElements(driver, this);
        }

        public void clickTransferFunds(){
            transferFunds.click();
        }

        public void enterAmount(String amountValue){
            amount.sendKeys(amountValue);
        }
        
        public void selectFromAccount(){
            // Select the first option
            System.out.println("Size of fromAccountOption: " + fromAccountOption.size());

             // Check if the list is not empty
                if (!fromAccountOption.isEmpty()) {
                    WebElement firstOption = fromAccountOption.get(0);
                    firstOption.getDomAttribute("value");
                    firstOption.click();
                } else {
                    System.out.println("No options found for From Account.");
                }      
            //fromAccountOption.get(0).click();
            //fromAccount.sendKeys(accountValue);
        }

        /*public void selectFromAccount(String accountValue){
            fromAccount.sendKeys(accountValue);
        }
        public void selectToAccount(String accountValue){
            toAccount.sendKeys(accountValue);
        }*/

        public void selectToAccount(){
            //Select the second option
            System.out.println("Size of toAccountOption: " + toAccountOption.size());
            // Check if the list has at least two options
            if (!toAccountOption.isEmpty()) {
                    secondOption = toAccountOption.get(1);
                    secondOption.getDomAttribute("value");
                    secondOption.click();
                } 
               else {
                System.out.println("Not enough options for To Account.");
            }
            //toAccountOption.get(1).click();
            //fromAccount.sendKeys(accountValue);
        }

        public void clickTransferButton(){
            transferButton.click();
        }
        public String getTransferMessage(){
            return driver.findElement(By.xpath("//div[@id='showResult']//h1[@class='title']")).getText();
        }
        public String getTransferDetails(){
            return driver.findElement(By.xpath("//div[@id='showResult']//p[1]")).getText();
        }
}