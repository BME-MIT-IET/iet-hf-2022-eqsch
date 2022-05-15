Feature: Robot

Scenario: Robot drills and Asteroid has more than 0 shell
Given I have a Robot
And I have an asteroid
And Asteroid has more than 0 shells
And Robot stands on asteroid
When Robot drills
Then Asteroid should have less shells

Scenario: Robot drills and Asteroid has 0 shell
Given I have a Robot
And I have an asteroid
And Asteroid has 0 shells
And Robot stands on asteroid
When Robot drills
Then Asteroid should have 0 shell

Scenario: Robot drills and Asteroid has a Uranium core and one shell
Given I have a Robot
And I have an asteroid
And Asteroid has 1 shell
And Asteroid is close to sun
And Asteroid has an Uranium Core
And Robot stands on asteroid
When Robot drills
Then Asteroid should have less shells
And Uranium has been exposed one more time

Scenario: Robot drills and Asteroid has an ice core and one shell
Given I have a Robot
And I have an asteroid
And Asteroid has 1 shell
And Asteroid is close to sun
And Asteroid has an ice Core
And Robot stands on asteroid
When Robot drills
Then Asteroid should have less shells
And Ice should have evaporated

