package za.co.wethinkcode.mastermind;

import java.util.Random;

public class CodeGenerator {
    private final Random random;

    public CodeGenerator(){
        this.random = new Random();
    }

    public CodeGenerator(Random random){
        this.random = random;
    }

    /**
     * Generates a random 4 digit code, using this.random, where each digit is in the range 1 to 8 only.
     * Duplicated digits are allowed.
     * @return the generated 4-digit code
     */
    public int generateDigit() {
        return this.random.nextInt(8) + 1;
    }

    public String generateCode(){
        //TODO: implement using this.random
        StringBuilder code = new StringBuilder();
        int digit;
        for(int i = 0; i < 4; i++) {
            boolean codeNotUnique = true;
            while(codeNotUnique) {
                digit = generateDigit();
                if(code.toString().contains(String.valueOf(digit)) != true) {
                    codeNotUnique = false;
                    code.append(digit);
                }
            }
        }
        System.out.println("4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.");
        return code.toString();
    }
}
