package za.co.wethinkcode.mastermind;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    public void testValidGuessLetters() {
        Player player = new Player();

        boolean result = player.checkGuess("34b2");
        assertEquals(false, result);
    }

    @Test
    public void testValidGuess5Digits() {
        Player player = new Player();

        boolean result = player.checkGuess("35381");
        assertEquals(false, result);
    }

    @Test
    public void testValidGuessOutOfRangeDigits() {
        Player player = new Player();

        boolean result = player.checkGuess("0129");
        assertEquals(false, result);
    }

    @Test
    public void testValidGuessCorrectInput() {
        Player player = new Player();

        boolean result = player.checkGuess("1234");
        assertEquals(true, result);
    }


}
