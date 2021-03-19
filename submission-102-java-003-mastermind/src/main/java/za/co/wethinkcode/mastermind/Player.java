package za.co.wethinkcode.mastermind;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;


public class Player {
    private final Scanner inputScanner;
    private int guessesLeft = 12;

    public Player(){
        this.inputScanner = new Scanner(System.in);
//        this.guessesLeft = 11;
    }

    public Player(InputStream inputStream){
        this.inputScanner = new Scanner(inputStream);
//        this.guessesLeft = 11;
    }

    /**
     * Gets a guess from user via text console.
     * This must prompt the user to re-enter a guess until a valid 4-digit string is entered, or until the user enters `exit` or `quit`.
     *
     * @return the value entered by the user
     */
    public String getGuess(){
        String guess;
        System.out.println("Input 4 digit code:");
        guess = inputScanner.nextLine();
        while(!checkGuess(guess)) {
            System.out.println("Please enter exactly 4 digits (each from 1 to 8).");
            System.out.println("Input 4 digit code:");
            guess = inputScanner.nextLine();
        }
        return guess;
    }

    public void trackGuesses() {
        this.guessesLeft = guessesLeft - 1;
    }

    public int getGuessesLeft() {
        return guessesLeft;
    }

    public void shutdownGame(String guess) {
        if(guess.toLowerCase().equals("exit") || guess.toLowerCase().equals("quit")) {
            System.exit(0);
        }
    }

    public boolean checkGuess(String guess) {
        int length = String.valueOf(guess).length();
        shutdownGame(guess);
        if(length != 4) {
            return false;
        }
        for(int i = 0; i < 4; i++) {
            int digit = Character.getNumericValue(guess.charAt(i));
            if(digit < 1 || digit > 8) {
                return false;
            }
            if(Character.isDigit(guess.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }
}
