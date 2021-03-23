package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.Random;

public class RandomMaze extends AbstractMaze {
    private Obstacle obstacle;

    public RandomMaze() {
        createMaze();
    }


    /**
     * Creates a series of random obstacles, atleast 1 is made and the maximum is 50 obstacles.
     * Adds them to the obstacles list
     */
    public void createMaze() {
        Random random = new Random();
        int numOfObstacles = random.nextInt(50 - 1) - 1;
        for (int i = 0; i < numOfObstacles; i++) {
            obstacle = new SquareObstacle(random.nextInt(96 - (-100)) -100, random.nextInt(196 - (-200)) -200);
            addObstacles(obstacle);
        }
    }
}
