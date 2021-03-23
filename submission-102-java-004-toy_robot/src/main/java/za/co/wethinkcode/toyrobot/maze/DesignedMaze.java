package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.Random;

public class DesignedMaze extends AbstractMaze {
    private Obstacle obstacle;

    public DesignedMaze() {
        createMaze();
    }


    /**
     * Creates the designed maze using the obstacles format.
     * Uses for loop by passing in parameters to the for each sides.
     */
    public void createMaze() {
        int upperWall = 186;
        int lowerWall = -190;
        int leftWall = -90;
        int rightWall = 86;

        int startX = -90;
        int endX = 90;
        int startY = -186;
        int endY = 186;

        int numberOfRectangles = 6;

        for (int i = 0; i < numberOfRectangles; i++) {
            createRectangleHorizontals(upperWall, startX, endX);
            createRectangleHorizontals(lowerWall, startX, endX);
            createRectangleVerticals(leftWall, startY, endY);
            createRectangleVerticals(rightWall, startY, endY);

            upperWall -= 14;
            lowerWall += 14;
            leftWall += 14;
            rightWall -= 14;

            startX += 14;
            endX -= 14;
            startY += 14;
            endY -= 14;
        }
    }


    /**
     * Creates the vertical walls of the designed maze
     * @param verticalWall - y coordinates of the vertical wall
     * @param startX - start x coordinate of the verticall wall
     * @param endX - end x coordinate of the verticall wall
     */
    public void createRectangleHorizontals(int verticalWall, int startX, int endX){
        int door = getIntegerInRangeMultipleOf(startX+4, endX-8);

        for (int i = startX; i < endX; i+=4) {
            if (i == door || i == door+4) {
                continue;
            }
            obstacle = new SquareObstacle(i, verticalWall);
            addObstacles(obstacle);
        }
    }


    /**
     * Creates the horizontal walls of the designed maze
     * @param horizontalWall - x coordinates of the vertical wall
     * @param startY - start y coordinate of the verticall wall
     * @param endY - end y coordinate of the verticall wall
     */
    public void createRectangleVerticals(int horizontalWall, int startY, int endY){
        int door = getIntegerInRangeMultipleOf(startY, endY-8);

        for (int i = startY; i < endY; i+=4) {
            if (i == door || i == door+4) {
                continue;
            }
            obstacle = new SquareObstacle(horizontalWall, i);
            addObstacles(obstacle);
        }
    }


    /**
     * Gets a random number in range to use as a door
     * @param minInclusive
     * @param maxExclusive
     * @return return random number for the door.
     */
    public int getIntegerInRangeMultipleOf(int minInclusive, int maxExclusive) {
        Random random = new Random();
        int rand = random.nextInt((maxExclusive-4) - (minInclusive+4)) + (minInclusive-4);

        for (int i = minInclusive; i < maxExclusive; i+=4) {
            if ((Math.abs(i - rand)) < 5){
                return i;
            }
        }
        return 0;
    }
}
