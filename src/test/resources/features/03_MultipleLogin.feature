
Feature: Log in to the website 
Login using different data

  @multiplelogin
  Scenario Outline: Login with different sets of data
    Given I am in LoginPage
    And Enter "<username>" and "<passowrd>"
    When Click login
    And User is logged in to the website 
    Then I validate the credentails 

    Examples: 
      | username     | passowrd  | status  |
      | rek        | 12re   | Fail    |
      | reka       | reka    | success |