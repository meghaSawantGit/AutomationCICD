package rahulShettyAcademy.testComponents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulShettyAcademy.pageObjects.LandingPage;

public class BaseTest {
	public WebDriver driver;
	public LandingPage landingPageObj;

	public void initializeWebDriver() throws IOException {

		// Properties Class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "\\src\\main\\java\\rahulShettyAcademy\\resources\\Globaldata.properties");
		prop.load(fis);
		
		/* The following code reads browser field value and executes tests in appropriate browser accordingly.
		 * We provide value in two ways:
		 * 
		 * 1) using .properties file - prop.getProperty("browser")
		 * 2) using terminal while executing tests(maven commands -D) - System.getProperty("browser")
		 * 
		 * the following code will check for terminal value first, if it is present it will override 
		 * value in .properties file
		 * */
		
		String browserName = System.getProperty("browser")!= null ? System.getProperty("browser") : prop.getProperty("browser");
		//String browserName =  prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			
			if(browserName.contains("headless")) {
				options.addArguments("headless");
			}
			
			driver = new ChromeDriver(options);
			
			/* when running in headless mode, browser will not open. tests will be running in background.
			 * in such situation we have to tell driver to set the screen size to full screen
			 * for execution so that all elements are visible. for this we need the following line.
			 * 
			 * for headded mode we use: driver.manage().window().maximize();			 * */
			
			driver.manage().window().setSize(new Dimension(1440,900));
		} else if (browserName.equalsIgnoreCase("firefox")) {
			// code for firefox
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			// code for edge
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		//return driver;
	}
	
	public List<HashMap<String, String>> readFileData(String filePath) throws IOException {
		String jsonString= FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<HashMap<String,String>>> typeRef 
        = new TypeReference<List<HashMap<String,String>>>() {};
        
		List<HashMap<String,String>> data = mapper.readValue(jsonString, typeRef);
		return data;
	}

	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException {
		initializeWebDriver();
		landingPageObj = new LandingPage(driver);
		
		landingPageObj.goTo();
		return landingPageObj;
	}
	
	@AfterMethod(alwaysRun=true)
	public void closeBrowser() {
		driver.close();
	}
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts =  (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
	}

}
