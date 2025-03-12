package utilities;
//Listener class used to generate extent reports
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import tech.grasshopper.pdf.extent.ExtentPDFCucumberReporter;

public class Reporting extends TestListenerAdapter{
	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;

     @Override
	public void onStart(ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
		String repName="Test-Report-"+timeStamp+".html";		
		htmlReporter=new ExtentHtmlReporter("test-output/"+repName);
		
		try {
			htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/src/test/resources/extent_config.xml");
		} catch (IOException e) {
			System.out.println(e);
		}
		
		extent=new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("user", "Reka");
		
		htmlReporter.config().setDocumentTitle("Bdd Cucumber Test Project");//title of report
		htmlReporter.config().setReportName("Functional Test Automation Report");//name of report
		//htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
	}
        @Override
		public void onTestSuccess(ITestResult tr)
	{
	  logger=extent.createTest(tr.getName());//create new entry in the report
	  logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));//Send passed info
	}
        @Override
		public void onTestFailure(ITestResult tr)
	{
		logger=extent.createTest(tr.getName());//create new entry in the report
		logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
		
		//String screenshotPath=System.getProperty("user.dir")+"/Screenshots/"+tr.getName()+".png";  // screenshot with name of test case
		String screenshotPath="Screenshots/Screenshots"+tr.getName()+".png";
		File f = new File(screenshotPath);
		
		if(f.exists())
			{
			logger.fail("Screenshot is below:"+logger.addScreenCaptureFromPath(screenshotPath));
			
		}
	}
        @Override
		public void onTestSkipped(ITestResult tr)
		{
			logger=extent.createTest(tr.getName());
			logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
		}
		
        @Override
		public void onFinish(ITestContext testContext)
		{
			extent.flush();
			
		}
}