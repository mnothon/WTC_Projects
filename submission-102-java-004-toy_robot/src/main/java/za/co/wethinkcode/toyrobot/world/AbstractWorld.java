package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.Robot;

import java.util.List;

public abstract class AbstractWorld implements IWorld {
    private final Position TOP_LEFT = new Position(-200,100);
    private final Position BOTTOM_RIGHT = new Position(100,-200);

    private Direction currentDirection;
    private Position position;

    public AbstractWorld() {
        this.currentDirection = Direction.UP;
        this.position = CENTRE;
    }

    /**
     * Updates the position of your robot in the world by moving the nrSteps in the robots current direction.
     * @param nrSteps steps to move in current direction
     * @return true if this does not take the robot over the world's limits, or into an obstacle.
     */

    public abstract UpdateResponse updatePosition(int nrSteps);

    public UpdateResponse mainUpdatePosition(int nrSteps) {
        int newX = this.position.getX();
        int newY = this.position.getY();
        int[] position = new int[2];

        position = handleMoveCommand(currentDirection, newY, newX, nrSteps);

        newX = position[0];
        newY = position[1];

        Position newPosition = new Position(newX, newY);
        if (isNewPositionAllowed(newPosition)){
            this.position = newPosition;
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }

    public static int[] handleMoveCommand(Direction currentDirection, int newY, int newX, int nrSteps) {
        switch (currentDirection) {
            case UP:
                newY = newY + nrSteps;
                break ;
            case RIGHT:
                newX = newX + nrSteps;
                break ;
            case DOWN:
                newY = newY - nrSteps;
                break ;
            case LEFT:
                newX = newX - nrSteps;
                break ;
        }
        int[] position = {newX, newY};
        return position;
    }


    /**
     * Updates the current direction your robot is facing in the world by cycling through the directions UP, RIGHT, BOTTOM, LEFT.
     * @param turnRight if true, then turn 90 degrees to the right, else turn left.
     */
    @Override
    public void updateDirection(boolean turnRight) {
        if (turnRight) {
            switch (this.currentDirection) {
                case UP:
                    this.currentDirection = Direction.RIGHT;
                    break ;
                case RIGHT:
                    this.currentDirection = Direction.DOWN;
                    break ;
                case DOWN:
                    this.currentDirection = Direction.LEFT;
                    break ;
                case LEFT:
                    this.currentDirection = Direction.UP;
                    break ;
            }
        } else {
            switch (this.currentDirection) {
                case UP:
                    this.currentDirection = Direction.LEFT;
                    break ;
                case RIGHT:
                    this.currentDirection = Direction.UP;
                    break ;
                case DOWN:
                    this.currentDirection = Direction.RIGHT;
                    break ;
                case LEFT:
                    this.currentDirection = Direction.DOWN;
                    break ;
            }
        }
    }

    /**
     * Retrieves the current position of the robot
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * Gets the current direction the robot is facing in relation to a world edge.
     * @return Direction.UP, RIGHT, DOWN, or LEFT
     */
    @Override
    public IWorld.Direction getCurrentDirection() {
        return this.currentDirection;
    }

    /**
     * Checks if the new position will be allowed, i.e. falls within the constraints of the world, and does not overlap an obstacle.
     * @param position the position to check
     * @return true if it is allowed, else false
     */
    @Override
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT, BOTTOM_RIGHT);
    }

    /**
     * Checks if the robot is at one of the edges of the world
     * @return true if the robot's current is on one of the 4 edges of the world
     */
    @Override
    public boolean isAtEdge() {
        System.out.println("Implement this function");
        return true;
    }

    /**
     * Reset the world by:
     * - moving current robot position to center 0,0 coordinate
     * - removing all obstacles
     * - setting current direction to UP
     */
    public void reset() {

    }

    /**
     * @return the list of obstacles, or an empty list if no obstacles exist.
     */
    @Override
    public List<Obstacle> getObstacles() {
        return null;
    }

    /**
     * Gives opportunity to world to draw or list obstacles.
     */
    public void showObstacles() {

    }
}
