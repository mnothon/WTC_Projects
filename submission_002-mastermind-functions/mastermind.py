import random

def generate_random_num():
    """
    Generates a 4 random digits between 1 and 8 and saves them into list.
    Returns a list of ints
    """
    code = [0,0,0,0]
    for i in range(4):
        value = random.randint(1, 8) # 8 possible digits
        while value in code:
            value = random.randint(1, 8)  # 8 possible digits
        code[i] = value
    return code


def print_welcome_code():
    """
    prints message about secret code being set
    """
    print('4-digit Code has been set. Digits in range 1 to 8. You have 12 turns to break it.')


def get_user_guess():
    """
    gets user to input 4 digits and saves them to a list.
    Returns a list of ints.
    """
    user_guess = input("Input 4 digit code: ")
    while len(user_guess) < 4 or len(user_guess) > 4:
        print("Please enter exactly 4 digits.")
        user_guess = input("Input 4 digit code: ")
    return user_guess


def track_positions(answer, code):
    """
    compares answer with the users guesss and prints out num of correct digits in correct place etc.
    Returns 2 ints.
    """
    global correct
    correct_digits_and_position = 0
    correct_digits_only = 0
    for i in range(len(answer)):
        if code[i] == int(answer[i]):
            correct_digits_and_position += 1
        elif int(answer[i]) in code:
            correct_digits_only += 1
    print('Number of correct digits in correct place:     '+str(correct_digits_and_position))
    print('Number of correct digits not in correct place: '+str(correct_digits_only))
    return correct_digits_and_position, correct_digits_only


def game_tracking(answer, code):
    """
    keeps track of the number of times user inputs the guess and ends the game if
    inputs correct code.
    Doesn't return anything. Game will end once this function ends
    """
    correct = False
    turns = 0
    while not correct and turns < 12:
        correct_digits_counter = track_positions(answer, code)
        turns += 1
        if correct_digits_counter[0] == 4:
            correct = True
            print('Congratulations! You are a codebreaker!')
        else:
            print('Turns left: '+str(12 - turns))
            answer = get_user_guess()
    print('The code was: '+str(code))


def run_game():
    code = generate_random_num()
    print_welcome_code()
    answer = get_user_guess()
    game_tracking(answer, code)


if __name__ == "__main__":
    run_game()
