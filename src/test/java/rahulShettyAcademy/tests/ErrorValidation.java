package rahulShettyAcademy.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import rahulShettyAcademy.pageObjects.Cart;
import rahulShettyAcademy.pageObjects.CheckOutPage;
import rahulShettyAcademy.pageObjects.ConfirmationPage;
import rahulShettyAcademy.pageObjects.ProductCtalogue;
import rahulShettyAcademy.testComponents.BaseTest;
import rahulShettyAcademy.testComponents.Retry;

public class ErrorValidation extends BaseTest{
	
	@Test(groups= {"loginValidation"}, retryAnalyzer=Retry.class)
	public void loginWithInvalidData() {
		landingPageObj.logIn("sam@gmail.com", "123456");
		Assert.assertEquals(landingPageObj.getErrorMessage(), "Incorrect email or password.");
	}
	
	@Test
	public void cartProductValidation() throws InterruptedException, IOException {
		// TODO Auto-generated method stub
			
		String productName="ZARA COAT 3";
		// enter credentials and login
		ProductCtalogue productCtalogueObj=landingPageObj.logIn("yosamhere@gmail.com", "Test@123");
		
		// find the desired product
		productCtalogueObj.addToCart(productName);
		
		Cart cartObj = productCtalogueObj.goToCart();
		boolean match = cartObj.verifyProductInCart("ZARA COAT 323");
		Assert.assertTrue(match);
		
		
	

	}

}
