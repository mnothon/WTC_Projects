# problem-102-java-004-002
Module 102
Topic 004: Inheritance, Composition and Polymorphism
Problem 002: Toy Robot 2

This project uses the Turtle GUI to create game that a user can interact with by issuing commands that the 'robot'.
The robot will comply with if the game allows or send an appropriate message if the command is not allowed.
command examples are forward 10, which moves robot 10 steps forward 10 steps, right, left, back. 
There is a help command which the user can use to find which commands to use.

The main feature of this project though is the implementation of the A* pathfinding algorithm. If a user issues the command
mazerun the robot finds the shortest way past the the obstacles and also prints out the steps that the robot takes to find its way

Run project:
 * The program takes in 2 parameters. The first being either a string "turtle", or "text". This determines which GUI the game will
   Use turtle being the Turtle GUI, text being a text based interface.
   The secpnd parameter allows the user to choose what Maze the game will use, these are the options:
   * EmptyMaze - No maze at all
   * SimpleMaze - One obstacle at position (1, 1)
   * RandomMaze - Obstacles at random places on the map. Between 1 and 50.
   * DesignedMaze - An intricate procedurally generated maze.
   
## Build, Test & Run
You may use IntelliJ to run your code and tests, but alternatively you can use the Maven build tool:
* First ensure you are in the root directory of the project
* To compile your code, run: `mvn compile` 
* To run the tests: `mvn test`
* To run your application: `mvn compile exec:java`

