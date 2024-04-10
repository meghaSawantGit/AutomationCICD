package rahulShettyAcademy.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Cart {
	
	WebDriver driver;
	
	public Cart(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".totalRow button")
	WebElement checkOutBtn;
	
	@FindBy(css=".cartSection h3")
	List<WebElement> cartProducts;
		
	
	
	public CheckOutPage goToCheckout() {
		checkOutBtn.click();
		return new CheckOutPage(driver);
	}
	
	public boolean verifyProductInCart(String productName) {
		return cartProducts.stream().anyMatch(p -> p.getText().equals(productName));
		
	}
}
