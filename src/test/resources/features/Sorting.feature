Feature: Migros Price Sorting Test

  Scenario: Sorting the Pet Shop Product Prices
    Given Open the Migros website
    And Close the popups
    When I select Pet Shop from the Categories tab
    Then the Pet Shop page should open
    And the products should be sorted by low price
    And Verifies that products are sorted from low price to high price