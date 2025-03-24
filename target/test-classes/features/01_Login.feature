@register
Feature: Register in to the website 
I want to verify Register and login functions of my application

Background:
    Given I navigate to "<website>"
    Then I fill user details "<First Name>", "<Last Name>", "<Address>", "<City>", "<State>", "<ZipCode>", "<Phone>", "<SSN>"	
    And I enter "<Username>", "<Password>", and "<Confirm Password>"	
    When I submit on Register button
    Then I should be successfully navigated to the accounts page
    
@login
@username=<generated_username> @password=<generated_password>
Scenario: Login with credentails 
    Given I am in LoginPage
    When I enter the registered username "<generated_username>" and password "<generated_password>"
    When Click login
    And User is logged in to the website 
    Then I validate the credentials 


