import random

# my global variables
obstacles = []


def create_obstacle():
    """
    Creates a random obstacle within the given parameters
    """
    x = random.randint(-100, 100)
    y = random.randint(-200, 200)
    return (x,y)


def does_it_overlap(obstacles, point):
    """
    This function checks if the obstacle we are trying to create overlaps
    with any of the obstacles already in the list.
    :Params - obstacles - (list of tuples) list of aleady existing obstacles
            - point - (tuple) the new obstacle we are trying to create
    :Return - return true if it overlaps, false if it doesnt
    """
    if obstacles == []:
        return False
    for obstacle in obstacles:   
        if point[0] >= obstacle[0] and point[0] <= obstacle[0]+4: 
            if point[1] >= obstacle[1] and point[1] <= obstacle[1]+4:
                return True
    return False


def spawned_over_robot(point):
    """
    Checks whether the obstacle point is spawning in the center. 
    Which would obstruct the robot.
    """
    if point[0] >= -4 and point[0] <= 0:
        if point[1] >= -4 and point[1] <= 0:
            return True
    return False


def get_obstacles():
    """
    Function creates a list of obstacles. Calls the the create_obstacle function and 
    does_it_overlap function.
    :Return - (list of tuples) returns a list with all the obstacles in it as tuples.
    """
    global obstacles
    obstacles = []

    for i in range(random.randint(0,10)):
        point = create_obstacle()
        while does_it_overlap(obstacles, point) and spawned_over_robot(point):
            point = create_obstacle()
        obstacles.append(point)        
    return obstacles
        

def is_position_blocked(x2, y2):
    """
    This function checks if the the position that the robot is trying to 
    land on blocked.
    :Params - x2 and xy are the coordinates of the new potential position of 
            the robot
    :Return - returns True if position overlaps and false if it doesnt
    """
    point = (x2,y2)
    if does_it_overlap(obstacles, point):
        return True
    return False


def is_path_blocked(x1,y1,x2,y2):
    """
    This function checks if the path that the robot is heading to blocked
    depending on where the new coordinates say the robot should go.
    :Params - x1 and y1 are the coordinates of the position of the robot. x2 and y2 are 
            the coordinates of the new position robot should go to.
    :Return - Returns true if there is an obstacle in the way and false if there isn't.
    """
    min_x = min(x1, x2)
    max_x = max(x1, x2)
    min_y = min(y1, y2)
    max_y = max(y1, y2)
    
    blocked = False
    if x1 == x2:
        for i in range(min_y, max_y + 1):
            if is_position_blocked(x1, i) == True:
                blocked = True           
    elif y1 == y2:
        for i in range(min_x, max_x + 1):
            if is_position_blocked(i, y1) == True:
                blocked = True
    return blocked


def delete_list():
    """
    Initialize obstacles back to zero
    """
    obstacles = []