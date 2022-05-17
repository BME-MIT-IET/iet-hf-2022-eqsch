Feature: Player
  Scenario: 1. PS mines - shell = 0, not empty, materials < 10
    Given I have a player
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has a core
    And Player stands on asteroid
    And Player has place in backpack
    When Player mines
    Then Player should have mined a mineral

  Scenario: 2. PS mines - shell = 0, not empty, materials = 10
    Given I have a player
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has a core
    And Player stands on asteroid
    And Player has no place in backpack
    When Player mines
    Then Player should have mined nothing

  Scenario: 4. PS mines - shell = 0, empty
    Given I have a player
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has not got a core
    And Player stands on asteroid
    When Player mines
    Then Player should have mined nothing

  Scenario: 6. PS mines - shell > 0
    Given I have a player
    And I have an asteroid
    And Asteroid has more than 0 shells
    And Player stands on asteroid
    When Player mines
    Then Player should have mined nothing

  Scenario: 27. PS drills - shell >= 1
    Given I have a player
    And I have an asteroid
    And Asteroid has more than 0 shells
    And Player stands on asteroid
    When Player drills
    Then Asteroid should have less shells

  Scenario: 29. PS drills - shell = 0
    Given I have a player
    And I have an asteroid
    And Asteroid has 0 shells
    And Player stands on asteroid
    When Player drills
    Then Asteroid should have 0 shell

  Scenario: 31. PS drills - shell = 1, close, uranium.ExposedFor++
    Given I have a player
    And I have an asteroid
    And Asteroid has 1 shell
    And Asteroid is close to sun
    And Asteroid has an Uranium Core
    And Player stands on asteroid
    When Player drills
    Then Asteroid should have less shells
    Then Uranium has been exposed one more time

  Scenario: 33. PS drills - shell = 1, close, ice evaporates
    Given I have a player
    And I have an asteroid
    And Asteroid has 1 shell
    And Asteroid is close to sun
    And Asteroid has an ice Core
    And Player stands on asteroid
    When Player drills
    Then Asteroid should have less shells
    And Ice should have evaporated

    Scenario: 8. PS crafting teleport - enough materials, enough space
      Given I have a player
      And Player has 2 Iron in backpack
      And Player has 1 Coal in backpack
      And Player has 1 Ice in backpack
      And Player has 1 Uranium in backpack
      And Player has place in backpack
      When Player creates a teleport
      Then Player should have created 2 teleport

  Scenario: 9. PS crafting teleport - enough materials, not enough space
    Given I have a player
    And Player has 2 Iron in backpack
    And Player has 1 Coal in backpack
    And Player has 1 Ice in backpack
    And Player has 1 Uranium in backpack
    And Player has no place in backpack
    When Player creates a teleport
    Then Player should not have created 2 teleport

  Scenario: 10. PS crafting teleport - not enough materials
    Given I have a player
    And Player has 2 Iron in backpack
    When Player creates a teleport
    Then Player should not have created 2 teleport

  Scenario: 11. PS crafting robot - enough materials
    Given I have a player
    And Player has 1 Iron in backpack
    And Player has 1 Coal in backpack
    And Player has 1 Uranium in backpack
    When Player creates a robot
    Then I should have a robot

  Scenario: 12. PS crafting robot - not enough materials
    Given I have a player
    And Player has 1 Iron in backpack
    When Player creates a robot
    Then I should not have a robot

    Scenario: 13. PS puts down teleport - has teleport
      Given I have a player
      And I have an asteroid
      And Player has 1 teleport
      And Player stands on asteroid
      When Player puts down teleport
      Then Asteroid should have neighboring teleport

  Scenario: 13. PS puts down teleport - has teleport
    Given I have a player
    And I have an asteroid
    And Player has 0 teleport
    And Player stands on asteroid
    When Player puts down teleport
    Then Asteroid should not have neighboring teleport

    Scenario: 23. PS moves to asteroid - has neighbour
      Given I have a player
      And I have an asteroid
      And Player stands on asteroid
      And Asteroid has a neighboring asteroid
      When Player moves
      Then Player should stand on the neighboring asteroid

  Scenario: 26. PS moves to asteroid - doesnt have neighbour
    Given I have a player
    And I have an asteroid
    And Player stands on asteroid
    And Asteroid doesnt have neighboring asteroid
    When Player moves
    Then Player should not stand on the neighboring asteroid

  Scenario: 29. PS moves to teleport - pair is active
    Given I have a player
    And I have an asteroid
    And Player stands on asteroid
    And Asteroid has a neighboring teleport
    And Teleport is active
    When Player moves
    Then Player should stand on the neighboring teleports pair

  Scenario: 32. PS moves to teleport - pair is not active
    Given I have a player
    And I have an asteroid
    And Player stands on asteroid
    And Asteroid has a neighboring teleport
    And Teleport is not active
    When Player moves
    Then Player should have not moved