package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.*;
import za.co.wethinkcode.toyrobot.world.IWorld;

import java.util.*;

public class SimpleMazerunner implements MazeRunner {
    private Maze maze;
    private Grid grid;
    private ArrayList<Node> path = new ArrayList<>();
    private Robot target;
    private int mazerunCost;
    private boolean foundPath = true;

    public SimpleMazerunner(Maze maze) {
        this.maze = maze;
        this.grid = new Grid();
    }


    /**
     * Asks Mazerunner to start its mazerun.
     *
     * @param target        the instance of Robot to use to run the maze
     * @param edgeDirection the edge to try and reach, one of Direction.UP, RIGHT, DOWN, or LEFT
     * @return true if it was successful
     */
    @Override
    public boolean mazeRun(Robot target, IWorld.Direction edgeDirection) {
        this.target = target;
        Position endPosition = getEndPosition(edgeDirection);
        Position startPosition = target.getWorld().getPosition();
        boolean run = true;

        while (run) {
            this.grid.updateNeighbors(startPosition, endPosition);
            aStarAlgorithm(grid.getGrid(), grid.getStartNode(), grid.getEndNode(), target);
            break ;
        }
        return true;
    }


    /**
     * Returns the cost for the previous mazerun attempt:
     * <p>
     * - Each command that involves moving 1 or more steps must count the number of steps taken in that command towards the total steps.
     * - Each time your robot turns, it also counts as 1 step.
     * - Commands that fails because it is blocked by an obstacle or an edge must also count the steps involved in the command towards the total number of steps.
     *
     * @return the total cost in steps of most recent mazerun
     */
    @Override
    public int getMazeRunCost() {
        return this.mazerunCost;
    }


    public void resetMazerunCost() {
        this.mazerunCost = 0;
    }


    public void addMazerunCost() {
        this.mazerunCost += 1;
    }


    public boolean getFoundPath() {
        return foundPath;
    }

    public void setFoundPath(boolean foundPath) {
        this.foundPath = foundPath;
    }

    /**
     * This is where the magic happens, this algorith is driven by the following formula Fn = Gn + Hn, where Fn is the total
     * distance from the start to finish, Gn = the exact distance from start node to current node, Hn being the theoretical distance
     * from the current node to the end node, which we use the manhattan distance formula to calculate.
     * The prioruty queue is used to keep track of the Fn scores, and it always returns the smallest Fn score which is the shortes route.
     * @param grid - The grid with all the nodes in it
     * @param start - the start node
     * @param end - the end node
     * @param target - the robot object
     * @return - boolean returns false when algorithm is done.
     */
    public boolean aStarAlgorithm(Node [][] grid, Node start, Node end, Robot target) {
        Hashtable<Node, Node> cameFrom = new Hashtable<>();
        Hashtable<Node, Double> gScore = new Hashtable<>();
        Hashtable<Node, Double> fScore = new Hashtable<>();

        ArrayList <Node> path = new ArrayList<>();
        ArrayList <Node> openSetHash = new ArrayList<>();

        PriorityQueue<MyParameters> openSet = new PriorityQueue<>();

        int count = 0;

        openSet.add(new MyParameters(0, count, start));

        path.add(start);

        for (Node[] row : grid) {
            for (Node node : row) {
                gScore.put(node, Double.POSITIVE_INFINITY);
                fScore.put(node, Double.POSITIVE_INFINITY);
            }
        }

        gScore.replace(start, 0.0);
        fScore.replace(start, heuristic(start.getPosition(), end.getPosition()));

        openSetHash.add(start);

        while (!openSet.isEmpty()) {
            Node currentNode= openSet.poll().getNode();
//            System.out.println(openSet.size());
            if (openSet.size() == 1) {
                setFoundPath(false);
            }
            path.add(currentNode);
            openSetHash.remove(currentNode);

            if (currentNode.equals(end)) {
                reconstructPath(cameFrom, start, end, target);
                return true;
            }
            for (Node neighbor : currentNode.getNeighbors()){
                double tempGScore;
                tempGScore = gScore.get(currentNode) + 1.0;
                if (tempGScore < gScore.get(neighbor)){
                    cameFrom.put(neighbor, currentNode);
                    gScore.replace(neighbor, tempGScore);
                    fScore.replace(neighbor, tempGScore + heuristic(neighbor.getPosition(), end.getPosition()));
                    if (!openSetHash.contains(neighbor)) {
                        count += 1;
                        openSet.add(new MyParameters(fScore.get(neighbor), count, neighbor));
                        openSetHash.add(neighbor);
                    }
                }
            }
        }
        return false;
    }


