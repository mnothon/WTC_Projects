import unittest
import word_processor

class MyTestCase(unittest.TestCase):
    def test_convert_to_word_list_overall_test(self):
        my_string = "But soft! What light through yonder window breaks? It is the east, and Juliet is the sun."
        expected_output = ['but', 'soft', 'what', 'light', 'through', 'yonder', 'window', 'breaks',
         'it', 'is', 'the', 'east', 'and', 'juliet', 'is', 'the', 'sun']
        self.assertEqual(word_processor.convert_to_word_list(my_string), expected_output)


    def test_convert_to_word_list_empty_list(self):
        self.assertEqual(word_processor.convert_to_word_list(""), [])


    def test_words_lengths_map_overall_test(self):
        my_string = "But soft! What light through yonder window breaks? It is the east, and Juliet is the sun."
        expected_output = {2: 3, 3: 5, 4: 3, 5: 1, 6: 4, 7: 1}
        self.assertEqual(word_processor.words_lengths_map(my_string), expected_output)


    def test_words_lengths_map_empty_string(self):
        self.assertEqual(word_processor.words_lengths_map(""), {})

    def test_letters_count_map_overall_test(self):
        my_string = "But soft! What light through yonder window breaks? It is the east, and Juliet is the sun."
        expected_output = {'a': 4, 'b': 2, 'c': 0, 'd': 3, 'e': 6, 'f': 1, 'g': 2, 'h': 6, 'i': 6, 'j': 1, 'k': 1,
         'l': 2, 'm': 0, 'n': 4, 'o': 4, 'p': 0, 'q': 0, 'r': 3, 's': 6, 't': 10, 'u': 4, 'v': 0, 'w': 3, 'x': 0, 'y': 1, 'z': 0}
        self.assertEqual(word_processor.letters_count_map(my_string), expected_output)

    
    def test_letters_count_map_empty_string(self):
        expected_output = {'a': 0, 'b': 0, 'c': 0, 'd': 0, 'e': 0, 'f': 0, 'g': 0, 'h': 0, 'i': 0, 'j': 0, 'k': 0, 
         'l': 0, 'm': 0, 'n': 0, 'o': 0, 'p': 0, 'q': 0, 'r': 0, 's': 0, 't': 0, 'u': 0, 'v': 0, 'w': 0, 'x': 0, 'y': 0, 'z': 0}
        self.assertEqual(word_processor.letters_count_map(""), expected_output)


    def test_most_used_character_overall(self):
        my_string = "But soft! What light through yonder window breaks? It is the east, and Juliet is the sun."
        self.assertEqual(word_processor.most_used_character(my_string), "t")


if __name__ == "__main__":
     unittest.main()