# -------------------------------------------------------------------------------------------------
#
# TODO: Please replace this placeholder code with your solution for Toy Robot 4, and work from there.
#
# -------------------------------------------------------------------------------------------------

import sys, os, inspect

currentdir = os.path.dirname(os.path.abspath(inspect.getfile(inspect.currentframe())))
parentdir = os.path.dirname(currentdir)
sys.path.insert(0, parentdir)

import robot as main_module
from queue import PriorityQueue
from maze import obstacles as crazy_maze

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

        if crazy_maze.is_path_blocked(new_x, new_y, position_x, position_y) or crazy_maze.is_position_blocked(new_x, new_y):
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

    global position_x, position_y
    new_x = position_x
    new_y = position_y
    # is_coordinate_blocked = True

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



def determine_next_node_direction(node, next_node):
    """
    Checks the position of the next node in relation to the current node, will return either 0 for front,
    1 for right, 2 for behind, 3 for left
    """
    if current_direction_index == 0:
        if node.x == next_node.x and node.y+1 == next_node.y:
            return 0
        elif node.x+1  == next_node.x and node.y == next_node.y:
            return 1
        elif node.x  == next_node.x and node.y-1 == next_node.y:
            return 2
        elif node.x-1  == next_node.x and node.y == next_node.y:
            return 3
    
    if current_direction_index == 1:
        if node.x+1 == next_node.x and node.y == next_node.y:
            return 0
        elif node.x  == next_node.x and node.y-1 == next_node.y:
            return 1
        elif node.x-1  == next_node.x and node.y == next_node.y:
            return 2
        elif node.x  == next_node.x and node.y+1 == next_node.y:
            return 3

    if current_direction_index == 2:
        if node.x == next_node.x and node.y-1 == next_node.y:
            return 0
        elif node.x-1  == next_node.x and node.y == next_node.y:
            return 1
        elif node.x  == next_node.x and node.y+1 == next_node.y:
            return 2
        elif node.x+1  == next_node.x and node.y == next_node.y:
            return 3

    if current_direction_index == 3:
        if node.x-1 == next_node.x and node.y == next_node.y:
            return 0
        elif node.x  == next_node.x and node.y+1 == next_node.y:
            return 1
        elif node.x+1  == next_node.x and node.y == next_node.y:
            return 2
        elif node.x == next_node.x and node.y-1 == next_node.y:
            return 3


def create_commands_list(path, start, end, robot_name):
    """
    This function is responsible for doing all the movements that where taken from the path,
    takes in the path and determines whether robot should face a different direction before it 
    starts moving. Keeps track of whether robot is moving in one and if that direction changes, 
    executes the do_forward function with the number of steps passed in as a parameter.
    Then executes the function that changes direction, depending on whether its right or left.
    It does this until the path is done
    """
    loop = 0
    direction = determine_next_node_direction(start, path[0])

    if direction == 1:
        print(main_module.do_right_turn(robot_name)[1])
    if direction == 2:
        print(main_module.do_right_turn(robot_name)[1])
        print(main_module.do_right_turn(robot_name)[1])
    if direction == 3:
        print(main_module.do_left_turn(robot_name)[1])

    previous_direction = current_direction_index
    temp_step = 0
    steps = 1
    while loop < len(path):
        if path[loop] == end:
            print(main_module.do_forward(robot_name, steps)[1])
            break
        direction = determine_next_node_direction(path[loop], path[loop+1])
        if direction == 0:
            steps += 1
        else:
            print(main_module.do_forward(robot_name, steps)[1])
            steps = 1
            if direction == 1:
                print(main_module.do_right_turn(robot_name)[1])
            if direction == 2:
                print(main_module.do_right_turn(robot_name)[1])
                print(main_module.do_right_turn(robot_name)[1])
            if direction == 3:
                print(main_module.do_left_turn(robot_name)[1])
        loop += 1


