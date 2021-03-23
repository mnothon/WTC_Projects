package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.maze.Maze;
import za.co.wethinkcode.toyrobot.maze.SimpleMaze;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimplemazeTest {

    @Test
    void testSimplemazeHasOneObstacle() {
        Maze maze = new SimpleMaze();
        assertEquals(1, maze.getObstacles().size());
    }
}
