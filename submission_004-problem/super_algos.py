
def find_min(element):
    """ 
    for loop to find any wrong elements in the list
    returns -1 if any of the elements is not an int    
    """
    for i in range(len(element)):
        if type(element[i]) != int:
            return -1 
    """ 
    if statement to check if list is empty
    returns -1 if list is empty
    """
    if len(element) == 0:
        """ returns -1 if there are no elements in the list """
        return -1
    elif len(element) == 1:
        """returns first element if theres length of element is 1 """
        return element[0]
    else:
        minNumber = find_min(element[1:])
        min = element[0]
        if minNumber < min:
            min = minNumber
        return min
        

def sum_all(element, sum=0, n=0):
    """
    pass in placeholder parameters sum and n and initialize them to zero
    """
    
    """ 
    for loop to find any wrong elements in the list
    returns -1 if any of the elements is not an int    
    """
    for i in range(len(element)):
        if type(element[i]) != int:
            return -1
    if len(element) == 0:
        return -1
    elif len(element) == 1:
        return element[0]
    if n==len(element):
        return sum
    else:
        n += 1
        sum = sum_all(element, sum, n)
        sum += element[n-1]
        return sum


def find_possible_strings(character_set, a):
    """
    this is the function that will be called. Creates an empty list and calls
    secondary function and saves the result of that function to the empty list
    returns it.
    Also check for any invalid entries in the list are made here.
    Returns the correct list at the end.
    """
    n = len(character_set)
    my_list = []
    for i in range(n):
        my_char = str(character_set[i])
        if my_char.isdigit():
            return my_list
    my_list = find_possible_strings2(character_set,a,"", n, my_list)
    return my_list


def find_possible_strings2(set, a, newstr, n, my_list): 
    """
    second function that recursively calls itself, each time it recurses it saves
    a new letter to 'newstr' until function reaches base case, which happens when 'a'
    is equal to zero, that means that the length of the string has been reached,
    that string, 'newstr', then gets appended to my list.
    function returns the full list at the end.
    """
    if (a == 0) : 
        my_list.append(newstr)
        return my_list
    for i in range(n):
        newPrefix = newstr + str(set[i])
        find_possible_strings2(set, (a - 1), newPrefix, n, my_list)
    return my_list