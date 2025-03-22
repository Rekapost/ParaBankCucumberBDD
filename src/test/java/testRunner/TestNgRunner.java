package testRunner;
import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/resources/features/04_RequestLoan.feature",
		//tags = "@register or @accountopen",
		//tags= "not (@multiplelogin or @login )",
		//tags= "not @multiplelogin",
		glue = {"stepDefinitions","hooks"}, 
		dryRun = false, 
		monochrome = true,
		plugin = { "pretty",			
		   "html:target/cucumber-reports.html", 
           }
		)

public class TestNgRunner extends AbstractTestNGCucumberTests {
//@DataProvider(parallel = true) enables parallel execution of scenarios in Cucumber with TestNG.
		@Override
	    @DataProvider(parallel = false)
	    public Object[][] scenarios() {
	        return super.scenarios();
    }

/*	@AfterClass
	public void tearDown() {
		//you can quit the webdriver here as well if needed
		Hooks.tearDown();
	}
*/
}


//mvn clean test
//you can specify the thread count from the command line
//mvn clean test -DthreadCount=4
//This will pick up the maven-surefire-plugin configuration from your pom.xml and execute your Cucumber scenarios in parallel.


