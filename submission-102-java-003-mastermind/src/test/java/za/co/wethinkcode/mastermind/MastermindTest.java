package za.co.wethinkcode.mastermind;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

import org.mockito.Mockito;
import za.co.wethinkcode.mastermind.CodeGenerator;
import za.co.wethinkcode.mastermind.Mastermind;
import za.co.wethinkcode.mastermind.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

class MastermindTest {
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

    @Test
    void testInvalidInput() {
        Random randomNumberMock = Mockito.mock(Random.class);
        Mockito.when(randomNumberMock.nextInt(anyInt())).thenReturn(0, 1, 2, 3);

        InputStream mockedInput = new ByteArrayInputStream("4321\n1234\n".getBytes());
        Mastermind mastermind = new Mastermind(new CodeGenerator(randomNumberMock), new Player(mockedInput));
        mastermind.runGame();
        assertEquals("4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.\n" +
                "Input 4 digit code:\n" +
                "Number of correct digits in correct place: 0\n" +
                "Number of correct digits not in correct place: 4\n" +
                "Turns left: 11\n" +
                "Input 4 digit code:\n" +
                "Number of correct digits in correct place: 4\n" +
                "Number of correct digits not in correct place: 0\n" +
                "Congratulations! You are a codebreaker!\n" +
                "The code was: 1234", outputStreamCaptor.toString().trim());
    }

    @Test
    void testValidCompareAnswers() {
        Mastermind mastermind = new Mastermind();

        boolean result = mastermind.compareAnswers("1234", "1324");
        assertEquals(false, result);
    }

    @Test
    void testInvalidCompareAnswers() {
        Mastermind mastermind = new Mastermind();

        boolean result = mastermind.compareAnswers("1234", "1234");
        assertEquals(true, result);

    }

}
