# breakout
## NAME


DO NO FORK THIS REPOSITORY, clone it directly to your computer.


This project implements the game of Breakout.

### Timeline

Start Date: 9/3/2022

Finish Date: 9/4/2022

Hours Spent: 9

### Resources Used

Dr Duvall's sample animation code

### Running the Program

Main class:
Main
Data files needed: 
None
Key/Mouse inputs:
Cursor controls paddle
Cheat keys:
None

### Notes/Assumptions

Assumptions or Simplifications:
I assumed that each component of the game would be constant - i.e. the ball is always a ball and the bricks are always 
rectangles filled with a singular color. The current method of populating the bricks assumes that the level does not begin
with any missing/nonuniformly sized bricks. 
Known Bugs:
Exception is thrown when a brick is hit, but there is no impact on gameplay.
Extra features or interesting things we should not miss:


### Impressions
Overall, I fell short in compartmentalizing my code properly. I ran out of time to delve deeper into how custom objects 
work in OpenJX, so I lumped everything together in the main class. I do feel that I did a reasonable job of keeping methods
short and selfcontained. Overall, I am happy with the game play I was able to acheive. Given more time, I would like to 
implement file-read-in levels so that more customizability is possible. I would also like to allow the user to pick from
one of several difficulty levels and ball/brick skins. These changes would be fairly easy to accomodate given the current
code. 
