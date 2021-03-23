package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.maze.Maze;
import za.co.wethinkcode.toyrobot.maze.SimpleMazerunner;

public class TextWorld extends AbstractWorld {

    public TextWorld(Maze maze) {
        super(maze);
        showObstacles();
    }


    @Override
    public UpdateResponse updatePosition(int nrSteps) {
        return mainUpdatePosition(nrSteps);
    }


    @Override
    public void updateDirection(boolean turnRight) {
        mainUpdateDirection(turnRight);
    }


    /**
     * Shows all the obstacles in text format
     */
    @Override
    public void showObstacles() {
        if (!(getMaze() instanceof EmptyMaze)) {
            System.out.println("There are some obstacles:");
        }

        for (Obstacle obst : getObstacles()) {
            int x = obst.getBottomLeftX();
            int y = obst.getBottomLeftY();
            System.out.println("- At position " + x + "," + y + " (to " + (x + 4) + "," + (y + 4) + ")");
        }
    }


    /**
     * Resets the text world.
     */
    @Override
    public void reset() {
        resetPosition();
        emptyList();
        resetCurrentDirection();
        setMazerunner(new SimpleMazerunner(new EmptyMaze()));
        getMazerunner().createGrid();
        showObstacles();
    }
}
