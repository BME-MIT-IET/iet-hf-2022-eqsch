Feature: Ufo
  Scenario: 3. UFO mines - shell = 0, not empty
    And I have an asteroid
    Given I have an ufo
    And Asteroid has 0 shells
    And Asteroid has a core
    And Ufo stands on asteroid
    And Ufo has place in backpack
    When Ufo mines
    Then Ufo should have mined a mineral


  Scenario: 5. UFO mines - shell = 0, empty
    And I have an asteroid
    Given I have an ufo
    And Asteroid has 0 shells
    And Asteroid has not got a core
    And Ufo stands on asteroid
    When Ufo mines
    Then Ufo should have mined nothing

  Scenario: 7. UFO mines - shell > 0
    And I have an asteroid
    Given I have an ufo
    And Asteroid has more than 0 shells
    And Ufo stands on asteroid
    When Ufo mines
    Then Ufo should have mined nothing

  Scenario: 25. UFO moves to asteroid - has neighbour
    And I have an asteroid
    Given I have an ufo
    And Ufo stands on asteroid
    And Asteroid has a neighboring asteroid
    When Ufo moves
    Then Ufo should stand on the neighboring asteroid

  Scenario: 28. UFO moves to asteroid - doesnt have neighbour
    And I have an asteroid
    Given I have an ufo
    And Ufo stands on asteroid
    And Asteroid doesnt have neighboring asteroid
    When Ufo moves
    Then Ufo should not stand on the neighboring asteroid

  Scenario: 31. UFO moves to teleport - pair is active
    And I have an asteroid
    Given I have an ufo
    And I have a player
    And Player stands on asteroid
    And Asteroid has a neighboring teleport
    And Teleport is active
    When Ufo moves
    Then Ufo should stand on the neighboring teleports pair

  Scenario: 34. UFO moves to teleport - pair is not active
    And I have an asteroid
    Given I have an ufo
    And Ufo stands on asteroid
    And Asteroid has a neighboring teleport
    And Teleport is not active
    When Ufo moves
    Then Ufo should have not moved