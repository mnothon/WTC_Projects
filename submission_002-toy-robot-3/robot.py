import collections

# list of valid command names
valid_commands = ['off', 'help', 'forward', 'back', 'right', 'left', 'sprint', 'replay','history']
extra_commands = [['replay','silent'], ['replay'],['replay', 'reversed'], ['replay','reversed','silent']]
replay_command = []
# variables tracking position and direction
position_x = 0
position_y = 0
directions = ['forward', 'right', 'back', 'left']
current_direction_index = 0
commands_list = []

# area limit vars
min_y, max_y = -200, 200
min_x, max_x = -100, 100

#TODO: WE NEED TO DECIDE IF WE WANT TO PRE_POPULATE A SOLUTION HERE, OR GET STUDENT TO BUILD ON THEIR PREVIOUS SOLUTION.

def get_robot_name():
    name = input("What do you want to name your robot? ")
    while len(name) == 0:
        name = input("What do you want to name your robot? ")
    return name


def get_command(robot_name):
    """
    Asks the user for a command, and validate it as well
    Only return a valid command
    """

    prompt = ''+robot_name+': What must I do next? '
    command = input(prompt)
    while len(command) == 0 or not valid_command(command):
        output(robot_name, "Sorry, I did not understand '"+command+"'.")
        command = input(prompt)
    return command.lower()


def sort_replays(args):
    """
    Takes in list of all the commands and sorts them in a manner where the 
    order doesnt matter.
    Returns: Sorted list
    """
    global replay_command
    
    my_bool = False

    args = [arg.lower() for arg in args]
    compare = lambda x, y: collections.Counter(x) == collections.Counter(y)

    for arg in args:
        if arg.isdigit() or has_num(arg):
            temp_num = arg
            my_bool = True
    temp_list = [arg for arg in args if arg.isdigit() == False and has_num(arg) == False]

    for element in extra_commands:
        temp = element[:]
        if compare(temp_list, temp):
            if my_bool:
                temp.append(temp_num)
                replay_command = (' '.join(temp))
                return temp
            else:
                replay_command = (' '.join(temp))
                return temp
    return ['']

def split_command_input(command):
    """
    Splits the string at the first space character, to get the actual command, as well as the argument(s) for the command
    :return: (command, argument)
    """
    args = command.split(' ', 3)
    
    if args[0].lower() == 'replay':
        args = sort_replays(args)

    if len(args) == 2:
        return args[0], args[1],'',''
    elif len(args) == 3:
        return args[0], args[1], args[2],''
    elif len(args) == 4:
        return args[0], args[1], args[2], args[3]
    return args[0], '','', ''


def has_num(num):
    num_list = num.split('-')
    return len(num_list) == 2 and num_list[0].isdigit() and num_list[1].isdigit()


def is_int(value):
    """
    Tests if the string value is an int or not
    :param value: a string value to test
    :return: True if it is an int
    """
    try:
        int(value)
        return True
    except ValueError:
        return False


def valid_command(command):
    """
    Returns a boolean indicating if the robot can understand the command or not
    Also checks if there is an argument to the command, and if it a valid int
    """

    (arg, arg1, arg2, arg3) = split_command_input(command)   
    if arg=='replay':
        return True
    elif arg.lower() in valid_commands and (len(arg1) == 0 or arg1.isdigit()) and arg2.lower() == '' and arg3.lower() == '':
        return True
    else:
        return False


def output(name, message):
    print(''+name+": "+message)


def do_help():
    """
    Provides help information to the user
    :return: (True, help text) to indicate robot can continue after this command was handled
    """
    return True, """I can understand these commands:
OFF  - Shut down robot
HELP - provide information about commands
FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'
BACK - move backward by specified number of steps, e.g. 'BACK 10'
RIGHT - turn right by 90 degrees
LEFT - turn left by 90 degrees
SPRINT - sprint forward according to a formula
REPLAY - replays all the previous commands for robot
REPLAY SILENT - Replays all the previous commands for robot without printing what 
                robot does except the final position of the robot
REPLAY REVERSED - replays all the previous commands for robot in reverse
REPLAY REVERSED SILENT - Replays all the previous commands for robot in reverse without
                         printing what robot does except the final position of the robot
"""

