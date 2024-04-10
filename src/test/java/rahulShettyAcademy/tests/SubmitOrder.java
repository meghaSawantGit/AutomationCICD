package rahulShettyAcademy.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulShettyAcademy.pageObjects.Cart;
import rahulShettyAcademy.pageObjects.CheckOutPage;
import rahulShettyAcademy.pageObjects.ConfirmationPage;
import rahulShettyAcademy.pageObjects.LandingPage;
import rahulShettyAcademy.pageObjects.OrdersPage;
import rahulShettyAcademy.pageObjects.ProductCtalogue;
import rahulShettyAcademy.testComponents.BaseTest;

public class SubmitOrder extends BaseTest{

	String productName="ZARA COAT 3";
	
	@Test(dataProvider= "getData", groups= {"multipleOrders"})
	public void submitOrder(HashMap<String,String> input) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		String productName= input.get("productName");	
		// enter credentials and login
		ProductCtalogue productCtalogueObj=landingPageObj.logIn(input.get("email"), input.get("pwd"));
		
		// find the desired product
		productCtalogueObj.addToCart(productName);
		
		Cart cartObj = productCtalogueObj.goToCart();
		boolean match = cartObj.verifyProductInCart(productName);
		Assert.assertTrue(match);
		CheckOutPage checkOutPageObj = cartObj.goToCheckout();
		
		checkOutPageObj.selectCountry("india");
		ConfirmationPage confirmationPageObj= checkOutPageObj.placeOrder();
		
			
		//grab the title to check for successful order placement
		String message = confirmationPageObj.getText();
		Assert.assertTrue(message.equalsIgnoreCase("Thankyou for the order."));	

	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void verifyOrder() {
		ProductCtalogue productCtalogueObj=landingPageObj.logIn("yosamhere@gmail.com", "Test@123");
		OrdersPage ordersPageObj = productCtalogueObj.goToOrders();
		boolean match = ordersPageObj.verifyProductInOrders(productName);
		Assert.assertTrue(match);
	}
	
	/*@DataProvider
	public Object[][] getData() {
		return new Object[][] {{"yosamhere@gmail.com","Test@123","ZARA COAT 3"},{"test.email@gmail.com","Test@123","ADIDAS ORIGINAL"}};
	}*/
	
	@DataProvider
	public Object[][] getData() throws IOException {
		/*HashMap <String,String> map = new HashMap<String,String>();
		HashMap <String,String> map1 = new HashMap<String,String>();
		
		map.put("email", "yosamhere@gmail.com");
		map.put("pwd", "Test@123");
		map.put("productName", "ZARA COAT 3");
		
		map1.put("email", "test.email@gmail.com");
		map1.put("pwd", "Test@123");
		map1.put("productName", "ADIDAS ORIGINAL");*/
		List<HashMap<String,String>> jsonData= readFileData(System.getProperty("user.dir")+"\\src\\test\\java\\rahulShettyAcademy\\data\\orderData.json");
		
		return new Object[][] {{jsonData.get(0)},{jsonData.get(1)}};
	}
	

}
