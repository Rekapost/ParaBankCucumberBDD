package pageObjects;
import java.time.Duration;
import utilities.BrowserUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	public  static WebDriver driver;	
	private WebDriverWait wait; 
	BrowserUtility browserUtility;
		//using constructor to initialze webelements in pagefactory	
		public LoginPage(WebDriver driver)
		{   
			this.driver = driver;
			browserUtility = new BrowserUtility(driver);
			PageFactory.initElements(driver, this);
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		}
	
//  What is CacheLookup in Selenium?
//	@CacheLookup, as the name suggests helps us control when to cache a WebElement and when not to. 
//	This annotation, when applied over a WebElement, instructs Selenium to keep a cache of the WebElement 
//	instead of searching for the WebElement every time from the WebPage. This helps us save a lot of time.
	
	@FindBy(how=How.XPATH,using="//input[@name='username']")
	@CacheLookup
	WebElement username;

	@FindBy(how=How.XPATH,using="//input[@name='password']")
	@CacheLookup
	WebElement password;
	
	@FindBy(how=How.XPATH,using="//input[@value='Log In']")
	@CacheLookup   
	WebElement loginButton;

	@FindBy(how=How.XPATH,using="//*[@id='leftPanel']/ul/li[8]/a")
	@CacheLookup
	WebElement logoutButton;
	

	public void logout(){
		//logoutButton.click();
		browserUtility.clickOn(logoutButton);
	}
	public void username(String uname)
	{
		
		WebElement usernamelField = wait.until(ExpectedConditions.visibilityOf(username));
		//WebElement usernamelField = wait.until(ExpectedConditions.presenceOfElementLocated(username));
		usernamelField.sendKeys(uname);
	}
	
	public void password(String pwd)
		{
		password.sendKeys(pwd);
		}
	
	public void login()
		{	
        WebElement login =wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		browserUtility.clickOn(login);
		//login.click();		
	  }
	
	public void loginDDT(String userName, String passWord) throws InterruptedException {			
		username.clear();
		username.sendKeys(userName);
		password.clear();
		password.sendKeys(passWord);
		//loginButton.click();
		browserUtility.clickOn(loginButton);
	}
}
