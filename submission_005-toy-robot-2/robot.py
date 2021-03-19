import sys


def get_robot_name():
    """
    gets user input to assign name to the robot.
    Returns the robots name
    """
    robot_name = input("What do you want to name your robot? ")
    print(f'{robot_name}: Hello kiddo!')
    return robot_name


def turn_robot_off(name):
    """
    Turns robot off
    """
    print(f"{name}: Shutting down..")
    return


def show_help_options():
    """
    shows help options 
    """
    return("""I can understand these commands:
OFF  - Shut down robot
HELP - provide information about commands
FORWARD - move robot forward. Input how far you want robot to move
BACK    - move robot backwards. Input how far you want robot to move
LEFT    - turn robot left 90 degrees.
RIGHT   - turn robot right 90 degrees.
SPRINT  - move robot forward in a recursive manner. Input how far you want robot to move""")


def separate_commands(user_input):
    '''
    1.  checks if word has any white space in it
    2.  separates input into 2 and returns it as a list
    3.  returns empty list if there isnt a space
    '''
    if (" ") in user_input:
        return user_input.split(" ")
    else:
        return []


def get_my_direction(current_direction,m_input,user_input):
    '''
    function to handle commands to call function to calculate which direction 
    robot is facing
    '''
    y_movements = ['forward','back','sprint']
    x_movements = ['left', 'right']
    
    if len(m_input) == 2:
        if m_input[0] in y_movements and m_input[1].isdigit() == True:
            current_direction = direction_robot_is_facing(current_direction, m_input[0])
    elif user_input in x_movements:
        current_direction = direction_robot_is_facing(current_direction, user_input)
    return current_direction


def is_movement_input_valid(m_input, user_input):
    '''
    checks whether inputs are valid inputs that robot can use to move
    returns a tuple with True(valid input) and whether its input is a list or not
    '''
    y_movements = ['forward','back','sprint']
    x_movements = ['left', 'right']
    if len(m_input) > 0:
        if len(m_input) == 2 and m_input[0] in y_movements and m_input[1].isdigit() == True:
           return True, "is_list"
        else:
            return False, "is_list"
    elif user_input in x_movements:
        return True, "not_list"
    else:
        return False, "not_list"


def direction_robot_is_facing(current_direction, new_direction):
    '''
    determines which side the robot is facing
    takes in old direction and where robot is turning then determines
    where its facing. Returns new direction
    '''
    if current_direction == "facing_forward":
        if new_direction == "left":
            return "facing_left"
        elif new_direction == "right":
            return "facing_right"
        else:
            return "facing_forward"
    elif current_direction == "facing_left":
        if new_direction == "left":
            return "facing_back"
        elif new_direction == "right":
            return "facing_forward"
        else:
            return "facing_left"
    elif current_direction == "facing_right":
        if new_direction == "left":
            return "facing_forward"
        elif new_direction == "right":
            return "facing_back"
        else:
            return "facing_right"
    elif current_direction == "facing_back":
        if new_direction == "left":
            return "facing_right"
        elif new_direction == "right":
            return "facing_left"
        else:
            return "facing_back"
    return


def print_sprints(m_input,name,position):
    '''
    prints statements related to sprint movement
    '''
    i = int(m_input[1])
    while (i) > 0:
        print(f' > {name} moved forward by {i} steps.')
        i -= 1
    print(f' > {name} now at position ({position[0]},{position[1]}).')
    return


def print_linear_movements(m_input,name,position):
    '''
    prints statements related to forward and back movements
    '''
    if m_input[0] == "sprint":
        print_sprints(m_input,name,position)
    else:
        print(f' > {name} moved {m_input[0]} by {m_input[1]} steps.')
        print(f' > {name} now at position ({position[0]},{position[1]}).')
    return


def print_turn_movements(name,user_input,position):
    '''
    prints statements related to right and left turns
    '''
    print(f' > {name} turned {user_input}.')
    print(f' > {name} now at position ({position[0]},{position[1]}).')
    return


