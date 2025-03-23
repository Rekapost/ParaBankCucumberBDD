@registerRequestLoan
Feature: I request loan from the bank 

Background:
    Given I navigate to "<website>"
    Then I fill user details "<First Name>", "<Last Name>", "<Address>", "<City>", "<State>", "<ZipCode>", "<Phone>", "<SSN>"	
    And I enter "<Username>", "<Password>", and "<Confirm Password>"	
    When I submit on Register button
    Then I should be successfully navigated to the accounts page

@requestLoan   
Scenario: Open New Account and Apply for Loan
    Given In front page I click "Open New Account"  
    Then I should see the account creation page
    When I click open new account
    Then I select account type
    Then I enter deposit ammount
    And I create new account
    Then I get success message for account creation
    
    Then I click request Loan
    Then I enter loan amount
    Then I enter down payment amount
    Then I select From Account
    Then I submit loan request
    Then I should see the success message for loan approved
