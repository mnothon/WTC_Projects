import sys, os, inspect

currentdir = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
parentdir = os.path.dirname(currentdir)
sys.path.insert(0, parentdir)

from world import obstacles

is_coordinate_blocked = False


# variables tracking position and direction
position_x = 0
position_y = 0
current_direction_index = 0
directions = ['forward', 'right', 'back', 'left']


# area limit vars
min_y, max_y = -200, 200
min_x, max_x = -100, 100


def is_position_allowed(new_x, new_y, position_x, position_y):
        """
        Checks if the new position will still fall within the max area limit
        :param new_x: the new/proposed x position
        :param new_y: the new/proposed y position
        :return: True if allowed, i.e. it falls in the allowed area, else False
        """
        global is_coordinate_blocked

        if obstacles.is_path_blocked(new_x, new_y, position_x, position_y) or obstacles.is_position_blocked(new_x, new_y):
            is_coordinate_blocked = True
            return False
        return min_x <= new_x <= max_x and min_y <= new_y <= max_y


def show_position(robot_name):
        print(' > '+robot_name+' now at position ('+str(position_x)+','+str(position_y)+').')


def do_right_turn():
    pass


def do_left_turn():
    pass


def update_position(steps):
    """
    Update the current x and y positions given the current direction, and specific nr of steps
    :param steps:
    :return: True if the position was updated, else False
    """

    global position_x, position_y, is_coordinate_blocked
    new_x = position_x
    new_y = position_y
    is_coordinate_blocked = False
    
    if directions[current_direction_index] == 'forward':
        new_y = new_y + steps
    elif directions[current_direction_index] == 'right':
        new_x = new_x + steps
    elif directions[current_direction_index] == 'back':
        new_y = new_y - steps
    elif directions[current_direction_index] == 'left':
        new_x = new_x - steps

    if is_position_allowed(new_x, new_y, position_x, position_y):
        position_x = new_x
        position_y = new_y
        return True
    return False