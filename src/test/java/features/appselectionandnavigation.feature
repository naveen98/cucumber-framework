Feature:LED Campaign Creation
  Background:
    Given I am on the Ads login page
    When I login with valid credentials
    Then verify the page title

  @smoke
  Scenario: Select Apollo Digital Signage app
    When I select the Apollo Digital Signage app
    Then the dashboard title should be verified

  @regression
  Scenario: Navigate to the LED Campaign page
    When I select the Apollo Digital Signage app
    And I navigate to the LED Campaign page
    Then the LED Campaign page title should be verified


