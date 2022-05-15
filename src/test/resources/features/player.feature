Feature: Player
  Scenario: Player mines successfully
    Given I have a player
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has a core
    And Player stands on asteroid
    And Player has place in backpack
    When Player mines
    Then Player should have mined a mineral

  Scenario: Player mines without success because storage is full
    Given I have a player
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has a core
    And Player stands on asteroid
    And Player has no place in backpack
    When Player mines
    Then Player should have mined nothing

  Scenario: Player mines without success because Asteroid is empty
    Given I have a player
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has not got a core
    And Player stands on asteroid
    When Player mines
    Then Player should have mined nothing