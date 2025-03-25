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
				//"com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
				"timeline:test-output-thread/"
           }
		)

public class TestJunitRunner {

}
//mvn -Dtest="testRunner.TestJunitRunner" test
/*
  <!-- Run test through testRunner folder having testrunner files
				for parallel execution -->
To run junit runner file 
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version>        
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
		</plugin>  	
 */ 
/*
 **************   To run junit runner,  remove testng configuration ***********************
 <properties>
    <suiteXmlFile>./src/test/resources/testng/testng.xml,./src/test/resources/testng/paralleltestng.xml</suiteXmlFile> 
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M5</version>
            <configuration>
                <suiteXmlFiles>${suiteXmlFile}</suiteXmlFiles> 
                <parallel>classes</parallel>
                <threadCount>4</threadCount>
                <includes>
                    <include>testRunner/Test*.java</include>
                </includes>
                <systemPropertyVariables>					
                    <!-- <cucumber.filter.tags>@SmokeTest</cucumber.filter.tags> -->
                </systemPropertyVariables>
                <argLine>--add-opens java.base/java.lang=ALL-UNNAMED</argLine>
            </configuration>
        </plugin>
    </plugins>
</build>
*/