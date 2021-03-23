package za.co.wethinkcode.toyrobot.world;

import za.co.wethinkcode.toyrobot.Position;

public class SquareObstacle implements Obstacle {
    private int bottomLeftX;
    private int bottomLeftY;
    private int size;

    public SquareObstacle(int bottomLeftX, int bottomLeftY) {
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
        this.size = 5;
    }

    /**
     * Get X coordinate of bottom left corner of obstacle.
     *
     * @return x coordinate
     */
    @Override
    public int getBottomLeftX() {
        return bottomLeftX;
    }

    /**
     * Get Y coordinate of bottom left corner of obstacle.
     *
     * @return y coordinate
     */
    @Override
    public int getBottomLeftY() {
        return bottomLeftY;
    }

    /**
     * Gets the side of an obstacle (assuming square obstacles)
     *
     * @return the length of one side in nr of steps
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Checks if this obstacle blocks access to the specified position.
     *
     * @param position the position to check
     * @return return `true` if the x,y coordinate falls within the obstacle's area
     */
    @Override
    public boolean blocksPosition(Position position) {
        if (position.getX() >= getBottomLeftX() && position.getX() <= getBottomLeftX() + 4) {
            if (position.getY() >= getBottomLeftY() && position.getY() <= getBottomLeftY() + 4) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if this obstacle blocks the path that goes from coordinate (x1, y1) to (x2, y2).
     * Since our robot can only move in horizontal or vertical lines (no diagonals yet), we can assume that either x1==x2 or y1==y2.
     *
     * @param a first position
     * @param b second position
     * @return `true` if this obstacle is in the way
     */
    @Override
    public boolean blocksPath(Position a, Position b) {
        int min_x = Math.min(a.getX(), b.getX());
        int max_x = Math.max(a.getX(), b.getX());
        int min_y = Math.min(a.getY(), b.getY());
        int max_y = Math.max(a.getY(), b.getY());

        boolean blocked = false;

        if (a.getX() == b.getX()) {
            for (int i = min_y; i <= max_y; i++) {
                if (blocksPosition(new Position(a.getX(), i)) == true) {
                    blocked = true;
                }
            }
        }

        if (a.getY() == b.getY()) {
            for (int i = min_x; i <= max_x; i++) {
                if (blocksPosition(new Position(i, a.getY())) == true) {
                    blocked = true;
                }
            }
        }

        return blocked;
    }
}