def do_history(robot_name):
    print(f'{robot_name} has the following commands in history.')
    for element in commands_list:
        if element == commands_list[-1]:
            continue
        print(f' > {element}')
    return True, ''


def show_position(robot_name):
    print(' > '+robot_name+' now at position ('+str(position_x)+','+str(position_y)+').')


def is_position_allowed(new_x, new_y):
    """
    Checks if the new position will still fall within the max area limit
    :param new_x: the new/proposed x position
    :param new_y: the new/proposed y position
    :return: True if allowed, i.e. it falls in the allowed area, else False
    """

    return min_x <= new_x <= max_x and min_y <= new_y <= max_y


def update_position(steps):
    """
    Update the current x and y positions given the current direction, and specific nr of steps
    :param steps:
    :return: True if the position was updated, else False
    """

    global position_x, position_y
    new_x = position_x
    new_y = position_y

    if directions[current_direction_index] == 'forward':
        new_y = new_y + steps
    elif directions[current_direction_index] == 'right':
        new_x = new_x + steps
    elif directions[current_direction_index] == 'back':
        new_y = new_y - steps
    elif directions[current_direction_index] == 'left':
        new_x = new_x - steps

    if is_position_allowed(new_x, new_y):
        position_x = new_x
        position_y = new_y
        return True
    return False


def do_forward(robot_name, steps):
    """
    Moves the robot forward the number of steps
    :param robot_name:
    :param steps:
    :return: (True, forward output text)
    """
    if update_position(steps):
        return True, ' > '+robot_name+' moved forward by '+str(steps)+' steps.'
    else:
        return True, ''+robot_name+': Sorry, I cannot go outside my safe zone.'


def do_back(robot_name, steps):
    """
    Moves the robot forward the number of steps
    :param robot_name:
    :param steps:
    :return: (True, forward output text)
    """

    if update_position(-steps):
        return True, ' > '+robot_name+' moved back by '+str(steps)+' steps.'
    else:
        return True, ''+robot_name+': Sorry, I cannot go outside my safe zone.'


def do_right_turn(robot_name):
    """
    Do a 90 degree turn to the right
    :param robot_name:
    :return: (True, right turn output text)
    """
    global current_direction_index

    current_direction_index += 1
    if current_direction_index > 3:
        current_direction_index = 0

    return True, ' > '+robot_name+' turned right.'


def do_left_turn(robot_name):
    """
    Do a 90 degree turn to the left
    :param robot_name:
    :return: (True, left turn output text)
    """
    global current_direction_index

    current_direction_index -= 1
    if current_direction_index < 0:
        current_direction_index = 3

    return True, ' > '+robot_name+' turned left.'


def do_sprint(robot_name, steps):
    """
    Sprints the robot, i.e. let it go forward steps + (steps-1) + (steps-2) + .. + 1 number of steps, in iterations
    :param robot_name:
    :param steps:
    :return: (True, forward output)
    """

    if steps == 1:
        return do_forward(robot_name, 1)
    else:
        (do_next, command_output) = do_forward(robot_name, steps)
        print(command_output)
        return do_sprint(robot_name, steps - 1)


def print_replays(robot_name, temp_num=[]):
    """
    Prints replay stuff
    """
    if len(temp_num) == 1:
        print(f' > {robot_name} replayed {temp_num[0]} commands.')
    elif len(temp_num) == 2:
        print(f' > {robot_name} replayed {temp_num[0]-temp_num[1]} commands.')
    elif len(temp_num) == 0:
        print(f' > {robot_name} replayed {len(commands_list)-1} commands.')
    show_position(robot_name)


def print_replay_reversed(robot_name, temp_num=[]):
    """
    Prints replay reversed stuff
    """
    if len(temp_num) == 1:
        print(f' > {robot_name} replayed {temp_num[0]} commands in reverse.')
    elif len(temp_num) == 2:
        print(f' > {robot_name} replayed {temp_num[0]-temp_num[1]} commands in reverse.')
    elif len(temp_num) == 0:
        print(f' > {robot_name} replayed {len(commands_list)-1} commands in reverse.')
    show_position(robot_name)


