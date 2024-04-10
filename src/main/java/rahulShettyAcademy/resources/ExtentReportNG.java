package rahulShettyAcademy.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {

	public static ExtentReports getReporterObject() {
		String path = System.getProperty("user.dir")+"\\reports\\index.html";		
		// configuring report settings
		ExtentSparkReporter reporter =new ExtentSparkReporter(path); // setting path 
		
		reporter.config().setDocumentTitle("Test Results"); // setting document title
		reporter.config().setReportName("Web Automation Results"); // setting report name
		
		ExtentReports extent = new ExtentReports(); // creating new object
		extent.attachReporter(reporter); // attaching reporter 
		extent.setSystemInfo("Tester", "Megha");// setting tester name in system info
		
		return extent;
	}
	
	
}
