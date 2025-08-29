Feature: LED Campaign Creation

  @smoke
  Scenario: Login and select Apollo Digital Signage app
    Given I am on the Ads login page
    When I login with valid credentials
    Then verify the page title
    When I select the Apollo Digital Signage app
    Then the dashboard title should be verified


  @smoke
    Scenario: Navigate to the LED Campaign page
    When I navigate to the LED Campaign page
    Then the LED Campaign page title should be verified

  @regression
  Scenario: Create campaigns from Excel and verify count
    Given I open the Ads app and land on the LED Campaigns page
    And I note current total campaign count and pages
    When I create LED campaigns from basic excel
    And I apply LED styles from led excel
    Then each campaign run should be successful
    And the total campaign count should increase
