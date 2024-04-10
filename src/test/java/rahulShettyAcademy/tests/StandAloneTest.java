package rahulShettyAcademy.tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client");
		
		String productName="ZARA COAT 3";
		// enter credentials and login
		driver.findElement(By.id("userEmail")).sendKeys("yosamhere@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Test@123");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-body")));
		
		// find the desired product
		List<WebElement> products = driver.findElements(By.className("card-body"));
		
		// alternate code for finding the product instead of using streams
		/*for(WebElement p: products) {
			if(p.findElement(By.tagName("b")).getText().equals("ZARA COAT 3")) {
				p.findElement(By.cssSelector(".card-body button:last-of-type")).click();
			}
		}*/
		
		WebElement product =products.stream().filter(p-> p.findElement(By.tagName("b")).getText().equals(productName)).findFirst().orElse(null);
		//click on "add to cart"
		product.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		// wait for loading mask to disappear and success toast to show
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		//click on cart button to navigate to cart page
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//verify that the product we added is in the cart
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Assert.assertTrue(cartProducts.stream().anyMatch(p -> p.getText().equals(productName)));
		
		//click on checkout button
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//select country 
		
		driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys("india");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("(//button[contains(@class,'list-group-item')])[2]")).click();
		
		WebElement placeOrderBtn = driver.findElement(By.className("action__submit"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", placeOrderBtn);
		Thread.sleep(3000);
		placeOrderBtn.click();
		
		//grab the title to check for successful order placement
		
		Assert.assertTrue(driver.findElement(By.className("hero-primary")).getText().equalsIgnoreCase("Thankyou for the order."));
		driver.close();
		
	

	}

}
