import random
import sys

def read_file(file_name):
    file = open(file_name,'r')
    return file.readlines()


def get_user_input():
    user_input = input("Guess the missing letter: ").lower()
    #if user_input == "exit" or user_input == "quit":
        #sys.exit("Bye!")
    return user_input


def ask_file_name():
    if len(sys.argv) == 1 and len(sys.argv) < 2 and sys.argv == True:
        words_file = str(sys.argv[1])
        return words_file
    else:
        return 'short_words.txt'


def select_random_word(words):
    random_index = random.randint(0, len(words)-1)
    word = words[random_index].strip()
    return word


# TODO: Step 1 - update to randomly fill in one character of the word only
def random_fill_word(word):
    # TODO
    length = len(word)
    position = random.randint(0,(length-1))
    mod_word = ""
    for i in range(length):
        if i == position:
            mod_word = mod_word + word[position]
            continue
        mod_word = mod_word + "_"
    return mod_word



# TODO: Step 1 - update to check if character is one of the missing characters
def is_missing_char(original_word, answer_word, char):
    length = len(answer_word)
    i = 0
    while i < length:
        if original_word[i] == char and answer_word[i] == "_":
            return True
        else:
            i += 1
    return False


# TODO: Step 1 - fill in missing char in word and return new more complete word
def fill_in_char(original_word, answer_word, char):
    # TODO
    length = len(answer_word)
    i = 0
    char = char.lower()
    #print("The answer is: " + original_word)
    #change answer_word into an array so I can edit string at index
    answer_word_list = list(answer_word)

    while i < length - 1:
        if original_word[i] == char and answer_word_list[i] == "_":
            answer_word_list[i] = char
            i += 1
        else:
            i += 1
    answer_word = ''.join(answer_word_list)
    
    return answer_word


def do_correct_answer(original_word, answer, guess):
    answer = fill_in_char(original_word, answer, guess)
    print(answer)
    return answer


# TODO: Step 4: update to use number of remaining guesses
def do_wrong_answer(answer, number_guesses):
    if number_guesses != 0:
        print('Wrong! Number of guesses left: '+str(number_guesses))
        draw_figure(number_guesses)
    else:
        print('Wrong! Number of guesses left: '+str(number_guesses))
        draw_figure(number_guesses)
        print("Sorry, you are out of guesses. The word was: " + answer)



# TODO: Step 5: draw hangman stick figure, based on number of guesses remaining
def draw_figure(number_guesses):
    if number_guesses == 4:
        print("""/----
|
|
|
|
_______""")
    elif number_guesses == 3:
        print("""/----
|   0
|
|
|
_______""")
    elif number_guesses == 2:
        print("""/----
|   0
|  /|\\
|
|
_______""")
    elif number_guesses == 1:
        print("""/----
|   0
|  /|\\
|   |
|
_______""")
    elif number_guesses == 0:
        print("""/----
|   0
|  /|\\
|   |
|  / \\
_______""")
    

# TODO: Step 2 - update to loop over getting input and checking until whole word guessed
# TODO: Step 3 - update loop to exit game if user types `exit` or `quit`
# TODO: Step 4 - keep track of number of remaining guesses
def run_game_loop(word, answer):
    guesses = 4
    print("Guess the word: "+answer)
    while guesses >= 0:
        guess = get_user_input()
        if guess == "exit" or guess == "quit":
            print("Bye!")
            break
        if is_missing_char(word, answer, guess):
            answer = do_correct_answer(word, answer, guess)
            if answer == word:
                break
        else:
            do_wrong_answer(word, guesses)
            guesses -= 1


# TODO: Step 6 - update to get words_file to use from commandline argument
if __name__ == "__main__":
    words_file = ask_file_name()
    words = read_file(words_file)
    selected_word = select_random_word(words)
    current_answer = random_fill_word(selected_word)

    run_game_loop(selected_word, current_answer)

