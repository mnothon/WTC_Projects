

# TODO: Decompose into functions
def prints_move_forward_turn_right(my_length, my_degrees):
    print("* Move Forward "+str(my_length))
    print("* Turn Right "+str(my_degrees)+" degrees")


def move_square(size, degrees):
    # moves square, doesnt return anything
    print("Moving in a square of size "+str(size))
    for i in range(4):
        prints_move_forward_turn_right(size, degrees)


def move_rectangle(length, width, degrees):
    # moves rectangle, doesnt return anything
    print("Moving in a rectangle of "+str(length)+" by "+str(width))
    for i in range(2):
        prints_move_forward_turn_right(length, degrees)
        prints_move_forward_turn_right(width, degrees)


def move_circle():
    # moves circle, doesnt return anything
    print("Moving in a circle")
    degrees = 1
    for i in range(360):
        length = 1
        prints_move_forward_turn_right(length, degrees)


def square_dancing(length, degrees):
    # moves dancing square, doesnt return anything
    size = 20
    print("Square dancing - 3 squares of size 20")
    for i in range(3):
        print("* Move Forward "+str(length))
        print("Moving in a square of size 20")
        for j in range(4):
            prints_move_forward_turn_right(size, degrees)


def crop_circles():
    # moves crop_circles, doesnt return anything
    degrees = 1
    length = 1
    print("Crop circles - 4 circles")
    for i in range(4):
        print("* Move Forward 20")
        print("Moving in a circle")
        for k in range(360):
            prints_move_forward_turn_right(length, degrees)


def move():
    """ 
    This function calls all the other functions that determine the robots movements
    """
    size = 10
    length = 20
    width = 10
    degrees = 90
    
    move_square(size, degrees)
    move_rectangle(length, width, degrees)
    move_circle()
    square_dancing(length, degrees)
    crop_circles()


def robot_start():
    move()
    

if __name__ == "__main__":
    robot_start()
