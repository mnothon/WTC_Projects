import unittest
from world.text import world
import robot
from maze import obstacles as crazy_maze

class MyTestCase(unittest.TestCase):
    
    def test_is_position_allowed(self):
        world.position_x = 1
        world.position_y = 0
        crazy_maze.random.randint = lambda a, b: 1
        crazy_maze.create_obstacles(0, 0, 0, 0)

        output = world.is_position_allowed(1,3, world.position_x, world.position_y)
        self.assertEqual(output, False)


    def test_update_position_when_it_hits_from_bottom(self):
        world.position_x = 1
        world.position_y = 0
        current_direction_index = 0
        crazy_maze.random.randint = lambda a, b: 1
        crazy_maze.create_obstacles(0, 0, 0, 0)

        output = world.update_position(5)
        self.assertEqual(output, False)


    def test_update_position_when_it_hits_from_right(self):
        world.position_x = -2
        world.position_y = 1
        world.current_direction_index = 1
        crazy_maze.random.randint = lambda a, b: 1
        crazy_maze.create_obstacles(0, 0, 0, 0)

        output = world.update_position(5)
        self.assertEqual(output, False)


    def test_update_position_when_it_hits_from_left(self):
        world.position_x = 3
        world.position_y = 1
        world.current_direction_index = 3
        crazy_maze.random.randint = lambda a, b: 1
        crazy_maze.create_obstacles(0, 0, 0, 0)

        output = world.update_position(5)
        self.assertEqual(output, False)


    def test_update_position_when_it_hits_from_top(self):
        world.position_x = 1
        world.position_y = 3
        world.current_direction_index = 2
        crazy_maze.random.randint = lambda a, b: 1
        crazy_maze.create_obstacles(0, 0, 0, 0)

        output = world.update_position(5)
        self.assertEqual(output, False)


if __name__ == '__main__':
    unittest.main()
        