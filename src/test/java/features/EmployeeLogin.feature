Feature: Employee Login
  I want to check that Employee can Login into our Portal

  Scenario: Employee Login

    Given user is on the CRM Portal
    When user enters username, password and click on Login
    Then user should be logged in successfully and main page should open successfully

