
def split(delimiters, text):
    """
    Splits a string using all the delimiters supplied as input string
    :param delimiters:
    :param text: string containing delimiters to use to split the string, e.g. `,;? `
    :return: a list of words from splitting text using the delimiters
    """

    import re
    regex_pattern = '|'.join(map(re.escape, delimiters))
    return re.split(regex_pattern, text, 0)


def convert_to_word_list(text):
    """
    Function takes in a string of words converts them to a list and removes any characters
    that aren't letters and makes all words lower case.
    
    Parameters:
        arg1 (str): The string that has to be converted to a list

    Returns:
        list: List of each of the words in the string without any special characters
        and also with lowercase.
    """

    text_list = split(' '','';''.''?''!', text)
    return [word.lower() for word in text_list if word != '']
    
    
def words_longer_than(length, text):
    """
    Function takes in a string of words converts them to a list without special
    characters then removes words that are shorter than a certain length.

    Parameters:
        arg1 (int): The length of the words that have to be returned
        arg2 (str): The string of words that will be processed by the function.

    Return:
        list: List of words that are longer than the length passed into the parameter.
    """

    return [word for word in convert_to_word_list(text) if len(word) > length]


def find_sum(length, words_list):
    """
    Helper function returns sum of words that are equal to length
    """
    my_sum = [1 if len(word)==length else 0 for word in words_list]
    return sum(my_sum)


def words_lengths_map(text):
    """
    Function takes in string of words and returns a dictionary with the key 
    being the length of a word and the value of the key being how many times
    a word of that length appears in the string.

    Parameters:
        arg1 (str): The string that will be evaluated by the function

    Return:
        dict: Dictionary with the key being the different lengths of the words
        in the string. And the value being the number of times a word of that length
        appears in the string.
    """

    words_list = convert_to_word_list(text)
    if words_list == []:
        return {}
    longest_string = len(max(words_list, key=len))
    return {i:find_sum(i, words_list) for i in range(1, (longest_string + 1)) if find_sum(i, words_list) != 0}
    

def get_alphabet_characters():
    """Returns a list with the alphabet in it"""    
    return list(map(chr,range(ord('a'),ord('z')+1)))


def letters_count_map(text):
    """
    Function takes in string and returns a map with the key being a letter in the
    alphabet and the value being the number of times that letter appears in the
    string

    Parameters:
        arg1 (str): A string, preferably of words, that will be counted.

    Return:
        dict: Returns a dictionary with each key being the letter of the alphabet
        and each value being the number of times that letter appears in the string.
    """

    alphabet = get_alphabet_characters()
    words_as_string = ''.join(convert_to_word_list(text))
    return {letter:words_as_string.count(letter) for letter in alphabet}


def most_used_character(text):
    """
    Function takes in a string and returns the letter that appears the most 
    in the string

    Parameters:
        arg1: (str): A string, preferably of words, that will be counted.

    Returns:
        char: Returns a letter that appears the most often in the string.
    """
    my_dict = letters_count_map(text)
    biggest = 0
    if text == "":
        return None
    inverse = [(value,key) for key,value in my_dict.items()]
    return max(inverse)[1]