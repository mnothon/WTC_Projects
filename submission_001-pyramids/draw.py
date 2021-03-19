

# TODO: Step 1 - get shape (it can't be blank and must be a valid shape!)
def get_shape():
    shape = input("Shape?: ")
    shape = shape.lower().strip()
    while shape.lower() not in ("square", "triangle", "pyramid", "parallelogram","rhombus","rectangle"):
        shape = input("Shape?: ")
        shape = shape.lower().strip()
    return shape


# TODO: Step 1 - get height (it must be int!)
def get_height():
    height = input('Height?: ')
    
    while height.isdigit() == False:
        height = input('Height?: ')

    height = int(height)
    
    while height < 1 and height > 80:
        height = input('Height?: ')
    return height


# TODO: Step 2
def draw_pyramid(height, outline):
    count = 1
    if outline == False:
        if height == 1:
            print('*')
        else:
            while count <= height:
                i = count
                while i < height:
                    print(' ', end="")
                    i+=1
                x = 1
                while x <= ((2*count) - 1):
                    print('*', end="")
                    x+=1
                print('')
                count += 1
    else:
        count = 1
        if height == 1:
            print('*')
        else:
            d = height - 1
            #c is to keep track of the spaces to print, starts at -1 so that the second line of pyramid has 1 space.
            c = -1

            #prints the first star
            while count <= height:
                if count == 1:
                    i = 1
                    while i <= height-1:
                        print(' ', end="")
                        i+=1
                    print('*')
                    count += 1
                elif count > 1 and count < height:
                    i = count
                    #prints space before first star
                    while i <= height - 1:
                        print(' ', end="")
                        i += 1
                    print('*', end="")
                    x = 1
                    #add 2 to c each iteration because space increase by 2 each time height increases
                    while x <= (c + 2):
                        print(' ', end="")
                        x+=1
                    print('*')
                    c+=2
                    count += 1
                else:
                    x = 0
                    #while loop to print the last line of stars
                    while x < ((2*height) - 1):
                        print('*', end="")
                        x+=1
                        count += 1
                    print("")
            
            



# TODO: Step 3
def draw_square(height, outline):
    i = 0
    if outline == False:
        while i < height:
            d = 0
            while d < height:
                print('*', end="")
                d+=1
            print('')
            i += 1
    else:
        #if statement to check if we are printing the first or last line
        while i < height:
            if (i == 0 or i == height - 1):
                for d in range(height): 
                    print('*',end="")
                print('')
                i += 1
            else:
                print('*', end="")
                for d in range(height - 2): 
                    print(' ',end="")
                print('*')
                i += 1



# TODO: Step 4
def draw_triangle(height, outline):
    i = 0
    if outline == False:
        while i < height:
            d = -1
            while d < i:
                print('*', end="")
                d += 1
            print('')
            i += 1
    else:
        while i < height:
            #if statement checks if its the first 2 line of triangle and prints them out
            if i < 2:
                d = -1
                while d < i:
                    print('*', end="")
                    d += 1
                print('')
                i += 1
            elif i < height - 1:
                print('*', end="")
                for d in range(i - 1):
                    print(' ', end="")
                print('*')
                i += 1
            else:
                for d in range(height):
                    print('*', end="")
                print("")
                i += 1           

def draw_parallelogram(height, outline):
    i = 0
    if outline == False:
        while i < (height):
            for d in range(height + height - 1):
                print('*', end="")
            if i == (height - 1):
                print("")
                i += 1
                continue
            print("")
            print(" " *(i+1), end="")
            i += 1
    else:
        while i < height:
            if i == 0:
                print("*" *(height + height - 1))
                i += 1
            elif i == (height - 1):
                print(" "*(height - 1), end="")
                print("*" *(height + height - 1))
                i += 1
            else:
                while i < (height - 1):
                    print(" " *(i), end="")
                    print("*", end="")
                    print(" " *(height + height - 3), end="")
                    print("*")
                    i += 1

def draw_rhombus(height, outline):
    i = 0
    if outline == False:
        while i < (height):
            for d in range(height):
                print('*', end="")
            if i == (height - 1):
                print("")
                i += 1
                continue
            print("")
            print(" " *(i+1), end="")
            i += 1
    else:
        while i < height:
            if i == 0:
                print("*" *(height))
                i += 1
            elif i == (height - 1):
                print(" "*(height - 1), end="")
                print("*" *(height))
                i += 1
            else:
                while i < (height - 1):
                    print(" " *(i), end="")
                    print("*", end="")
                    print(" " *(height - 2), end="")
                    print("*")
                    i += 1

def draw_rectangle(height, outline):
    i = 0
    if outline == False:
        while i < height:
            d = 0
            while d < height + height - 1:
                print('*', end="")
                d+=1
            print('')
            i += 1
    else:
        #if statement to check if we are printing the first or last line
        while i < height:
            if (i == 0 or i == height - 1):
                for d in range(height + height - 1):
                    print('*',end="")
                print('')
                i += 1
            else:
                print('*', end="")
                for d in range(height + height - 3):
                    print(' ',end="")
                print('*')
                i += 1




# TODO: Steps 2 to 4, 6 - add support for other shapes
def draw(shape, height, outline):
    if shape == 'pyramid':
        draw_pyramid(height, outline)
    elif shape == 'square':
        draw_square(height, outline)
    elif shape == 'triangle':
        draw_triangle(height, outline)
    elif shape == 'parallelogram':
        draw_parallelogram(height, outline)
    elif shape == 'rhombus':
        draw_rhombus(height, outline)
    elif shape == 'rectangle':
        draw_rectangle(height, outline)


# TODO: Step 5 - get input from user to draw outline or solid
def get_outline():
    outline = input('Outline only (y/n): ')
    outline = outline.lower()
    while outline not in ("y", "n", ""):
        outline = input('Outline only (y/n): ')
        outline = outline.lower()
    if outline == 'y':
        return True
    elif outline == '' or outline == 'n':
        return False


if __name__ == "__main__":
    shape_param = get_shape()
    height_param = get_height()
    outline_param = get_outline()
    draw(shape_param, height_param, outline_param)

