package za.co.wethinkcode.toyrobot;


import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    void initialPosition() {
        Robot robot = new Robot("CrashTestDummy");
        robot.setWorld(new TextWorld(new EmptyMaze()));
        assertEquals(Robot.CENTRE, robot.getWorld().getPosition());
        assertEquals(IWorld.Direction.UP, robot.getWorld().getCurrentDirection());
    }

    @Test
    void dump() {
        Robot robot = new Robot("CrashTestDummy");
        robot.setWorld(new TextWorld(new EmptyMaze()));
        assertEquals("[0,0] CrashTestDummy> Ready", robot.toString());
    }

    @Test
    void shutdown() {
        Robot robot = new Robot("CrashTestDummy");
        ShutdownCommand command = new ShutdownCommand();
        assertFalse(robot.handleCommand(command));
    }

    @Test
    void forward() {
        Robot robot = new Robot("CrashTestDummy");
        robot.setWorld(new TextWorld(new EmptyMaze()));
        ForwardCommand command = new ForwardCommand("10");
        assertTrue(robot.handleCommand(command));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() + 10);
        assertEquals(expectedPosition, robot.getWorld().getPosition());
        assertEquals("Moved forward by 10 steps.", robot.getStatus());
    }

    @Test
    void forwardforward() {
        Robot robot = new Robot("CrashTestDummy");
        robot.setWorld(new TextWorld(new EmptyMaze()));
        assertTrue(robot.handleCommand(new ForwardCommand("10")));
        assertTrue(robot.handleCommand(new ForwardCommand("5")));
        assertEquals("Moved forward by 5 steps.", robot.getStatus());
    }

    @Test
    void tooFarForward() {
        Robot robot = new Robot("CrashTestDummy");
        robot.setWorld(new TextWorld(new EmptyMaze()));
        assertTrue(robot.handleCommand(new ForwardCommand("1000")));
        assertEquals(Robot.CENTRE, robot.getWorld().getPosition());
        assertEquals("Sorry, I cannot go outside my safe zone.", robot.getStatus());
    }

//    @Test
//    void help() {
//        Robot robot = new Robot("CrashTestDummy");
//        Command command = new HelpCommand();
//        assertTrue(robot.handleCommand(command));
//        assertEquals("I can understand these commands:\n" +
//                "OFF  - Shut down robot\n" +
//                "HELP - provide information about commands\n" +
//                "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'", robot.getStatus());
//    }
}