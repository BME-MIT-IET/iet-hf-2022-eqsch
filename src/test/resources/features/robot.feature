Feature: Robot

Scenario: 28. RS drills - shell >= 1
Given I have a Robot
And I have an asteroid
  And Robot stands on asteroid
And Asteroid has 3 shells
When Robot drills
Then Asteroid should have less shells

Scenario: 30. RS drills - shell = 0
Given I have a Robot
And I have an asteroid
And Asteroid has 0 shells
And Robot stands on asteroid
When Robot drills
Then Asteroid should have 0 shell

Scenario: 32. RS drills - shell = 1, close, uranium.ExposedFor++
  Given I have a Robot
  And I have an asteroid
  And Robot stands on asteroid
  And Asteroid has 0 shells
  And Asteroid has an Uranium Core
  And Asteroid has 1 shells
  And Asteroid is close to sun
  When Robot drills
  Then Asteroid should have less shells
  And Uranium has been exposed one more time

Scenario: 34. RS drills - shell = 1, close, ice evaporates
  Given I have a Robot
  And I have an asteroid
  And Robot stands on asteroid
  And Asteroid has 1 shells
  And Asteroid is close to sun
  And Asteroid has an ice Core
  When Robot drills
  Then Asteroid should have less shells
  And Ice should have evaporated

  Scenario: 24. RS moves to asteroid - has neighbour
    Given I have a Robot
    And I have an asteroid
    And Robot stands on asteroid
    And Asteroid has a neighboring asteroid
    When Robot moves
    Then Robot should stand on the neighboring asteroid

  Scenario: 27. RS moves to asteroid - doesnt have neighbour
    Given I have a Robot
    And I have an asteroid
    And Robot stands on asteroid
    And Asteroid doesnt have neighboring asteroid
    When Robot moves
    Then Robot should not stand on the neighboring asteroid

  Scenario: 30. RS moves to teleport - pair is active
    Given I have a Robot
    And I have an asteroid
    And I have a teleport
    And Robot stands on asteroid
    And Asteroid is neighbors with teleport
    And Teleport is active
    When Robot moves
    Then Robot should stand on the neighboring teleports pair

  Scenario: 33. RS moves to teleport - pair is not active
    Given I have a Robot
    And I have an asteroid
    And Robot stands on asteroid
    And I have a teleport
    And Asteroid is neighbors with teleport
    And Teleport is not active
    When Robot moves
    Then Robot should have not moved