    /**
     * Reconstructs the path that is made by the algorithm. This uses a hashtable, the key is the current node and the value is the node
     * it came from. The path is reconstructed by following the node last node and seeing where it came from until you reach the
     * beginning
     * @param cameFrom - hashtable with all the nodes the algorithm visited
     * @param start - start node
     * @param current - end node
     * @param target - robot object
     */
    public void reconstructPath(Hashtable<Node, Node> cameFrom, Node start, Node current, Robot target){
        Node end = current;
        while (cameFrom.containsKey(current)) {
            addNodeToPath(current);
            current = cameFrom.get(current);
        }

        Collections.reverse(getPath());
        if (getPath().size() > 0) {
            createCommandsList(start, end);
        }
    }


    /**
     * Prints out to the chosen interface, either turtle world or text world, the commands that correspond the path that the robot took
     * @param start - start node
     * @param end - end node
     */
    public void createCommandsList(Node start, Node end) {
        int loop = 0;
        IWorld.Direction direction = determineNextNodeDirection(start, getPath().get(0));

        if (direction == IWorld.Direction.RIGHT) {
            handleReconstruct("right");
        }
        if (direction == IWorld.Direction.DOWN) {
            handleReconstruct("right");
            handleReconstruct("right");
        }
        if (direction == IWorld.Direction.LEFT) {
            handleReconstruct("left");
        }

        int steps = 1;
        while (loop < path.size()) {
            if (path.get(loop).equals(end)) {
                this.target.handleCommand(new ForwardCommand(Integer.toString(steps)));
                Play.printRobot(this.target);
                addMazerunCost();
                break;
            }
            direction = determineNextNodeDirection(path.get(loop), path.get(loop+1));
            if (direction == IWorld.Direction.UP){
                steps += 1;
            } else {
                this.target.handleCommand(new ForwardCommand(Integer.toString(steps)));
                Play.printRobot(this.target);
                addMazerunCost();
                steps = 1;
                if (direction == IWorld.Direction.RIGHT) {
                    handleReconstruct("right");
                }
                if (direction == IWorld.Direction.DOWN) {
                    handleReconstruct("right");
                    handleReconstruct("right");
                }
                if (direction == IWorld.Direction.LEFT) {
                    handleReconstruct("left");
                }
            }
            loop += 1;
        }
    }


    /**
     * Assists the above method in printing out the commands
     * @param command
     */
    public void handleReconstruct(String command) {
        switch (command) {
            case "left":
                this.target.handleCommand(new LeftCommand());
                break;
            case "right":
                this.target.handleCommand(new RightCommand());
                break;
        }
        Play.printRobot(this.target);
        addMazerunCost();
    }


    /**
     * Determines the direction the robot should turn based on where the next node in the path is relative to the current node.
     * @param node - current node
     * @param nextNode - next node
     * @return direction next node is relative to current node.
     */
    public IWorld.Direction determineNextNodeDirection(Node node, Node nextNode) {

        if (this.target.getWorld().getCurrentDirection() == IWorld.Direction.UP) {

            if (node.getPosition().getX() == nextNode.getPosition().getX() && node.getPosition().getY()+1 == nextNode.getPosition().getY()) {
                return IWorld.Direction.UP;
            } else if (node.getPosition().getX()+1 == nextNode.getPosition().getX() && node.getPosition().getY() == nextNode.getPosition().getY()) {
                return IWorld.Direction.RIGHT;
            } else if (node.getPosition().getX() == nextNode.getPosition().getX() && node.getPosition().getY()-1 == nextNode.getPosition().getY()) {
                return IWorld.Direction.DOWN;
            } else if (node.getPosition().getX()-1 == nextNode.getPosition().getX() && node.getPosition().getY() == nextNode.getPosition().getY()) {
                return IWorld.Direction.LEFT;
            }

        } else if (this.target.getWorld().getCurrentDirection() == IWorld.Direction.RIGHT) {

            if (node.getPosition().getX()+1 == nextNode.getPosition().getX() && node.getPosition().getY() == nextNode.getPosition().getY()) {
                return IWorld.Direction.UP;
            } else if (node.getPosition().getX() == nextNode.getPosition().getX() && node.getPosition().getY()-1 == nextNode.getPosition().getY()) {
                return IWorld.Direction.RIGHT;
            } else if (node.getPosition().getX()-1 == nextNode.getPosition().getX() && node.getPosition().getY() == nextNode.getPosition().getY()) {
                return IWorld.Direction.DOWN;
            } else if (node.getPosition().getX() == nextNode.getPosition().getX() && node.getPosition().getY()+1 == nextNode.getPosition().getY()) {
                return IWorld.Direction.LEFT;
            }

        } else if (this.target.getWorld().getCurrentDirection() == IWorld.Direction.DOWN) {

            if (node.getPosition().getX() == nextNode.getPosition().getX() && node.getPosition().getY()-1 == nextNode.getPosition().getY()) {
                return IWorld.Direction.UP;
            } else if (node.getPosition().getX()-1 == nextNode.getPosition().getX() && node.getPosition().getY() == nextNode.getPosition().getY()) {
                return IWorld.Direction.RIGHT;
            } else if (node.getPosition().getX() == nextNode.getPosition().getX() && node.getPosition().getY()+1 == nextNode.getPosition().getY()) {
                return IWorld.Direction.DOWN;
            } else if (node.getPosition().getX()+1 == nextNode.getPosition().getX() && node.getPosition().getY() == nextNode.getPosition().getY()) {
                return IWorld.Direction.LEFT;
            }

        } else {

            if (node.getPosition().getX()-1 == nextNode.getPosition().getX() && node.getPosition().getY() == nextNode.getPosition().getY()) {
                return IWorld.Direction.UP;
            } else if (node.getPosition().getX() == nextNode.getPosition().getX() && node.getPosition().getY()+1 == nextNode.getPosition().getY()) {
                return IWorld.Direction.RIGHT;
            } else if (node.getPosition().getX()+1 == nextNode.getPosition().getX() && node.getPosition().getY() == nextNode.getPosition().getY()) {
                return IWorld.Direction.DOWN;
            } else if (node.getPosition().getX() == nextNode.getPosition().getX() && node.getPosition().getY()-1 == nextNode.getPosition().getY()) {
                return IWorld.Direction.LEFT;
            }

        }
        return null;
    }


