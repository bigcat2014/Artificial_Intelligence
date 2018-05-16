/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */

 I am using 2 coupons for this assignment
 
Setup:
    Open the files in IDE of choice and set Driver.main as the startup method.


Running:
    Program can be run normally, or with option -d to enter debug mode for more information.

    When the program is run, it will read the file specified in the arguments, which contains a board pattern to be solved using the A* algorithm.
	The board is solved 3 times with 3 different heuristics used for A*. The first one uses a heuristic of 0. This makes it equivalent to a
	uniform cost search algorithm. The second heuristic is just the number of out of order tiles; for example, the board 43215 has 4 tiles out
	of order. The final heuristic uses a normalized accumulative distance from the correct position; for example, the board 512436 has a heuristic
	of (4 + 1 + 1 + 0 + 2 + 0)/4 = 2. The formula is the distances all added together divided by the greatest distance. This formula allows for
	no one distance to have more of an effect on the heuristic than the others.

Output:
    In debug mode it outputs the path, move, fringe list, expanded node, heuristic, and cost along the way from start board to final board.

    In normal mode, it outputs the path, move, and cost alont the way from start board to final board.