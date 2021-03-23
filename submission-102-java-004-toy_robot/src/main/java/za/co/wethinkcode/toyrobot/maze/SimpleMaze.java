package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

public class SimpleMaze extends AbstractMaze {
    private Obstacle obstacle;

    public SimpleMaze() {
        createMaze();
    }


    /**
     * Creates 1 obstacle at position 1, 1.
     */
    public void createMaze() {
        obstacle = new SquareObstacle(1, 1);
        addObstacles(obstacle);
    }
}
