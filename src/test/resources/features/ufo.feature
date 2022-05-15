Feature: Ufo
  Scenario: Ufo mines successfully
    Given I have an ufo
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has a core
    And Ufo stands on asteroid
    And Ufo has place in backpack
    When Ufo mines
    Then Ufo should have mined a mineral

  Scenario: Ufo mines without success because storage is full
    Given I have a ufo
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has a core
    And Ufo stands on asteroid
    And Ufo has no place in backpack
    When Ufo mines
    Then Ufo should have mined nothing

  Scenario: Ufo mines without success because Asteroid is empty
    Given I have a ufo
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has not got a core
    And Ufo stands on asteroid
    When Ufo mines
    Then Ufo should have mined nothing

  Scenario: Ufo mines without success because Asteroid has shell
    Given I have a ufo
    And I have an asteroid
    And Asteroid has more than 0 shells
    And Ufo stands on asteroid
    When Ufo mines
    Then Ufo should have mined nothing