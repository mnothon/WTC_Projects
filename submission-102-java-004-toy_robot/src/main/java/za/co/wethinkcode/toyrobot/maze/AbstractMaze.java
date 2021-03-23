package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMaze implements Maze {
    private List<Obstacle> obstaclesList;

    public AbstractMaze() {
        this.obstaclesList = new ArrayList<>();
    }


    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */


    @Override
    public List<Obstacle> getObstacles() {
        return obstaclesList;
    }


    /**
     * Checks if this maze has at least one obstacle that blocks the path that goes from coordinate (x1, y1) to (x2, y2).
     * Since our robot can only move in horizontal or vertical lines (no diagonals yet), we can assume that either x1==x2 or y1==y2.
     *
     * @param a first position
     * @param b second position
     * @return `true` if there is an obstacle is in the way
     */
    @Override
    public boolean blocksPath(Position a, Position b) {
        for (Obstacle obstacle : getObstacles()) {
            if (obstacle.blocksPath(a, b)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks whether position in grid is blocked
     * @param position
     * @return boolean, true if position is blocked false if its not
     */
    @Override
    public boolean isPositionBlocked(Position position) {
        for (Obstacle obstacle : getObstacles()) {
            if (obstacle.blocksPosition(position)) {
                return true;
            }
        }
        return false;
    }


    public void addObstacles(Obstacle obstacle) {
        this.obstaclesList.add(obstacle);
    }

}