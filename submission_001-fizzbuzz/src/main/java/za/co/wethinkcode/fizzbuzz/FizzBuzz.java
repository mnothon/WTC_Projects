package za.co.wethinkcode.fizzbuzz;

public class FizzBuzz {
    public String checkNumber(int number) {
        boolean divisibleBy3 = number % 3 == 0;
        boolean divisibleBy5 = number % 5 == 0;

        if ( divisibleBy3 && divisibleBy5 ) {
            return "FizzBuzz";
        }
        if (divisibleBy5) {
            return "Buzz";
        }
        if (divisibleBy3) {
            return "Fizz";
        } else {
            return String.valueOf(number);
        }
    }

    public String countTo(int number) {
        StringBuilder answer = new StringBuilder();
        answer.append("[");

        for(int i=1; i<number+1; i++) {
            answer.append(checkNumber(i));
            if(i == number) {
                answer.append("]");
            } else {
                answer.append(", ");
            }
        }
        return answer.toString();
    }
}
