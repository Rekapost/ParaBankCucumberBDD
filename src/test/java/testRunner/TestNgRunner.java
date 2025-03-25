package testRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;

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
    public void setBrowser(String browser) {
        //browser = browserName;  // Set browser parameter
		TestNgRunner.browser = browser;
        System.out.println("Running tests on: " + browser);
    }
	
/*		@Override
	    @DataProvider(parallel = false)  // Enables parallel execution of scenarios in Cucumber with TestNG.
	    public Object[][] scenarios() {
	        return super.scenarios();
    }
*/

	@Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        Object[][] data = super.scenarios();
        
        // Debugging to check if data is coming in as expected
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("No scenarios were provided by the DataProvider");
        }
        
        // Log each scenario to confirm it's valid
        for (Object[] scenario : data) {
            if (scenario == null || scenario.length != 2) {
                throw new IllegalArgumentException("Scenario data is malformed");
            }
            PickleWrapper pickleWrapper = (PickleWrapper) scenario[0];
            FeatureWrapper featureWrapper = (FeatureWrapper) scenario[1];
            if (pickleWrapper == null || featureWrapper == null) {
                throw new IllegalArgumentException("PickleWrapper or FeatureWrapper is null in the DataProvider");
            }
            System.out.println("DataProvider scenario: " + pickleWrapper.getPickle().getName());
        }
    
        return data;
    }

	@Override
    @Test(dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        super.runScenario(pickleWrapper, featureWrapper);
    }


}
//To run the tests in parallel, you can use the maven-surefire-plugin. Add the following configuration to your pom.xml file:
//mvn clean test
//you can specify the thread count from the command line
//mvn clean test -DthreadCount=4
//This will pick up the maven-surefire-plugin configuration from your pom.xml and execute your Cucumber scenarios in parallel.


