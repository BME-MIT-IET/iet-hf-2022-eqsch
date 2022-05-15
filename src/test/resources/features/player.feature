Feature: Player
  Scenario: Player mines
    Given I have a player
    And I have an asteroid
    And Asteroid has <shellnumber> shells
    And Asteroid has a core
    And Player stands on asteroid
    When Player mines
    Then Player should have mined a mineral
