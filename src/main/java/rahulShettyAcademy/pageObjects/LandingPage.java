package rahulShettyAcademy.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {

	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement loginBtn;
	
	@FindBy(css="[class*='flyInOut'] div")
	WebElement errorToast;
	
	public ProductCtalogue logIn(String uName, String pwd) {
		userEmail.sendKeys(uName);
		userPassword.sendKeys(pwd);
		loginBtn.click();
		return new ProductCtalogue(driver);
	}
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getErrorMessage() {
		System.out.println("msg: "+errorToast.getText());
		return errorToast.getText();
	}
}
