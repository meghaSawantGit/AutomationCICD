
@tag
Feature: Error validation for login with incorrect credentials
  I want to use this template for my feature file

  @LoginError
  Scenario Outline: Error validation for login
    Given I have landed on Ecommerce page
    When I have loggedin with user name <userName> and password <pwd>
    Then "Incorrect email or password." error message should display

    Examples: 
      | userName  					| pwd 		 | 
      | yosamhere@gmail.com | Test@123456 |