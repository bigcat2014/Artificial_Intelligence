/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 1
 */

Setup:
    Open the files in IDE of choice and set Driver.main as the startup method.


Running:
    Program can be run normally, or with option -d to enter debug mode for more information.

    When the program is run, it will prompt the user for either random mode, where the cop car path is completely random,
    or planned mode, where the car drives straight until it bumps, giving it the size of the map, and then drives the
    perimeter of the map, slowly closing in on the center of the map. It does this without bumping a second time and
    without crossing its own path.

Output:
    In debug mode it outputs the mode selected, the actual accident site, the path of the car, the final position of the car (accident site found),
    and the total time it took to find the site.

    In normal mode, it outputs the final position of the car (accident site found) and the total time it took to find the site.