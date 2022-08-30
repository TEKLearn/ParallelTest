Feature: Test login functionality
  Description: Ensure that the login page handles valid and invalid logins

  Background: Browser is open and user is on login page
    Given browser is open
    And user is on login page

  Scenario Outline: Check login is unsuccessful with invalid credentials
    When user enters <username> and <password>
    And user clicks on login button
    Then error message is displayed

    Examples: 
      | username | password       |
      | Admin         | errorpassword |
      | WrongUsername | admin123      |

  @SmokeTest
  Scenario: Check login is successful with valid credentials
    When user enters Admin and admin123
    And user clicks on login button
    Then user is navigated to home page
