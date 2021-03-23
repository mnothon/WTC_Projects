package za.co.wethinkcode.toyrobot.world;

import org.turtle.StdDraw;
import org.turtle.Turtle;
import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.maze.Maze;
import za.co.wethinkcode.toyrobot.maze.SimpleMazerunner;

import java.awt.*;

public class TurtleWorld extends AbstractWorld {
    private Turtle turtle;
    private Turtle world;
    private Turtle obs;

    public TurtleWorld(Maze maze) {
        super(maze);
        setupWorld();
        showObstacles();
        setupTurtle();
    }


    /**
     * Updates the position of your robot in the world by moving the nrSteps in the robots current direction.
     * @param nrSteps steps to move in current direction
     * @return true if this does not take the robot over the world's limits, or into an obstacle.
     */
    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        UpdateResponse response =  mainUpdatePosition(nrSteps);
        if (response == UpdateResponse.SUCCESS) {
            getTurtle().forward(nrSteps);
        }
        return response;
    }


    /**
     * Updates the current direction your robot is facing in the world by cycling through the directions UP, RIGHT, BOTTOM, LEFT.
     * @param turnRight if true, then turn 90 degrees to the right, else turn left.
     */
    @Override
    public void updateDirection(boolean turnRight) {
        mainUpdateDirection(turnRight);
        if (turnRight) {
            getTurtle().right(90);
        } else {
            getTurtle().left(90);
        }
    }


    public Turtle getTurtle() {
        return turtle;
    }


    /**
     * Creates the Turtle world by drawing the border.
     */
    public void setupWorld() {
        this.world = new Turtle(0,0,0);
        this.world.setPosition(-101, 201);
        this.world.setColor(Color.red);
        StdDraw.setXscale(-250, 250);
        StdDraw.setYscale(-250, 250);
        for (int i = 0; i < 2; i ++) {
            this.world.forward(202);
            this.world.right(90);
            this.world.forward(402);
            this.world.right(90);
        }
    }


    /**
     * Resets the world related stuff
     */
    @Override
    public void reset() {
        resetPosition();
        emptyList();
        resetCurrentDirection();
        StdDraw.clear();
        setupWorld();
        showObstacles();
        setMazerunner(new SimpleMazerunner(new EmptyMaze()));
        getMazerunner().createGrid();
        setupTurtle();
    }


    /**
     * Creates the main Turtle and puts it at the center of the world. Sets it color to red
     * return: no return
     */
    public void setupTurtle() {
        this.turtle = new Turtle(0,0,90);
        this.turtle.setPosition(0,0);
        this.turtle.setColor(Color.red);
        this.turtle.show();
    }


    /**
     * Draws a obstacle using the obstacle object passed in via the parameters
     * return: no return
     */
    @Override
    public void showObstacles() {
        obs = new Turtle(0,0,0);
        obs.setColor(Color.black);

        for (Obstacle obst : getObstacles()) {
            int x = obst.getBottomLeftX();
            int y = obst.getBottomLeftY();
            obs.setPosition(x,y);
            for (int i = 0; i < 4; i++) {
                obs.forward(4);
                obs.left(90);
            }
        }
    }
}
