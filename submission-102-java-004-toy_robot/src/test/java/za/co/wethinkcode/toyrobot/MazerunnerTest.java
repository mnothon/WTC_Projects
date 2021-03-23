package za.co.wethinkcode.toyrobot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazerunnerTest {

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
    void testMazerunTop() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nmazerun top\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,200] HAL> Moved forward by 200 steps.",
                "[0,200] HAL> I am at the top edge. (Cost: 1 steps)",
                "HAL> What must I do next?",
                "[0,200] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
    }


    @Test
    void testMazerunBottom() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nmazerun bottom\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,0] HAL> Turned right.",
                "[0,0] HAL> Turned right.",
                "[0,-200] HAL> Moved forward by 200 steps.",
                "[0,-200] HAL> I am at the bottom edge. (Cost: 3 steps)",
                "HAL> What must I do next?",
                "[0,-200] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
    }


    @Test
    void testMazerunRight() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nmazerun right\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,0] HAL> Turned right.",
                "[100,0] HAL> Moved forward by 100 steps.",
                "[100,0] HAL> I am at the right edge. (Cost: 2 steps)",
                "HAL> What must I do next?",
                "[100,0] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
    }

    @Test
    void testMazerunLeft() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nmazerun left\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"HAL> What must I do next?",
                "[0,0] HAL> Turned left.",
                "[-100,0] HAL> Moved forward by 100 steps.",
                "[-100,0] HAL> I am at the left edge. (Cost: 2 steps)",
                "HAL> What must I do next?",
                "[-100,0] HAL> Shutting down..."};
        verifyOutput(actualOutput, expectedOutput);
    }


}