class Spot:
    """
    This class represents the attribute of each node in my 2d array grid. 
    Row = the outer array of my 2d array where node is
    col = the inner array of my 2d array where node is
    x = the x coordinate of the node.
    y = the y coordinate of the node.
    is_barrier = (bool) checks whether the node is a barrier or not.
    total_rows = the number of total rows in my grid
    total_cols = the total num of columns in my grid
    """
    def __init__(self, row, col, x, y, barrier, total_rows, total_cols):
        self.row = row
        self.col = col
        self.x = x
        self.y = y
        self.is_barrier = barrier
        self.neighbors = []
        self.total_rows = total_rows
        self.total_cols = total_cols


    def get_pos(self):
        return self.x, self.y


    def update_neighbors(self, grid): 
        '''
        checks if neighbors of each element in the grid is an obstacle or not, 
        if it is not an obstacle it adds the element to a list
        '''
        self.neighbors = []
        if self.row < self.total_rows and not grid[self.row + 1][self.col].is_barrier: # DOWN
            self.neighbors.append(grid[self.row + 1][self.col])

        if self.row > 0 and not grid[self.row - 1][self.col].is_barrier: # UP
            self.neighbors.append(grid[self.row -1][self.col])

        if self.col < self.total_cols and not grid[self.row][self.col + 1].is_barrier: # RIGHT
            self.neighbors.append(grid[self.row][self.col + 1])

        if self.col > 0 and not grid[self.row][self.col - 1].is_barrier: # LEFT
            self.neighbors.append(grid[self.row][self.col - 1])

    # def __lt__(self, other):
    #         return False


def h(p1, p2):  
    """
    Params: p1 and p1 are tuples, with x and y coordinates in them, e.g p1 = (15,3)
    :This function returns calculates the distance between 2 coordinates using manhattan distance
    :formula. - "The distance between two points measured along axes at right angles."
    """
    x1, y1 = p1
    x2, y2 = p2
    return abs(x1 - x2) + abs(y1 - y2)


def reconstruct_path(came_from, start, current, robot_name):
    """
    This function recreates the shortest path that the algorithm found, by looking at the end node and 
    seeing which node it came from and where that node came from until we get to the start.
    It save each of those node into a list and then reverses that list and passes it in to the function that will
    move the robot along the path
    """
    my_path = []
    end = current
    while current in came_from:
        my_path.append(current)
        current = came_from[current]
        # border.color('blue')
        
    my_path.reverse() 
    if len(my_path) > 0:
        create_commands_list(my_path, start, end, robot_name) 


def algorithm(grid, start, end, robot_name):
    """
    Current algorithm formula is F(n) = G(n) + H(n).
    :G(n) = Current shortest distance from start node to the current node (n). [NB! This the exact distance]
    :H(n) = ESTIMATE of the distance from current node (n) to the end node. (Check out 'h' function, I use it to calculate the distance)
    :F(n) = G(n) + H(n) (this is the total distance from the beginning to the end.)
    """

    count = 0
    open_set = PriorityQueue()          # using the Priority queue (function I imported) to keep track of the shortest distance with each iteration. Priority queue always returns the smallest element in it
    open_set.put((0, count, start))     # 0 is the current f score of the start node, count is to keep track of when I insert a node into the queue, then I have to put the start node
    path = [start]
    came_from = {}                      # keeps track of where the current node Im working with came from

    g_score = {spot: float('inf') for row in grid for spot in row}  # create a dictionary with all keys being the spot, and the value being the g-score, setting every g_score value to infinity at the beginning because I dont know how long our shortest path will be yet
    g_score[start] = 0                                              # setting the g_score of the start node to 0 because I know the shortest distance from the start to the start node is 0.

    f_score = {spot: float('inf') for row in grid for spot in row}  # create a dictionary with all keys being the spot, and the value being the f-score, setting every f_score value to infinity at the beginning because I dont know how long our total path will be
    f_score[start] = h(start.get_pos(), end.get_pos())              # using the h function I made to find the manhattan distance from the start node to the end node.

    open_set_hash = {start}                 # keeps track of items in the priority queue (will store same stuff as priority queue). With the PriorityQueue you cant check if node is already in there but with this set you can

    while not open_set.empty():             # algorithm runs until open_set is empty. If open set is empty it means weve reached the end.
        current_node = open_set.get()[2]    # fetching the node from the open set, (its at index 2)
        path.append(current_node)
        open_set_hash.remove(current_node)  # removing the current node from the open set
     
        if current_node == end:             # this means weve reached the end, now we have to reconstruct the path
            reconstruct_path(came_from, start, end, robot_name)
            return True
        
        """ This for loop adds all the neighbors of the current node to the 'open_set' and 
        'open_set_hash' with all their different f_scores """

        for neighbor in current_node.neighbors:                     # checks the neighbors of the current node
            temp_g_score = g_score[current_node] + 1                # all the 4 neighbouring nodes are 1 unit away from current node so their g_scores will be  g_score[current] + 1

            if temp_g_score < g_score[neighbor]:                    # when the algorithm starts, I initialised all g_scores to infinity, so the temp_g score is less than infinity
                came_from[neighbor] = current_node
                g_score[neighbor] = temp_g_score
                f_score[neighbor] = temp_g_score + h(neighbor.get_pos(), end.get_pos())
                if neighbor not in open_set_hash:
                    count += 1
                    open_set.put((f_score[neighbor], count, neighbor))
                    open_set_hash.add(neighbor)

    return False


