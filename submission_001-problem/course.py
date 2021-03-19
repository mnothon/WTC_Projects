
def my_sort(student_progress):
    return student_progress[3]

def create_outline():
    """
    TODO: implement your code here
    """
    topics = {'Introduction to Python',
              'Tools of the Trade',
              'How to make decisions',
              'How to repeat code',
              'How to structure data',
              'Functions',
              'Modules'}
    print('Course Topics:')
    python_course = {}
    problems = []
    
    #loop to populate 'problems' with entries
    for i in range(1,4):
        problems.append(f"Problem {i}")
    
    #loop to print all the topics
    sorted_list = sorted(list(topics))
    
    for item in sorted_list:
        print(f'* {item}')
        python_course[item] = problems
    print("Problems:")
    
    #loop to print topics + problems
    for key, value in python_course.items():
        print(f'* {key} : {", ".join(value)}')
    
    progress = [('Nyari', 'Introduction to Python', 'Problem 2', '[STARTED]'),
                ('Rhys', 'Tools of the Trade', 'Problem 1', '[COMPLETED]'),
                ('Dylan', 'Modules', 'Problem 3', '[GRADED]'),
                ('Andreas', 'How to structure data', 'Problem 1', '[COMPLETED]')]
    
    print('Student Progress:')
    sorted_list = sorted(progress, key=my_sort, reverse = True)
    for i in range(len(sorted_list)):
        print(f'{i + 1}. {sorted_list[i][0]} - {sorted_list[i][1]} - {sorted_list[i][2]} {sorted_list[i][3]}')



if __name__ == "__main__":
    create_outline()
