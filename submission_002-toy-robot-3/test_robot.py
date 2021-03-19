import unittest
import robot
from io import StringIO
from test_base import captured_io

class MyTestCase(unittest.TestCase):
    def test_split_command_input(self):
        """
        test whether function splits correctly
        """
        arg, arg1, arg2, arg3 = robot.split_command_input('replay 5-8')
        self.assertEqual(arg, 'replay')
        self.assertEqual(arg1, '5-8')
        self.assertEqual(arg2, '')
        self.assertEqual(arg3, '')   

    
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

        output9 = robot.valid_command('replay silent silent')
        self.assertEqual(output9, False)

        output10 = robot.valid_command('')
        self.assertEqual(output10, False)
        

    def test_sort_replays(self):
        """
        tests whether function sorts out replay commands as it should
        """
        output = robot.sort_replays(['replay', 'reversed', '5', 'silent'])
        my_output = ['replay', 'reversed', 'silent', '5']
        self.assertEqual(output, my_output)

        output1 = robot.sort_replays(['replay', '5-8', 'reversed'])
        my_output1 = ['replay', 'reversed', '5-8']
        self.assertEqual(output1, my_output1)

        output2 = robot.sort_replays(['replay', '5-8', 'silent'])
        my_output2 = ['replay', 'silent', '5-8']
        self.assertEqual(output2, my_output2)

        output4 = robot.sort_replays(['replay','silent', 'reversed'])
        my_output4 = ['replay', 'reversed','silent']
        self.assertEqual(output4, my_output4)

        output3 = robot.sort_replays(['replay','reversed'])
        my_output3 = ['replay', 'reversed']
        self.assertEqual(output3, my_output3)

    
    def test_replay_reversed_outputs(self):
        with captured_io(StringIO('HAL\nforward 10\nforward 5\nreplay reversed\noff\n')) as (out, err):
            robot.robot_start()

        output = out.getvalue().strip()
        self.assertEqual("""What do you want to name your robot? HAL: Hello kiddo!
HAL: What must I do next?  > HAL moved forward by 10 steps.
 > HAL now at position (0,10).
HAL: What must I do next?  > HAL moved forward by 5 steps.
 > HAL now at position (0,15).
HAL: What must I do next?  > HAL moved forward by 5 steps.
 > HAL now at position (0,20).
 > HAL moved forward by 10 steps.
 > HAL now at position (0,30).
 > HAL replayed 2 commands in reverse.
 > HAL now at position (0,30).
HAL: What must I do next? HAL: Shutting down..""", output)


    def test_step2_replay_basic(self):
        with captured_io(StringIO('HAL\nforward 10\nforward 5\nreplay\noff\n')) as (out, err):
            robot.robot_start()

        output = out.getvalue().strip()
        self.assertEqual("""What do you want to name your robot? HAL: Hello kiddo!
HAL: What must I do next?  > HAL moved forward by 10 steps.
 > HAL now at position (0,10).
HAL: What must I do next?  > HAL moved forward by 5 steps.
 > HAL now at position (0,15).
HAL: What must I do next?  > HAL moved forward by 10 steps.
 > HAL now at position (0,25).
 > HAL moved forward by 5 steps.
 > HAL now at position (0,30).
 > HAL replayed 2 commands.
 > HAL now at position (0,30).
HAL: What must I do next? HAL: Shutting down..""", output)

    def test_replay_silent_reversed_upper_command(self):
        with captured_io(StringIO('HAL\nforward 10\nforward 5\nreplay REVERSED SILENT\noff\n')) as (out, err):
            robot.robot_start()

        output = out.getvalue().strip()
        self.assertEqual("""What do you want to name your robot? HAL: Hello kiddo!
HAL: What must I do next?  > HAL moved forward by 10 steps.
 > HAL now at position (0,10).
HAL: What must I do next?  > HAL moved forward by 5 steps.
 > HAL now at position (0,15).
HAL: What must I do next?  > HAL replayed 2 commands in reverse silently.
 > HAL now at position (0,30).
HAL: What must I do next? HAL: Shutting down..""", output)
        

if __name__ == "__main__":
    unittest.main()