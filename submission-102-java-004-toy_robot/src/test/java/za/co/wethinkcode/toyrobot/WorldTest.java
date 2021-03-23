package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.maze.DesignedMaze;
import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.maze.SimpleMaze;
import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WorldTest {


    @Test
    void updatePosition() {
        IWorld world = new TextWorld(new EmptyMaze());
        assertEquals(IWorld.CENTRE, world.getPosition());
        world.updatePosition(10);
        Position newPosition = new Position(IWorld.CENTRE.getX(), IWorld.CENTRE.getY() + 10);
        assertEquals(newPosition, world.getPosition());
    }

    @Test
    void updatePositionOutsideWorld() {
        IWorld world = new TextWorld(new EmptyMaze());
        assertEquals(IWorld.CENTRE, world.getPosition());
        IWorld.UpdateResponse response = world.updatePosition(201);
        Position newPosition = new Position(IWorld.CENTRE.getX(), IWorld.CENTRE.getY());
        assertEquals(newPosition, world.getPosition());
        assertEquals(response, IWorld.UpdateResponse.FAILED_OUTSIDE_WORLD);
    }

    @Test
    void updatePositionHitsObstacle() {
        IWorld world = new TextWorld(new SimpleMaze());
        assertEquals(IWorld.CENTRE, world.getPosition());
        world.updatePosition(2);
        world.updateDirection(true);
        IWorld.UpdateResponse response = world.updatePosition(10);
        assertEquals(response, IWorld.UpdateResponse.FAILED_OBSTRUCTED);
    }

    @Test
    void updateDirectionRight() {
        IWorld world = new TextWorld(new EmptyMaze());
        world.updateDirection(true);
        assertEquals(IWorld.Direction.RIGHT, world.getCurrentDirection());
    }

    @Test
    void updateDirectionLeft() {
        IWorld world = new TextWorld(new EmptyMaze());
        world.updateDirection(false);
        assertEquals(IWorld.Direction.LEFT, world.getCurrentDirection());
    }

    @Test
    void isNewPositionAllowed() {
        IWorld world = new TextWorld(new EmptyMaze());
        assertTrue(world.isNewPositionAllowed(new Position(1,1)));
        assertFalse(world.isNewPositionAllowed(new Position(101,0)));
        assertFalse(world.isNewPositionAllowed(new Position(-101,0)));
        assertFalse(world.isNewPositionAllowed(new Position(0,201)));
        assertFalse(world.isNewPositionAllowed(new Position(0,-201)));
    }

    @Test
    void isAtEdge() {
        IWorld world = new TextWorld(new EmptyMaze());
        assertFalse(world.isAtEdge());
        assertEquals(IWorld.UpdateResponse.SUCCESS,world.updatePosition(200));
        assertTrue(world.isAtEdge());
        assertEquals(IWorld.UpdateResponse.SUCCESS,world.updatePosition(-400));
        assertTrue(world.isAtEdge());
        assertEquals(IWorld.UpdateResponse.SUCCESS,world.updatePosition(200));
        world.updateDirection(true);
        assertEquals(IWorld.UpdateResponse.SUCCESS,world.updatePosition(100));
        assertTrue(world.isAtEdge());
        assertEquals(IWorld.UpdateResponse.SUCCESS,world.updatePosition(-200));
        assertTrue(world.isAtEdge());
    }


    @Test
    void reset() {
        IWorld world = new TextWorld(new DesignedMaze());
        assertEquals(world.updatePosition(200), IWorld.UpdateResponse.FAILED_OBSTRUCTED);
        world.updatePosition(20);
        world.updateDirection(false);
        world.reset();
        assertEquals(IWorld.Direction.UP, world.getCurrentDirection());
        assertEquals(IWorld.CENTRE, world.getPosition());
    }
}