    public void createGrid() {
        grid.createGrid(maze);
    }


    public ArrayList<Node> getPath() {
        return path;
    }


    public void addNodeToPath(Node node) {
        this.path.add(node);
    }


    /**
     * Gets the end node (position) where the robot should go if mazerun command is run based on the parameter, whether the user
     * input up down etc
     * @param edgeDirection - direction that the user wants the robot to go
     * @return - position of where robot should stop
     */
    public Position getEndPosition(IWorld.Direction edgeDirection) {
        switch (edgeDirection) {
            case UP:
                return getEndPositionForUP();
            case RIGHT:
                return getEndPositionForRIGHT();
            case DOWN:
                return getEndPositionForDOWN();
            case LEFT:
                return getEndPositionForLEFT();
            default:
                return null;
        }
    }


    /**
     * Heuristic to calculate the theoretical distacnce of where how far any node is from a particular node.
     * Based on the manhattan distance formula
     * @param start - start node
     * @param end - end node
     * @return final distance.
     */
    public Double heuristic(Position start, Position end) {
        int value = Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
        double finalValue = value;
        return finalValue;
    }


    /**
     * Gets end position for UP
     * @return
     */
    public Position getEndPositionForUP() {
        for (int x = 0; x < -101; x--){
            if (!this.maze.isPositionBlocked(new Position(x, 200))) {
                return new Position(x, 200);
            }
        }
        for (int x = 0; x < 101; x++) {
            if (!this.maze.isPositionBlocked(new Position(x, 200))) {
                return new Position(x, 200);
            }
        }
        return null;
    }


    /**
     * Gets end position for RIGHT
     * @return
     */
    public Position getEndPositionForRIGHT() {
        for (int y = 0; y < -201; y--) {
            if (!this.maze.isPositionBlocked(new Position(100, y))) {
                return new Position(100, y);
            }
        }
        for (int y = 0; y < 201; y++) {
            if (!this.maze.isPositionBlocked(new Position(100, y))) {
                return new Position(100, y);
            }
        }
        return null;
    }


    /**
     * Gets end position for DOWN
     * @return
     */
    public Position getEndPositionForDOWN() {
        for (int x = 0; x < -101; x--){
            if (!this.maze.isPositionBlocked(new Position(x, -200))) {
                return new Position(x, -200);
            }
        }
        for (int x = 0; x < 101; x++) {
            if (!this.maze.isPositionBlocked(new Position(x, -200))) {
                return new Position(x, -200);
            }
        }
        return null;
    }


    /**
     * Gets end position for LEFT
     * @return
     */
    public Position getEndPositionForLEFT() {
        for (int y = 0; y < -201; y--) {
            if (!this.maze.isPositionBlocked(new Position(-100, y))) {
                return new Position(-100, y);
            }
        }
        for (int y = 0; y < 201; y++) {
            if (!this.maze.isPositionBlocked(new Position(-100, y))) {
                return new Position(-100, y);
            }
        }
        return null;
    }
}
