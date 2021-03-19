package za.co.wethinkcode.mastermind;

import org.junit.platform.commons.util.StringUtils;

public class Mastermind {
    private final String code;
    private final Player player;

    public Mastermind(CodeGenerator generator, Player player){
        this.code = generator.generateCode();
        this.player = player;
    }

    public Mastermind(){
        this(new CodeGenerator(), new Player());
    }

    public void runGame(){
        //TODO: implement the main run loop logic
        boolean rightAnswer;
        while(player.getGuessesLeft() > 0) {
            String guess = player.getGuess();

            rightAnswer = compareAnswers(code, guess);
            if(rightAnswer) {
                doRightAnswer();
                break ;
            }
            player.trackGuesses();
            if(player.getGuessesLeft() != 0) {
                doWrongAnswer(player);
            }
        }
        if(player.getGuessesLeft() == 0){
            revealAnswer(code);
        }
    }

    public void printNumOfGuesses(int[] intArray) {
        System.out.println("Number of correct digits in correct place: "+intArray[0]);
        System.out.println("Number of correct digits not in correct place: "+intArray[1]);
    }

    public void doRightAnswer(){
        System.out.println("Congratulations! You are a codebreaker!");
        System.out.println("The code was: "+this.code);
    }

    public void doWrongAnswer(Player player){
        System.out.println("Turns left: "+player.getGuessesLeft());
    }

    public void revealAnswer(String code){
        System.out.println("No more turns left.");
        System.out.println("The code was: "+code);
    }

    public boolean compareAnswers(String code, String guess) {
        int numInCorrPlace = 0;
        int numNotInCorrPlace = 0;

        for(int i = 0; i < 4; i++) {
            if(code.charAt(i) == guess.charAt(i)) {
                numInCorrPlace++;
            } else if(code.charAt(i) != guess.charAt(i) && code.indexOf(guess.charAt(i)) != -1) {
                for(int d =0 ; d < 4 ; d++){
                    int charCount=0;
                    if(guess.charAt(i) == code.charAt(d)){
                        charCount++;
                    }
                    if(charCount == 1) {
                        numNotInCorrPlace++;
                    }
                }
            }
        }

        printNumOfGuesses(new int[]{numInCorrPlace, numNotInCorrPlace});

        if (code.equals(guess)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args){
        Mastermind game = new Mastermind();
        game.runGame();
    }
}
