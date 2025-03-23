package pageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
    public static WebDriver driver;
    private WebDriverWait wait;

    @FindBy(how=How.XPATH,using="//input[@id='customer.firstName']")
	@CacheLookup
	WebElement firstname;

    @FindBy(how=How.XPATH,using="//input[@id='customer.lastName']")
	@CacheLookup
	WebElement lastname;

    @FindBy(how=How.XPATH,using="//input[@id='customer.address.street']")
	@CacheLookup
	WebElement address;

    @FindBy(how=How.XPATH,using="//input[@id='customer.address.city']")
	@CacheLookup
	WebElement city;

    @FindBy(how=How.XPATH,using="//input[@id='customer.address.state']")
	@CacheLookup
	WebElement state;

    @FindBy(how=How.XPATH,using="//input[@id='customer.address.zipCode']")
	@CacheLookup
	WebElement zipcode;

    @FindBy(how=How.XPATH,using="//input[@id='customer.phoneNumber']")
	@CacheLookup
	WebElement phone;

    @FindBy(how=How.XPATH,using="//input[@id='customer.ssn']")
	@CacheLookup
	WebElement ssn;

    @FindBy(how=How.XPATH,using="//input[@id='customer.username']")
	@CacheLookup
	WebElement username; 

    @FindBy(how=How.XPATH,using="//input[@id='customer.password']")
	WebElement password; 

    @FindBy(how=How.XPATH,using="//input[@id='repeatedPassword']")
	WebElement confirm; 

    @FindBy(how=How.XPATH,using="//input[@type='submit']")
    @CacheLookup
    WebElement register; 

    @FindBy(how=How.XPATH,using="//*[@id='rightPanel']/h1[@class='title']")
    @CacheLookup
    WebElement welcomeMessage;

    @FindBy(how=How.XPATH,using="//*[@id='rightPanel']/p")
    @CacheLookup
    WebElement accountCreatedMessage;

    @FindBy(id = "//*[@id='rightPanel']/p") 
    private WebElement errorMessage;

	//using constructor to initialze webelements in pagefactory	
        public RegisterPage(WebDriver driver)
            {   
                this.driver = driver;
                wait = new WebDriverWait(driver, 10);
                PageFactory.initElements(driver, this);
            }

        public void enterFirstname(String fname){
            firstname.sendKeys(fname);
        }
        
        public void enterLastname(String lname){
            lastname.sendKeys(lname);
        }
        
        public void enterAddress(String addr){
            address.sendKeys(addr);
        }
        
        public void enterCity(String cityName){
            city.sendKeys(cityName);
        }
        
        public void enterState(String stateName){
            state.sendKeys(stateName);
        }
        
        public void enterZipcode(String zip){
            zipcode.sendKeys(zip);
        }
        
        public void enterPhone(String phoneNumber){
            phone.sendKeys(phoneNumber);
        }
        
        public void enterSSN(String ssnNumber){
            ssn.sendKeys(ssnNumber);
        }

        public void enterUsername(String uname) {
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='customer.username']")));
            usernameField.clear();
            usernameField.sendKeys(uname);
        }

        public void enterPassword(String pwd) {
           try{
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='customer.password']")));
            passwordField.clear();
            passwordField.sendKeys(pwd);
            }catch(Exception e){
                System.out.println("Password field not found. Error: " + e.getMessage());
            }
        }

        public void enterConfirmPassword(String cpwd) {
            try{
            WebElement confirmPasswordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='repeatedPassword']")));
            confirmPasswordField.clear();
            confirmPasswordField.sendKeys(cpwd);
            }catch(Exception e){
                System.out.println("Confirm Password field not found. Error: " + e.getMessage());
            }
        }
        public WebElement clickSubmitButton() {
            return driver.findElement(By.xpath("//input[@type='submit']"));
        }
        public String getMessage(){
            String message = welcomeMessage.getText();
            System.out.println("Welcome message is: " + message);
            return message;
        }
 
        public String getAccountCreatedMessage(){
            String accountCreateMess= accountCreatedMessage.getText();
               System.out.println("Welcome message is: "+accountCreateMess);
                 return accountCreateMess;
         }
    }

