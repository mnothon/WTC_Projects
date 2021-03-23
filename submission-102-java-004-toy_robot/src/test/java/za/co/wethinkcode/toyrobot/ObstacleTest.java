package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import static org.junit.jupiter.api.Assertions.*;

public class ObstacleTest {

    @Test
    void obstacleSize() {
        Obstacle obstacle = new SquareObstacle(1,1);
        assertEquals(obstacle.getSize(), 5);
    }

    @Test
    void blocksPosition() {
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPosition(new Position(1,1)));
        assertTrue(obstacle.blocksPosition(new Position(4,4)));
        assertFalse(obstacle.blocksPosition(new Position(0,0)));
        assertFalse(obstacle.blocksPosition(new Position(6,6)));
    }

    @Test
    void blocksPathFromBottom() {
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPath(new Position(3,0), new Position(3,10)));
    }

    @Test
    void blocksPathFromTop() {
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPath(new Position(3,10), new Position(3,0)));
    }

    @Test
    void blocksPathFromLeft() {
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPath(new Position(-2,3), new Position(10,3)));
    }

    @Test
    void blocksPathFromRight() {
        Obstacle obstacle = new SquareObstacle(1,1);
        assertTrue(obstacle.blocksPath(new Position(10,3), new Position(-2,3)));
    }

}
