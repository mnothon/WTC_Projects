import unittest
from world.text import world
import robot
from world import obstacles

class MyTestCase(unittest.TestCase):
    
    def test_is_position_allowed(self):
        world.position_x = 3
        world.position_y = 0
        obstacles.random.randint = lambda a, b: 1
        obstacles.get_obstacles()

        output = world.is_position_allowed(3,10, world.position_x, world.position_y)
        self.assertEqual(output, False)


    def test_update_position_when_it_misses_from_bottom(self):
        world.position_x = 0
        world.position_y = 0
        obstacles.random.randint = lambda a, b: 1
        obstacles.get_obstacles()

        output = world.update_position(50)
        self.assertEqual(output, True)


    def test_update_position_when_it_hits_from_bottom(self):
        obstacles.random.randint = lambda a, b: 1
        world.position_x = 3
        world.position_y = 0
        obstacles.get_obstacles()

        output = world.update_position(50)
        self.assertEqual(output, False)


    def test_update_position_when_it_hits_from_right(self):
        obstacles.random.randint = lambda a, b: 1
        world.position_x = 0
        world.position_y = 3
        world.current_direction_index = 1
        obstacles.get_obstacles()

        output = world.update_position(50)
        self.assertEqual(output, False)


    def test_update_position_when_it_hits_from_left(self):
        obstacles.random.randint = lambda a, b: 1
        world.position_x = 20
        world.position_y = 3
        world.current_direction_index = 3
        obstacles.get_obstacles()

        output = world.update_position(50)
        self.assertEqual(output, False)


    def test_update_position_when_it_hits_from_top(self):
        obstacles.random.randint = lambda a, b: 1
        world.position_x = 3
        world.position_y = 20
        world.current_direction_index = 2
        obstacles.get_obstacles()

        output = world.update_position(50)
        self.assertEqual(output, False)
        

if __name__ == '__main__':
    unittest.main()
        