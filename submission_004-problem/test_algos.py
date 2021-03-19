import unittest
import super_algos

class MyTestCase(unittest.TestCase):
    def test_find_min(self):
        self.assertEqual(super_algos.find_min([9, 80, "b", 5, "A"]), -1)
        self.assertEqual(super_algos.find_min([-90, 80, 2, 5, -2]), -90)
        self.assertEqual(super_algos.find_min([]), -1)
        self.assertEqual(super_algos.find_min([100]), 100)

    def test_find_all(self):
        self.assertEqual(super_algos.sum_all([9, 80, "b", 5, "A"]), -1)
        self.assertEqual(super_algos.sum_all([-90, 80, 2, 5, -2]), -5)
        self.assertEqual(super_algos.sum_all([]), -1)
        self.assertEqual(super_algos.sum_all([100]), 100)

    def test_find_possible_strings(self):
        correct_list = ['aaa', 'aab', 'aba', 'abb', 'baa', 'bab', 'bba', 'bbb']
        self.assertEqual(super_algos.find_possible_strings(['a','b'], 3), correct_list)
        self.assertEqual(super_algos.find_possible_strings(['x','y','z'], 1), ['x','y','z'])
        self.assertEqual(super_algos.find_possible_strings(['5','6','7'], 3), [])
