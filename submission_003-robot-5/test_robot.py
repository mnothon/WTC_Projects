import unittest
import robot
from io import StringIO
from test_base import captured_io

class MyTestCase(unittest.TestCase):
    def test_split_command_input(self):
        """
        test whether function splits correctly
        """
        arg, arg1 = robot.split_command_input('replay 5-8')
        self.assertEqual(arg, 'replay')
        self.assertEqual(arg1, '5-8')   

    
    def test_valid_command(self):
        """
        Tests whether different commands evaluate as either True or False
        """
        output = robot.valid_command('replay 5')
        self.assertEqual(output, True)

        output1 = robot.valid_command('replay silent')
        self.assertEqual(output1, True)

        output2 = robot.valid_command('replay 5-8 silent')
        self.assertEqual(output2, True)

        output3 = robot.valid_command('forward 10')
        self.assertEqual(output3, True)

        output4 = robot.valid_command('forwar 10')
        self.assertEqual(output4, False)

        output5 = robot.valid_command('REPLAY silent 2 reversed')
        self.assertEqual(output5, True)

        output6 = robot.valid_command('silent reversed')
        self.assertEqual(output6, False)

        output7 = robot.valid_command('replay 2a')
        self.assertEqual(output7, False)

        output8 = robot.valid_command('replay reversed 8--3')
        self.assertEqual(output8, False)

        output9 = robot.valid_command('')
        self.assertEqual(output9, False)


    def test_output_whether_it_prints_out_obstacles(self):
        with captured_io(StringIO('HAL\noff\n')) as (out, err):
            robot.robot_start()

        output = out.getvalue().strip()
        self.assertEqual("""What do you want to name your robot? HAL: Hello kiddo!
HAL: Loaded obstacles.
There are some obstacles:""", output[:103])
        

if __name__ == "__main__":
    unittest.main()