import unittest
from unittest.mock import patch
from io import StringIO
from test_base import captured_io
import robot

class MyTestCase(unittest.TestCase):
    def test_get_command_input_off(self):
        '''
        1.  gets captured IO simulates user input. hlp and 1265 arent
            valid inputs so the give error messages.
        2.  save outputs (what the function prints) after those inputs
            variable output.
        3.  tests if expected output is equal to what the function printed
        '''
        with captured_io(StringIO("hlp\n1265\nOFf")) as (out, err):
            robot.get_command_input("HAL")
        
        output = out.getvalue().strip()

        off_output = """HAL: What must I do next? HAL: Sorry, I did not understand 'hlp'.
HAL: What must I do next? HAL: Sorry, I did not understand '1265'.
HAL: What must I do next? HAL: Shutting down.."""
        
        self.assertEqual(off_output, output)


    def test_get_command_input_help(self):
        '''
        gets captured IO simulates user input. Tests if help output is what its supposed
        to be
        '''
        with captured_io(StringIO("HElp\noff")) as (out, err):
            robot.get_command_input("HAL")

        output = out.getvalue().strip()

        help_output = """HAL: What must I do next? I can understand these commands:
OFF  - Shut down robot
HELP - provide information about commands
FORWARD - move robot forward. Input how far you want robot to move
BACK    - move robot backwards. Input how far you want robot to move
LEFT    - turn robot left 90 degrees.
RIGHT   - turn robot right 90 degrees.
SPRINT  - move robot forward in a recursive manner. Input how far you want robot to move
HAL: What must I do next? HAL: Shutting down.."""

        self.assertEqual(help_output, output)


    def test_handle_movements(self):
        """
        tests that robot moves correctly, the function it tests only takes in
        forward and back as direction, and which direction the robot its facing
        after testing it facing different directions its supposed to return
        correct coordinates
        """
        self.assertEqual(robot.handle_movements(['forward', 10],[0,10],'facing_forward'), [0,20])
        self.assertEqual(robot.handle_movements(['forward', 10],[0,10],'facing_back'), [0,0])
        self.assertEqual(robot.handle_movements(['forward', 10],[0,10],'facing_right'), [10,10])
        self.assertEqual(robot.handle_movements(['forward', 10],[0,10],'facing_left'), [-10,10])

        self.assertEqual(robot.handle_movements(['back', 10],[0,10],'facing_forward'), [0,0])
        self.assertEqual(robot.handle_movements(['back', 10],[0,10],'facing_back'), [0,20])
        self.assertEqual(robot.handle_movements(['back', 10],[0,10],'facing_right'), [-10,10])
        self.assertEqual(robot.handle_movements(['back', 10],[0,10],'facing_left'), [10,10])


    def test_sprint_recursion(self):
        '''
        test that function returns the correct number after the recursive operation
        has been performed on it. Input will always be an int.
        '''
        self.assertEqual(robot.sprint_recursion(5), 15)
        self.assertEqual(robot.sprint_recursion(13), 91)
        self.assertEqual(robot.sprint_recursion(7), 28)

    def test_limit_area(self):
        '''
        tests that function returns True or False depending on whether position
        is outside the max area.
        '''
        self.assertEqual(robot.limit_area([0,101]), True)
        self.assertEqual(robot.limit_area([100,101]), True)
        self.assertEqual(robot.limit_area([101,0]), False)
        self.assertEqual(robot.limit_area([100,210]), False)


    def test_direction_robot_is_facing(self):
        '''
        tests that function returns the correct direction the robot is facing
        based on the current direction of the robot and where the robot should turn
        '''
        self.assertEqual(robot.direction_robot_is_facing("facing_forward", "left"), "facing_left")
        self.assertEqual(robot.direction_robot_is_facing("facing_forward", "right"), "facing_right")
        self.assertEqual(robot.direction_robot_is_facing("facing_forward", "forward"), "facing_forward")
        self.assertEqual(robot.direction_robot_is_facing("facing_forward", "back"), "facing_forward")

        self.assertEqual(robot.direction_robot_is_facing("facing_left", "left"), "facing_back")
        self.assertEqual(robot.direction_robot_is_facing("facing_left", "right"), "facing_forward")
        self.assertEqual(robot.direction_robot_is_facing("facing_left", "back"), "facing_left")
        self.assertEqual(robot.direction_robot_is_facing("facing_left", "forward"), "facing_left")
        
        self.assertEqual(robot.direction_robot_is_facing("facing_right", "left"), "facing_forward")
        self.assertEqual(robot.direction_robot_is_facing("facing_right", "right"), "facing_back")
        self.assertEqual(robot.direction_robot_is_facing("facing_right", "back"), "facing_right")
        self.assertEqual(robot.direction_robot_is_facing("facing_right", "forward"), "facing_right")

        self.assertEqual(robot.direction_robot_is_facing("facing_back", "left"), "facing_right")
        self.assertEqual(robot.direction_robot_is_facing("facing_back", "right"), "facing_left")
        self.assertEqual(robot.direction_robot_is_facing("facing_back", "back"), "facing_back")
        self.assertEqual(robot.direction_robot_is_facing("facing_back", "forward"), "facing_back")


    def test_get_command_input_sprint(self):
        with captured_io(StringIO("sprint 5\noff")) as (out, err):
            robot.get_command_input("HAL")

        output = out.getvalue().strip()
        
        sprint_output = '''HAL: What must I do next?  > HAL moved forward by 5 steps.
 > HAL moved forward by 4 steps.
 > HAL moved forward by 3 steps.
 > HAL moved forward by 2 steps.
 > HAL moved forward by 1 steps.
 > HAL now at position (0,15).
HAL: What must I do next? HAL: Shutting down..'''

        self.assertEqual(output, sprint_output)


if __name__ == '__main__':
    unittest.main()
