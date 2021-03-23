package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;

public class Grid {
    private final int ROWS = 400;
    private final int COLUMNS = 200;
    private Node [][] grid = new Node[401][201];
    private Node startNode;
    private Node endNode;


    /**
     * Creates a 2d array grid where each coordinate is a Node object, then also updates each node
     * with whether it's an obstalcle or not
     * @param maze
     */
    public void createGrid(Maze maze) {
        Position position;
        Node node;
        int y = 200;
        for (int row = 0; row < 401; row++) {
            int x = -100;
            for (int col = 0; col < 201; col++) {
                position = new Position(x,y);
                if (maze.isPositionBlocked(position)) {
                    node = new Node(row, col, position, true, ROWS, COLUMNS);
                } else {
                    node = new Node(row, col, position, false, ROWS, COLUMNS);
                }
                grid[row][col] = node;
                x += 1;
            }
            y -= 1;
        }
    }


    /**
     * updates each node object in the grid with its neighbours, with a neighbor being a node directly on top, right, left, below
     * it. Also sets the start and the end node when doing mazerun.
     * @param startPosition
     * @param endPosition
     */
    public void updateNeighbors(Position startPosition, Position endPosition) {
        for (Node[] row : this.grid) {
            for (Node node : row) {
                node.updateNeighbors(this.grid);
                if (node.getPosition().equals(startPosition)) {
                    setStartNode(node);
                }
                if (node.getPosition().equals(endPosition)) {
                    setEndNode(node);
                }
            }
        }
    }


    public Node getStartNode() {
        return startNode;
    }


    public Node getEndNode() {
        return endNode;
    }


    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }


    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }


    public Node[][] getGrid() {
        return grid;
    }
}
