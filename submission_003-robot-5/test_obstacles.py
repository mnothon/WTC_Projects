import unittest
from maze import ejohns_maze as obstacles
import robot

class MyTestCase(unittest.TestCase):
    
    def test_is_position_blocked(self):
        obstacles.obstacles =  [(1,1)]
        
        output = obstacles.is_position_blocked(4,4)
        self.assertEqual(output, True)

        output = obstacles.is_position_blocked(6,6)
        self.assertFalse(output)


    def test_is_path_blocked(self):
        """
        This test generates a random obstacle at position (1,1)
        and tests whether the function properly returns whether there
        is an obstacle in the way of the given path.
        """
        obstacles.obstacles =  [(1,1)]
             
        output = obstacles.is_path_blocked(-10,2,10,2)
        self.assertEqual(output, True)

        output1 = obstacles.is_path_blocked(4,-5,4,10)
        self.assertEqual(output1, True)

        output2 = obstacles.is_path_blocked(5,-20,5,10)
        self.assertEqual(output2, True)

        output3 = obstacles.is_path_blocked(6,-20,6,10)
        self.assertFalse(output3, False)

        output4 = obstacles.is_path_blocked(-20,6,2,6)
        self.assertFalse(output4)

        output5 = obstacles.is_path_blocked(-20,5,2,5)
        self.assertEqual(output5, True)


if __name__ == '__main__':
    unittest.main()