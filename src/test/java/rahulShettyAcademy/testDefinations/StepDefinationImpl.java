package rahulShettyAcademy.testDefinations;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulShettyAcademy.pageObjects.Cart;
import rahulShettyAcademy.pageObjects.CheckOutPage;
import rahulShettyAcademy.pageObjects.ConfirmationPage;
import rahulShettyAcademy.pageObjects.LandingPage;
import rahulShettyAcademy.pageObjects.ProductCtalogue;
import rahulShettyAcademy.testComponents.BaseTest;

public class StepDefinationImpl extends BaseTest{
	
	public LandingPage landingPageObj;
	public ProductCtalogue productCtalogueObj;
	public Cart cartObj;
	public CheckOutPage checkOutPageObj;
	public ConfirmationPage confirmationPageObj;
	
	@Given("I have landed on Ecommerce page")
	public void I_have_landed_on_Ecommerce_page() throws IOException {
		landingPageObj= launchApplication();
	}
	
	@Given("^I have loggedin with user name (.+) and password (.+)$")
	public void I_have_loggedin_with_username_and_password(String userName, String pwd) {
		productCtalogueObj=landingPageObj.logIn(userName, pwd);
	}
	
	@When("^I add (.+) in cart$")
	public void I_add_item_in_cart(String itemName) {
		productCtalogueObj.addToCart(itemName);
	}
	
	@When("^checkout (.+) and submit order$")
	public void checkout_item_and_submit_order(String itemName) throws InterruptedException {
		cartObj = productCtalogueObj.goToCart();
		boolean match = cartObj.verifyProductInCart(itemName);
		Assert.assertTrue(match);
		checkOutPageObj = cartObj.goToCheckout();
		
		checkOutPageObj.selectCountry("india");
		confirmationPageObj= checkOutPageObj.placeOrder();
	}
	
	@Then("^(.+) message is displayed on confirmation page$")
	public void thank_you_mssage_is_displayed_on_confirmation_page(String msg) {
		String message = confirmationPageObj.getText();
		Assert.assertTrue(message.equalsIgnoreCase(msg));
		driver.close();
	}
	
	@Then("{string} error message should display")
	public void error_message_should_display(String s1) {
		Assert.assertEquals(s1, "Incorrect email or password.");
		driver.close();
	}

}
