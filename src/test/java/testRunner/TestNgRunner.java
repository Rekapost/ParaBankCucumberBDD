package testRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/resources/features/",
		//tags = "@register or @accountopen",
		//tags= "not (@multiplelogin or @login )",
		tags= "not @multiplelogin",
		glue = {"stepDefinitions","hooks"}, 
		dryRun = false, 
		monochrome = true,
		plugin = { "pretty",			
		    "html:target/cucumber-reports.html", 
		    "json:target/cucumber-reports.json",
		    "junit:target/cucumber-reports.xml",
			//"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",	
			"com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
			"timeline:test-output-thread/"
           }
		)

public class TestNgRunner extends AbstractTestNGCucumberTests {

	public static String browser;

    @BeforeClass
    @Parameters({"browser"})
    public void setBrowser(String browserName) {
        browser = browserName;  // Set browser parameter
        System.out.println("Running tests on: " + browser);
    }
		
		@Override
	    @DataProvider(parallel = false)  // Enables parallel execution of scenarios in Cucumber with TestNG.
	    public Object[][] scenarios() {
	        return super.scenarios();
    }

}
//To run the tests in parallel, you can use the maven-surefire-plugin. Add the following configuration to your pom.xml file:
//mvn clean test
//you can specify the thread count from the command line
//mvn clean test -DthreadCount=4
//This will pick up the maven-surefire-plugin configuration from your pom.xml and execute your Cucumber scenarios in parallel.


