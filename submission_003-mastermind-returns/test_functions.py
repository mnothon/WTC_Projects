import unittest
from unittest.mock import patch
from io import StringIO
import mastermind

class MyTestCase(unittest.TestCase):

    def test_create_code(self):
        """
        Test if random num has 4 digits and whether each of the digits
        are between 1 and 8
        """

        for i in range(100):
            random_num = mastermind.create_code()
            self.assertEqual(len(random_num), 4) #tests if random num has 4 digits
            my_test = True
            for digit in random_num:
                if digit < 1 and digit > 8:
                    my_test = False
            self.assertTrue(my_test) #tests if random num is between 1 and 8


    def test_check_correctness(self):
        """
        tests if function returns True or False with different values.
        """
        self.assertEqual(mastermind.check_correctness(5, 3), False)
        self.assertEqual(mastermind.check_correctness(0, 4), True)
        self.assertEqual(mastermind.check_correctness(2, 1), False)
        self.assertEqual(mastermind.check_correctness(3, 2), False)
        self.assertEqual(mastermind.check_correctness(9, 0), False)


    @patch("sys.stdin", StringIO("123\n12345\n1234\n4321"))
    def test_get_answer_input(self):
        """
        tests if function returns the expected value and prompts you to
        enter correct one if you input the incorrect value.
        """
        self.assertEqual(mastermind.get_answer_input(), '1234')
        self.assertEqual(mastermind.get_answer_input(), '4321')


    @patch("sys.stdin", StringIO("123\n12345\n1234\n1234\n1234\n1234\n"))
    def test_take_turn(self):
        """
        tests if function returns the expected tuple if you input
        a particular list.
        """
        self.assertEqual(mastermind.take_turn([1,2,3,4]), (4,0))
        self.assertEqual(mastermind.take_turn([1,3,8,2]), (1,2))
        self.assertEqual(mastermind.take_turn([1,2,3,8]), (3,0))
        self.assertEqual(mastermind.take_turn([4,3,2,1]), (0,4))


if __name__ == '__main__':
    unittest.main()