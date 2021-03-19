package za.co.wethinkcode.examples.hangman;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class HangmanTest {

    private void simulateGame(String simulatedUserInput, String expectedLastLine) {
        InputStream simulatedInputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(simulatedInputStream);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        try {
            Hangman.main(new String[]{});
        } catch (IOException e) {
            fail("Not expecting an exception.");
        }

        String[] linesOutput = outputStreamCaptor.toString().split("\n");
        String lastLine = linesOutput[linesOutput.length - 1];
        assertEquals(expectedLastLine, lastLine);
    }

    @Test
    public void shouldWinTheGame() {
        String simulatedUserInput = "oneword.txt\nt\ne\ns\n";
        String expectedOutput = "That is correct! You escaped the noose .. this time.";
        simulateGame(simulatedUserInput, expectedOutput);
    }

    @Test
    public void shouldLoseTheGame() {
        String simulatedUserInput = "oneword.txt\na\nb\nc\nd\nx\n";
        String expectedOutput = "Sorry, you are out of guesses. The word was: test";
        simulateGame(simulatedUserInput, expectedOutput);
    }
}
