package testRunner;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)  
@CucumberOptions(
		features = "src/test/resources/features/",
		//tags = "@login or @multiplelogin or @sanity or @smoke or @regression",
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

public class JunitRunner {

}

/*
  <!-- Run test through testRunner folder having testrunner files
				for parallel execution -->
				
				<configuration> 
					<parallel>methods</parallel>  <!-- or classes / both -->
					<threadCount>4</threadCount>  <!-- Number of threads to use -->
					<includes>
						<include>testRunner/*.java</include>
					</includes>
					<systemPropertyVariables>					
			            <!-- <cucumber.filter.tags>@SmokeTest</cucumber.filter.tags> -->
					</systemPropertyVariables>
					<argLine>--add-opens java.base/java.lang=ALL-UNNAMED</argLine>
				</configuration>    	
 */ 
