
Feature: Register in to the website 
I want to verify Register and login functions of my application

@register
Scenario: Register
    Given I navigate to "<website>"
    Then I fill user details "<First Name>", "<Last Name>", "<Address>", "<City>", "<State>", "<ZipCode>", "<Phone>", "<SSN>"	
    And I enter "<Username>", "<Password>", and "<Confirm Password>"	
    When I submit on Register button
    Then I should be navigated to the login page
    
@login
Scenario: Login with credentails 
    Given I am in LoginPage
    And Enter "<username>" and "<password>"
    When Click login
    And User is logged in to the website 
    Then I validate the credentials 
    Then I navigate to Account Feature

