Feature: User CRUD API test
  Scenario:As a user I want to verify the crud for user
    Given The account is not registered
    When I send a POST request "/api/user.json" with body
    """
    {
      "Email":"user@testing.com",
      "FullName":"userTesting",
      "Password":"12345"
    }
    """
    Then The response code should be 200
    And The attribute "Email" must be equal to "user@testing.com"
    And I get the token for the user

    When I send a PUT request "/api/user/0.json" with body
    """
    {
      "Email": "userqatest@testing.com"
    }
    """
    Then The response code should be 200
    And The attribute "Email" must be equal to "userqatest@testing.com"
    And I get the token for the user

    When I send a DELETE request "/api/user/0.json" with body
    """
    """
    Then The response code should be 200
    And The attribute "Email" must be equal to "userqatest@testing.com"