def limit_area(temp_position):
    '''
    Takes in position and determines if any of the values in it are larger
    than the max allowed are.
    Returns True or False
    '''
    if temp_position[0] < -100 or temp_position[0] > 100:
        return False
    elif temp_position[1] < -200 or temp_position[1] > 200:
        return False
    else:
        return True


def sprint_recursion(num):
    '''
    1.  takes in int that was passed as param for the sprint command
    2.  recursives finds the distance robot should move and returns int.
    '''
    if num == 0:
        return num
    else:
        my_sum = sprint_recursion(num-1)
        num += my_sum
        return num
        

def handle_movements(movements,position,current_direction):
    '''
    Updates robot position
    1.  Takes in direction robot is facing and the direction robot is turning
    2.  calculates new position of robot based on how far it moves
    3.  returns new position
    '''
    temp_position = []
    temp_position.append(position[0])
    temp_position.append(position[1])
    if current_direction == "facing_forward":
        if movements[0] == "forward":
            temp_position[1] = position[1] + int(movements[1])
        elif movements[0] == "sprint":
            temp_position[1] = position[1] + sprint_recursion(int(movements[1]))
        else:
            temp_position[1] = position[1] - int(movements[1])
    if current_direction == "facing_back":
        if movements[0] == "forward":
            temp_position[1] = position[1] - int(movements[1])
        elif movements[0] == "sprint":
            temp_position[1] = position[1] + sprint_recursion(int(movements[1]))
        else:
            temp_position[1] = position[1] + int(movements[1])
    if current_direction == "facing_left":
        if movements[0] == "forward":
            temp_position[0] = position[0] - int(movements[1])
        elif movements[0] == "sprint":
            temp_position[0] = position[0] + sprint_recursion(int(movements[1]))
        else:
            temp_position[0] = position[0] + int(movements[1])
    if current_direction == "facing_right":
        if movements[0] == "forward":
            temp_position[0] = position[0] + int(movements[1])
        elif movements[0] == "sprint":
            temp_position[0] = position[0] + sprint_recursion(int(movements[1]))
        else:
            temp_position[0] = position[0] - int(movements[1])
    return temp_position
        

def get_command_input(name):
    """
    gets different inputs and calls other functions that correspond to
    The input
    """
    user_input = input(f'{name}: What must I do next? ')
    position = [0,0]
    current_direction = "facing_forward"

    is_input_valid = False
    while is_input_valid == False:
        """
        While loop keeps on executing until user inputs valid input
        or user turns robot off
        """
        m_input = separate_commands(user_input.lower())
        current_direction = get_my_direction(current_direction,m_input,user_input)
        is_valid, is_it_a_list = is_movement_input_valid(m_input, user_input.lower())

        if user_input.lower() == "off":
            turn_robot_off(name)
            break
        elif user_input.lower() == "help":
            user_input = user_input.lower()
            message = show_help_options()
            print(message)
            user_input = input(f'{name}: What must I do next? ')
        elif is_valid == True:
            if is_it_a_list == "is_list":
                position_list = handle_movements(m_input,position,current_direction)
                if limit_area(position_list) == True:
                    position[0] = position_list[0]
                    position[1] = position_list[1]
                    print_linear_movements(m_input,name,position)
                else:
                    print(f'{name}: Sorry, I cannot go outside my safe zone.')
                    print(f' > {name} now at position ({position[0]},{position[1]}).')
            else:
                print_turn_movements(name,user_input.lower(),position)
            
            user_input = input(f'{name}: What must I do next? ')
        else:
            print(f"{name}: Sorry, I did not understand '{user_input}'.")
            user_input = input(f'{name}: What must I do next? ')
    return


def robot_start():
    robot_name = get_robot_name()
    get_command_input(robot_name)
    

if __name__ == "__main__":
    robot_start()
