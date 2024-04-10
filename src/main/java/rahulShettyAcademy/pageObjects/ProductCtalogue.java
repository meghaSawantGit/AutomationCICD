package rahulShettyAcademy.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulShettyAcademy.abstractComponents.AbstractComponents;

public class ProductCtalogue extends AbstractComponents {

	WebDriver driver;
	
	public ProductCtalogue(WebDriver driver) {
		super(driver);
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(className="card-body")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement loadingMask;
	
	By byLocator = By.className("card-body");	
	By AddToCartBtnLocator = By.cssSelector(".card-body button:last-of-type");
	By toastLocator = By.id("toast-container");
	
	public List<WebElement> getProductList(){
		waitForElements(byLocator);
		return products;
	} 
	
	public WebElement findProductByName(String productName) {
		WebElement product =getProductList().stream().filter(p-> p.findElement(By.tagName("b")).getText().equals(productName)).findFirst().orElse(null);
		return product;
	}
	
	public void addToCart(String productName) {
		WebElement product = findProductByName(productName);
		product.findElement(AddToCartBtnLocator).click();
		waitForElements(toastLocator);
		waitForElementsToDisapear(loadingMask);
	}
}
