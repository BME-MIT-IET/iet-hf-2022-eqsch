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

  Scenario: Player mines without success because Asteroid has shell
    Given I have a player
    And I have an asteroid
    And Asteroid has more than 0 shells
    And Player stands on asteroid
    When Player mines
    Then Player should have mined nothing

    Scenario: Player drills and Asteroid has more than 0 shell
      Given I have a player
      And I have an asteroid
      And Asteroid has more than 0 shells
      And Player stands on asteroid
      When Player drills
      Then Asteroid should have less shells

  Scenario: Player drills and Asteroid has 0 shell
    Given I have a player
    And I have an asteroid
    And Asteroid has 0 shells
    And Player stands on asteroid
    When Player drills
    Then Asteroid should have 0 shell

  Scenario: Player drills and Asteroid has a Uranium core and one shell
    Given I have a player
    And I have an asteroid
    And Asteroid has 1 shell
    And Asteroid is close to sun
    And Asteroid has an Uranium Core
    And Player stands on asteroid
    When Player drills
    Then Asteroid should have less shells
    And Uranium has been exposed one more time

  Scenario: Player drills and Asteroid has an ice core and one shell
    Given I have a player
    And I have an asteroid
    And Asteroid has 1 shell
    And Asteroid is close to sun
    And Asteroid has an ice Core
    And Player stands on asteroid
    When Player drills
    Then Asteroid should have less shells
    And Ice should have evaporated

