import random

def get_random_nums():
    random_num = []
    for i in range(4):
        num = random.randint(1,8)
        # loops until num is unique
        while num in random_num:
            num = random.randint(1,8)
        random_num.append(num)
    print("4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.")
    return random_num

def guess_digits():
    guess = []
    my_input = input("Input 4 digit code: ")
    guess = list(my_input)
    # converts strings in list to ints
    for i in range(0, len(guess)): 
        guess[i] = int(guess[i])
    test = False

    int(my_input[0])

    while test == False:
        if len(guess) == 4:
            for i in range(len(guess)):
                if guess[i] > 8 or guess[i] < 1:
                    my_input = input("Input 4 digit code: ")
                    guess = list(my_input)
                    for i in range(0, len(guess)): 
                        guess[i] = int(guess[i])
                    test = False
                    break
                else:
                    test = True
        else:
            print("Please enter exactly 4 digits.")
            my_input = input("Input 4 digit code: ")
            guess = list(my_input)
            for i in range(0, len(guess)): 
                guess[i] = int(guess[i])
    return guess

def compare_guess_and_answer(answer, guess):
    tracking = 11
    while tracking > 0:
        num_corr = 0
        num_not_corr = 0
        if answer == guess:
            # changes answer from list of ints to string
            s = [str(i) for i in answer]
            num_corr = 4
            print(f'Number of correct digits in correct place:     {num_corr}')
            print(f'Number of correct digits not in correct place: {num_not_corr}')
            print(f'Congratulations! You are a codebreaker!\nThe code was: {int("".join(s))}')
            break
        else:
            for i in range(len(answer)):
                if guess[i] in answer:
                    if guess[i] == answer[i]:
                        num_corr += 1
                        continue
                    num_not_corr += 1
            print(f'Number of correct digits in correct place:     {num_corr}')
            print(f'Number of correct digits not in correct place: {num_not_corr}')
            print(f'Turns left: {tracking}')
            tracking -= 1
            guess = guess_digits()  

def run_game():
    """
    TODO: implement Mastermind code here
    """
    right_answer = get_random_nums()
    user_guess = guess_digits()
    compare_guess_and_answer(right_answer, user_guess)
    pass

if __name__ == "__main__":
    run_game()