def print_replay_silent(robot_name, temp_num=[]):
    """
    Prints replay silent stuff
    """
    if len(temp_num) == 1:
        print(f' > {robot_name} replayed {len(commands_list)-2} commands silently.')
    elif len(temp_num) == 2:
        print(f' > {robot_name} replayed {len(commands_list)-2} commands silently.')
    elif len(temp_num) == 0:
        print(f' > {robot_name} replayed {len(commands_list)-1} commands silently.')
    show_position(robot_name)


def print_replay_reversed_silent(robot_name, temp_num=[]):
    """
    Prints replay reversed silent stuff
    """
    if len(temp_num) == 1:
        print(f' > {robot_name} replayed {temp_num[0]} commands in reverse silently.')
    elif len(temp_num) == 2:
        print(f' > {robot_name} replayed {temp_num[0]-temp_num[1]} commands in reverse silently.')
    elif len(temp_num) == 0:
        print(f' > {robot_name} replayed {len(commands_list)-1} commands in reverse silently.')
    show_position(robot_name)


def do_replay(robot_name, temp_num):
    """
    Handles replay command by calling each of the commands in the commands list
    :Param 1 (str)  - Name of the robot
    :Param 2 (list) - How many steps robot should take
    """
    if len(temp_num) == 1:
        for i in range(len(commands_list)-temp_num[0]-1, len(commands_list)):
            if commands_list[i] == commands_list[-1]:
                print_replays(robot_name, temp_num)
                return
            handle_command(robot_name, commands_list[i])
    elif len(temp_num) == 2:
        for i in range(len(commands_list)-temp_num[0]-1, len(commands_list)):
            if commands_list[i] == commands_list[len(commands_list)-temp_num[1]-1]:
                print_replays(robot_name, temp_num)
                return
            handle_command(robot_name, commands_list[i])
    else:
        for command in commands_list:
            if command == commands_list[-1]:
                print_replays(robot_name)
                return
            handle_command(robot_name, command)
    return

def do_replay_silent(robot_name, temp_num):
    """
    Handles replay silent command by calling each of the commands in the commands list
    :Param 1 (str)  - Name of the robot
    :Param 2 (list) - How many steps robot should take
    """
    if len(temp_num) == 1:
        for i in range(len(commands_list)-temp_num[0]-1, len(commands_list)):
            handle_command(robot_name, commands_list[i])
            if commands_list[i] == commands_list[-2]:
                print_replay_silent(robot_name, temp_num)
    elif len(temp_num) == 2:
        for i in range((len(commands_list)-temp_num[0]-1), (len(commands_list)-temp_num[1]-1)):
            handle_command(robot_name, commands_list[i])
            if commands_list[i] == commands_list[-2]:
                print_replay_silent(robot_name, temp_num)
    else:    
        for command in commands_list:
            handle_command(robot_name, command)
            if command == commands_list[-1]:
                print_replay_silent(robot_name)


def do_replay_reversed(robot_name, temp_num):
    """
    Handles all the movements done when the command is replay reversed.
    :Param 1 (str)  - Name of the robot
    :Param 2 (list) - How many steps robot should take
    """
    d = 0
    if len(temp_num) == 1:
        for i in reversed(range(0, temp_num[0])):
            d+=1
            handle_command(robot_name, commands_list[i])
            if d == int(temp_num):
                print_replay_reversed(robot_name, temp_num)
    elif len(temp_num) == 2:
        for i in reversed(range(temp_num[1], temp_num[0])):
            d+=1
            handle_command(robot_name, commands_list[i])
            if d == temp_num[0]-temp_num[1]:
                print_replay_reversed(robot_name, temp_num)
    else:    
        for i in reversed(range(len(commands_list)-1)):
            d+=1
            handle_command(robot_name, commands_list[i])
            if d == len(commands_list)-1:
                print_replay_reversed(robot_name)


