Feature: Ufo
  Scenario: 3. UFO mines - shell = 0, not empty
    Given I have an ufo
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has a core
    And Ufo stands on asteroid
    And Ufo has place in backpack
    When Ufo mines
    Then Ufo should have mined a mineral


  Scenario: 5. UFO mines - shell = 0, empty
    Given I have an ufo
    And I have an asteroid
    And Asteroid has 0 shells
    And Asteroid has not got a core
    And Ufo stands on asteroid
    When Ufo mines
    Then Ufo should have mined nothing

  Scenario: 7. UFO mines - shell > 0
    Given I have an ufo
    And I have an asteroid
    And Asteroid has more than 0 shells
    And Ufo stands on asteroid
    When Ufo mines
    Then Ufo should have mined nothing

  Scenario: 25. UFO moves to asteroid - has neighbour
    Given I have an ufo
    And I have an asteroid
    And Ufo stands on asteroid
    And Asteroid has a neighboring asteroid
    When Ufo moves
    Then Ufo should stand on the neighboring asteroid

  Scenario: 28. UFO moves to asteroid - doesnt have neighbour
    Given I have an ufo
    And I have an asteroid
    And Ufo stands on asteroid
    And Asteroid doesnt have neighboring asteroid
    When Ufo moves
    Then Ufo should not stand on the neighboring asteroid

  Scenario: 31. UFO moves to teleport - pair is active
    Given I have an ufo
    And I have an asteroid
    And Player stands on asteroid
    And Asteroid has a neighboring teleport
    And Teleport is active
    When Ufo moves
    Then Ufo should stand on the neighboring teleports pair

  Scenario: 34. UFO moves to teleport - pair is not active
    Given I have an ufo
    And I have an asteroid
    And Ufo stands on asteroid
    And Asteroid has a neighboring teleport
    And Teleport is not active
    When Ufo moves
    Then Ufo should have not moved