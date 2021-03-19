import unittest
from world import obstacles
import robot

class MyTestCase(unittest.TestCase):
    def test_create_obstacle(self):
        obstacles.random.randint = lambda a, b: 1
        output = obstacles.create_obstacle()
        self.assertEqual(output, (1,1))


    def test_does_it_overlap(self):
        output = obstacles.does_it_overlap([(1,1)], (2,2))
        self.assertEqual(output, True)

        output1 = obstacles.does_it_overlap([(1,1)], (3,3))
        self.assertEqual(output1, True)
        
        output1 = obstacles.does_it_overlap([(1,1)], (4,4))
        self.assertEqual(output1, True)

        output1 = obstacles.does_it_overlap([(1,1)], (5,5))
        self.assertEqual(output1, True)

        output1 = obstacles.does_it_overlap([(1,1)], (6,6))
        self.assertEqual(output1, False)


    def test_get_obstacles(self):
        obstacles.random.randint = lambda a, b: 1
        
        output = obstacles.get_obstacles()
        self.assertEqual(output, [(1, 1)])


    def test_is_position_blocked(self):
        obstacles.random.randint = lambda a, b: 1
        obstacles.get_obstacles()

        output = obstacles.is_position_blocked(4,4)
        self.assertEqual(output, True)

        output = obstacles.is_position_blocked(6,6)
        self.assertEqual(output, False)


    def test_is_path_blocked(self):
        """
        This test generates a random obstacle at position (1,1)
        and tests whether the function properly returns whether there
        is an obstacle in the way of the given path.
        """
        
        obstacles.random.randint = lambda a, b: 1
        obstacles.get_obstacles()
                
        output = obstacles.is_path_blocked(-10,2,10,2)
        self.assertEqual(output, True)

        output1 = obstacles.is_path_blocked(4,-5,4,10)
        self.assertEqual(output1, True)

        output2 = obstacles.is_path_blocked(5,-20,5,10)
        self.assertEqual(output2, True)

        output3 = obstacles.is_path_blocked(6,-20,6,10)
        self.assertEqual(output3, False)

        output4 = obstacles.is_path_blocked(-20,6,2,6)
        self.assertEqual(output4, False)

        output5 = obstacles.is_path_blocked(-20,5,2,5)
        self.assertEqual(output5, True)


if __name__ == '__main__':
    unittest.main()