def do_replay_reversed_silent(robot_name, temp_num):
    """
    Handles replay reversed silent command by calling each of the commands in the commands list
    :Param 1 (str)  - Name of the robot
    :Param 2 (list) - How many steps robot should take
    """    
    global commands_list
    d = 0
    if len(temp_num) == 1:
        for i in reversed(range(0, temp_num[0])):
            d+=1
            handle_command(robot_name, commands_list[i])
            if d == int(temp_num):
                print_replay_reversed_silent(robot_name, temp_num)
    elif len(temp_num) == 2:
        for i in reversed(range(temp_num[1], temp_num[0])):
            d+=1
            handle_command(robot_name, commands_list[i])
            if d == temp_num[0]-temp_num[1]:
                print_replay_reversed_silent(robot_name, temp_num)
    else:    
        for i in reversed(range(len(commands_list)-1)):
            d+=1
            handle_command(robot_name, commands_list[i])
            if d == len(commands_list)-1:
                print_replay_reversed_silent(robot_name)


def handle_replay_commands(robot_name, command):
    """
    Takes in all commands beginning with replay and matches them with their respective
    replay functions.
    :Param 1 (str) - Robot name
    :Param 2 (list) - list of all commands beginning with 'replay'
    """    
    global commands_list
    global replay_command

    command = replay_command
    
    my_list = command.split(' ')
    my_bool = False
    
    temp_num = []
    for arg in my_list:
        if arg.isdigit():
            temp_num = [int(arg)]
        elif has_num(arg):
            hold = arg.split('-')
            temp_num = [int(hold[0]), int(hold[1])]

    temp_list = [arg for arg in my_list if arg.isdigit() == False and has_num(arg) == False]
    
    if temp_list == ['replay']:
        do_replay(robot_name, temp_num)
        replay_command = ''
        commands_list.pop(-1)
    if temp_list == ['replay','silent']:
        do_replay_silent(robot_name, temp_num)
        replay_command = ''
        commands_list.pop(-1)
    if temp_list == ['replay','reversed']:
        do_replay_reversed(robot_name, temp_num)
        replay_command = ''
        commands_list.pop(-1)
    if temp_list == ['replay','reversed','silent']:
        do_replay_reversed_silent(robot_name, temp_num)
        replay_command = ''
        commands_list.pop(-1)
    return
    

def handle_command(robot_name, command):
    """
    Handles a command by asking different functions to handle each command.
    :param robot_name: the name given to robot
    :param command: the command entered by user
    :return: `True` if the robot must continue after the command, or else `False` if robot must shutdown
    """
    global commands_list
    (command_name, arg, arg1, arg2) = split_command_input(command)

    if command_name == 'off':
        return False
    elif command_name == 'help':
        (do_next, command_output) = do_help()
        commands_list.pop(-1)
    elif command_name == 'history':
        (do_next, command_output) = do_history(robot_name)
        commands_list.pop(-1)
    elif command_name == 'forward':
        (do_next, command_output) = do_forward(robot_name, int(arg))
    elif command_name == 'back':
        (do_next, command_output) = do_back(robot_name, int(arg))
    elif command_name == 'right':
        (do_next, command_output) = do_right_turn(robot_name)
    elif command_name == 'left':
        (do_next, command_output) = do_left_turn(robot_name)
    elif command_name == 'sprint':
        (do_next, command_output) = do_sprint(robot_name, int(arg))
    elif command_name == 'replay':
        do_next = True

    if len(replay_command) != 1 and 'silent' not in replay_command:
        if command_output != '':
            print(command_output)
        show_position(robot_name)
    
    return do_next


def history(command):
    """
    Keeps track of all the robot commands as a list
    Parameters:
        arg1 (str): Takes in a string as a parameter
    """
    global commands_list

    commands_list.append(command)


def robot_start():
    """
    This is the entry point for starting my robot
    """

    global position_x, position_y, current_direction_index, commands_list, replay_command

    replay_command = []
    position_x = 0
    position_y = 0
    current_direction_index = 0
    commands_list = []
    
    robot_name = get_robot_name()
    output(robot_name, "Hello kiddo!")

    command = get_command(robot_name)
    commands_list.append(command)
    while handle_command(robot_name, command):
        command = get_command(robot_name)
        if len(replay_command) == 1:
            command = replay_command
        history(command)
        while 'replay' in command:
            handle_replay_commands(robot_name, command)
            command = get_command(robot_name)
            history(command)
    output(robot_name, "Shutting down..")


if __name__ == "__main__":
    robot_start()
