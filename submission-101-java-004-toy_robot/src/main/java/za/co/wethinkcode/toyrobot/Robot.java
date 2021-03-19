package za.co.wethinkcode.toyrobot;

import java.util.Arrays;
import java.util.List;

public class Robot {
    private static final List<String> VALID_COMMANDS = Arrays.asList("off", "help", "forward");

    private static int MIN_Y = -200, MAX_Y = 200;
    private static int MIN_X = -100, MAX_X = 100;
    private static String NORTH = "NORTH", WEST = "WEST", EAST = "EAST", SOUTH = "SOUTH";
    private final Position TOP_LEFT = new Position(-100,200);
    private final Position BOTTOM_RIGHT = new Position(100, -200);

    public static final Position CENTRE = new Position(0,0);

    private Direction currentDirection; // check code below for declaration of Direction enum

    private Position position;
    private String status;
    private String name;

    public Robot(String name) {
        this.name = name;
        this.status = "Ready";
        this.position = CENTRE;
        this.currentDirection = Direction.NORTH;
    }

    public String getStatus() {
        return this.status;
    }

    public Position getPosition() {
        return this.position;
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean handleCommand(Command command){
        return command.execute(this);
    }

    public boolean updatePosition(int nrSteps){
        int newY = this.position.getY();
        int newX = this.position.getX();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        if (newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)){
            this.position = newPosition;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
       return "[" + this.position.getX() + "," + this.position.getY() + "] "
               + this.name + "> " + this.status;
    }
}
