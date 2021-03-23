package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private int row;
    private int col;
    private Position position;
    private boolean isBarrier;
    private List<Node> neighbors;
    private int totalRows;
    private int totalCols;


    public Node(int row, int col, Position position, boolean barrier, int totalRows, int totalCols) {
        this.row = row;
        this.col = col;
        this.position = position;
        this.isBarrier = barrier;
        this.neighbors = new ArrayList<>();
        this.totalRows = totalRows;
        this.totalCols = totalCols;
    }


    public Position getPosition() {
        return position;
    }


    public List<Node> getNeighbors() {
        return neighbors;
    }


    public void addNeighbors(Node node) {
        this.neighbors.add(node);
    }


    public void updateNeighbors(Node[][] grid) {
        if (this.row < this.totalRows && !(grid[this.row+1][this.col].isBarrier)) {
            addNeighbors(grid[this.row+1][this.col]);
        }

        if (this.row > 0 && !(grid[this.row - 1][this.col].isBarrier)) {
            addNeighbors(grid[this.row - 1][this.col]);
        }

        if (this.col < this.totalCols && !(grid[this.row][this.col + 1].isBarrier)) {
            addNeighbors(grid[this.row][this.col + 1]);
        }

        if (this.col > 0 && !(grid[this.row][this.col - 1].isBarrier)) {
            addNeighbors(grid[this.row][this.col - 1]);
        }
    }


    /**
     * Overwrites equal method so I can compare each node based on its position
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return getPosition().equals(node.getPosition());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getPosition());
    }
}