def make_grid_with_obstacles():
    """
    This function creates a 2d array with each element being an object, 'check spot class', each object
    is a coordinate in the grid. It also checks whether that spot is in an obstacle or not. If it is it changes
    'is_barrier' attribute to true.
    :Returns the 2d array as a variable called grid.
    """
    grid = []
    p = 200
    for i in range(401):
        grid.append([])
        q = -100
        for j in range(201):
            if crazy_maze.is_position_blocked(q, p):
                spot = Spot(i, j, q, p, True, 400, 200)
            else:
                spot = Spot(i, j, q, p, False, 400, 200)
            grid[i].append(spot)
            q += 1
        p -= 1

    return grid


def determine_end_node(command_arg):
    """
    This function determines the end coordinates of the robot when the user passes in the commands 'top',
    'bottom', 'right', 'left'. It checks the border of that side to see if there are no obstacles
    then sets the coordinates to a free spot along the border.
    :Returns x and y coordinates
    """
    if command_arg == 'top' or command_arg == '':
        for i in range(0, -101,-1):
            if crazy_maze.is_position_blocked(i, 200) != True:
                return i,200
        for i in range(0, 101):
            if crazy_maze.is_position_blocked(i, 200) != True:
                return i,200
    elif command_arg == 'right':
        for i in range(0,-201,-1):
            if crazy_maze.is_position_blocked(100, i) != True:
                return 100, i
        for i in range(0,201):
            if crazy_maze.is_position_blocked(100, i) != True:
                return 100, i
    elif command_arg == 'left':
        for i in range(0,-201,-1):
            if crazy_maze.is_position_blocked(-100, i) != True:
                return -100, i
        for i in range(0,201):
            if crazy_maze.is_position_blocked(-100, i) != True:
                return -100, i
    elif command_arg == 'bottom':
        for i in range(0,-101,-1):
            if crazy_maze.is_position_blocked(i, -200) != True:
                return i,-200
        for i in range(0,101):
            if crazy_maze.is_position_blocked(i, -200) != True:
                return i,-200


def update_node_neighbors(grid, end_x, end_y):
    """
    This function updates the all the node objects neighbours and sets the current position of the 
    robot as the start node and the node at position end_x, end_y as the end node.
    :Returns the start and end node
    """
    global position_x, position_y

    for row in grid:
        for spot in row:
            spot.update_neighbors(grid)
            if spot.x == position_x and spot.y == position_y:   
                start = spot
            if spot.x == end_x and spot.y == end_y:
                end = spot
    return start, end


def main(robot_name, command_arg=''):
    """
    This function is the main for the world module. 
    -It calls the function that creates my grid,
    -It calls the function that determines the end node.
    -Calls the function that updates all the neighbours for each node.
    -Calls algorithm function
    """
    global position_x, position_y
    
    grid = make_grid_with_obstacles()

    end_x, end_y = determine_end_node(command_arg)

    run = True
    started = False
    
    while run:
        start, end = update_node_neighbors(grid, end_x, end_y)
        algorithm(grid, start, end, robot_name)

        break


def setup_world():
    pass

