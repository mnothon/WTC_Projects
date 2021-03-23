package za.co.wethinkcode.toyrobot;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {
    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setIn(standardIn);
    }

    private void verifyOutput(String[] actualOutput, String[] expectedOutput) {
        for (int i = actualOutput.length - 1, j = expectedOutput.length - 1; j > 0; i--, j--) {
            assertEquals(expectedOutput[j], actualOutput[i]);
        }
    }

    @Test
    void getShutdownName() {
        Command test = new ShutdownCommand();
        assertEquals("off", test.getName());
    }

    @Test
    void executeShutdown() {
        Robot robot = new Robot("CrashTestDummy");
        Command shutdown = Command.create("shutdown");
        assertFalse(shutdown.execute(robot));
        assertEquals("Shutting down...", robot.getStatus());
    }

    @Test
    void getForwardName() {
        ForwardCommand test = new ForwardCommand("100");
        assertEquals("forward", test.getName());
        assertEquals("100", test.getArgument());
    }

    @Test
    void executeForward() {
        Robot robot = new Robot("CrashTestDummy");
        robot.setWorld(new TextWorld(new EmptyMaze()));
        Command forward100 = Command.create("forward 10");
        assertTrue(forward100.execute(robot));
        Position expectedPosition = new Position(Robot.CENTRE.getX(), Robot.CENTRE.getY() + 10);
        assertEquals(expectedPosition, robot.getWorld().getPosition());
        assertEquals("Moved forward by 10 steps.", robot.getStatus());
    }

    @Test
    void getHelpName() {
        Command test = new HelpCommand();
        assertEquals("help", test.getName());
    }


    @Test
    void createCommand() {
        Command forward = Command.create("forward 10");
        assertEquals("forward", forward.getName());
        assertEquals("10", forward.getArgument());

        Command shutdown = Command.create("shutdown");
        assertEquals("off", shutdown.getName());

        Command help = Command.create("help");
        assertEquals("help", help.getName());
    }

    @Test
    void createInvalidCommand() {
        try {
            Command forward = Command.create("say hello");
            fail("Should have thrown an exception");
        } catch (IllegalArgumentException e) {
            assertEquals("Unsupported command: say hello", e.getMessage());
        }
    }

    @Test
    void executeRight() {
        Robot robot = new Robot("CrashTestDummy");
        robot.setWorld(new TextWorld(new EmptyMaze()));
        Command turnRight = Command.create("right");
        assertTrue(turnRight.execute(robot));
        assertEquals(IWorld.Direction.RIGHT, robot.getWorld().getCurrentDirection());

        assertTrue(turnRight.execute(robot));
        assertEquals(IWorld.Direction.DOWN, robot.getWorld().getCurrentDirection());

        assertTrue(turnRight.execute(robot));
        assertEquals(IWorld.Direction.LEFT, robot.getWorld().getCurrentDirection());

        assertTrue(turnRight.execute(robot));
        assertEquals(IWorld.Direction.UP, robot.getWorld().getCurrentDirection());

        assertEquals("Turned right.", robot.getStatus());

    }

    @Test
    void executeLeft() {
        Robot robot = new Robot("CrashTestDummy");
        robot.setWorld(new TextWorld(new EmptyMaze()));
        Command turnLeft = Command.create("left");
        assertTrue(turnLeft.execute(robot));
        assertEquals(IWorld.Direction.LEFT, robot.getWorld().getCurrentDirection());

        assertTrue(turnLeft.execute(robot));
        assertEquals(IWorld.Direction.DOWN, robot.getWorld().getCurrentDirection());

        assertTrue(turnLeft.execute(robot));
        assertEquals(IWorld.Direction.RIGHT, robot.getWorld().getCurrentDirection());

        assertTrue(turnLeft.execute(robot));
        assertEquals(IWorld.Direction.UP, robot.getWorld().getCurrentDirection());

        assertEquals("Turned left.", robot.getStatus());

    }

    @Test
    void executeTurnLeftThenBack() {
        Robot robot = new Robot("CrashTestDummy");
        robot.setWorld(new TextWorld(new EmptyMaze()));
        Command turnLeft = Command.create("left");
        assertTrue(turnLeft.execute(robot));
        Command back10 = Command.create("back 10");
        assertTrue(back10.execute(robot));
        Position expectedPosition = new Position(Robot.CENTRE.getX() + 10, Robot.CENTRE.getY());
        assertEquals(expectedPosition, robot.getWorld().getPosition());
    }

    @Test
    void testSprint() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nsprint 4\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,4] HAL> Moved forward by 4 steps.",
                "[0,7] HAL> Moved forward by 3 steps.",
                "[0,9] HAL> Moved forward by 2 steps.",
                "[0,10] HAL> Moved forward by 1 steps.",
                "HAL> What must I do next?",
                "[0,10] HAL> Shutting down..."};
        for (String element: actualOutput) {
            System.out.println(element);
        };
        verifyOutput(actualOutput, expectedOutput);
    }

    @Test
    void testDoReplayNoArgs() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nforward 10\nforward 15\nreplay\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,10] HAL> Moved forward by 10 steps.",
                "HAL> What must I do next?",
                "[0,25] HAL> Moved forward by 15 steps.",
                "HAL> What must I do next?",
                "[0,35] HAL> Moved forward by 10 steps.",
                "[0,50] HAL> Moved forward by 15 steps.",
                "[0,50] HAL> replayed 2 commands.",
                "HAL> What must I do next?",
                "[0,50] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
    }

    @Test
    void testReplayLast2Args() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nforward 10\nforward 15\nback 25\nreplay 2\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,10] HAL> Moved forward by 10 steps.",
                "HAL> What must I do next?",
                "[0,25] HAL> Moved forward by 15 steps.",
                "HAL> What must I do next?",
                "[0,0] HAL> Moved back by 25 steps.",
                "HAL> What must I do next?",
                "[0,15] HAL> Moved forward by 15 steps.",
                "[0,-10] HAL> Moved back by 25 steps.",
                "[0,-10] HAL> replayed 2 commands.",
                "HAL> What must I do next?",
                "[0,-10] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
    }

    @Test
    void testReplayRange() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nforward 5\nforward 10\nforward 15\nforward 20\nreplay 4-2\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,5] HAL> Moved forward by 5 steps.",
                "HAL> What must I do next?",
                "[0,15] HAL> Moved forward by 10 steps.",
                "HAL> What must I do next?",
                "[0,30] HAL> Moved forward by 15 steps.",
                "HAL> What must I do next?",
                "[0,50] HAL> Moved forward by 20 steps.",
                "HAL> What must I do next?",
                "[0,55] HAL> Moved forward by 5 steps.",
                "[0,65] HAL> Moved forward by 10 steps.",
                "[0,65] HAL> replayed 2 commands.",
                "HAL> What must I do next?",
                "[0,65] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
    }

    @Test
    void testReplayNoArgReversed() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nforward 5\nforward 10\nreplay reversed\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,5] HAL> Moved forward by 5 steps.",
                "HAL> What must I do next?",
                "[0,15] HAL> Moved forward by 10 steps.",
                "HAL> What must I do next?",
                "[0,25] HAL> Moved forward by 10 steps.",
                "[0,30] HAL> Moved forward by 5 steps.",
                "[0,30] HAL> replayed 2 commands.",
                "HAL> What must I do next?",
                "[0,30] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
        System.out.println(actualOutput);
    }

    @Test
    void testReplayLast2Reversed() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nforward 5\nforward 10\nforward 15\nreplay reversed 2\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,5] HAL> Moved forward by 5 steps.",
                "HAL> What must I do next?",
                "[0,15] HAL> Moved forward by 10 steps.",
                "HAL> What must I do next?",
                "[0,30] HAL> Moved forward by 15 steps.",
                "HAL> What must I do next?",
                "[0,45] HAL> Moved forward by 15 steps.",
                "[0,55] HAL> Moved forward by 10 steps.",
                "[0,55] HAL> replayed 2 commands.",
                "HAL> What must I do next?",
                "[0,55] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
    }

    @Test
    void testReplayRangeReversed() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nforward 5\nforward 10\nforward 15\nforward 20\nreplay reversed 4-2\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,5] HAL> Moved forward by 5 steps.",
                "HAL> What must I do next?",
                "[0,15] HAL> Moved forward by 10 steps.",
                "HAL> What must I do next?",
                "[0,30] HAL> Moved forward by 15 steps.",
                "HAL> What must I do next?",
                "[0,50] HAL> Moved forward by 20 steps.",
                "HAL> What must I do next?",
                "[0,60] HAL> Moved forward by 10 steps.",
                "[0,65] HAL> Moved forward by 5 steps.",
                "[0,65] HAL> replayed 2 commands.",
                "HAL> What must I do next?",
                "[0,65] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
    }
}
