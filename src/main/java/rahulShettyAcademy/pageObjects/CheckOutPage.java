package rahulShettyAcademy.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulShettyAcademy.abstractComponents.AbstractComponents;

public class CheckOutPage extends AbstractComponents{

	WebDriver driver;
	
	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@placeholder='Select Country']")
	WebElement countryField;
	
	@FindBy(xpath="(//button[contains(@class,'list-group-item')])[2]")
	WebElement countryBtn;
	
	@FindBy(className="action__submit")
	WebElement placeOrderBtn;
	
	By countryList = By.cssSelector(".ta-results");
	
	public void selectCountry(String countryName) {
		countryField.sendKeys(countryName);
		waitForElements(countryList);
		countryBtn.click();
	}
	
	public ConfirmationPage placeOrder() throws InterruptedException {
		scrollToElementView(placeOrderBtn);
		placeOrderBtn.click();
		return new ConfirmationPage(driver);
	}
}
