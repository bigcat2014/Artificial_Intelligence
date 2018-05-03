/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Driver {
    private static boolean DEBUG = false;
    private static int boardSize = 0;
    private static String fileName = "";

    public static void main(String args[]){
        PathFinder uniformCostSearch = new PathFinder(new Heuristic(Driver::ucsHeuristic));
        PathFinder aStarNormal = new PathFinder(new Heuristic(Driver::aStarNormalHeuristic));
        PathFinder aStarCustom = new PathFinder(new Heuristic(Driver::aStarCustomHeuristic));

        ParseArgs(args);

        Board board = CreateBoard();
        Board finalboard = board.getFinalState();
        if (DEBUG) { System.out.printf("Board Size: %d\n", board.getSize()); }
        if (DEBUG) { System.out.printf("File name: %s\n", fileName); }
        board.PrintBoard();
        finalboard.PrintBoard();
        int cost = aStarNormal.FindPath(board);
        if (cost != -1) {
            System.out.printf("Path found!\nCost: %d\n", cost);
        } else {
            System.out.println("Path not found.");
        }
    }

    private static void ParseArgs(String args[]){
        if (args.length > 1 && args.length < 4) {
            for (String arg : args) {
                // If -d option, set DEBUG flag and continue
                if (arg.toLowerCase().equals("-d")) { DEBUG = true; continue; }

                // If the option is an integer, store it as the board size
                try { boardSize = Integer.parseInt(arg); continue; }
                catch (NumberFormatException ignored) {}

                // Pattern: \w+\.txt$
                // Matches file names with .txt suffix
                if (Pattern.matches("\\w+\\.txt$", arg)){ fileName = arg; }
                else { ArgumentError(); }
            }
        } else { ArgumentError(); }
    }
    private static Board CreateBoard(){
        File f;
        Scanner fileScanner = null;
        int numTiles = 0;

        try {
            f = new File("src\\" + fileName);
            fileScanner = new Scanner(f);
        } catch (FileNotFoundException e){
            System.out.printf("File %s not found\n", fileName);
            System.exit(1);
        }

        fileScanner.useDelimiter(" ");

        Board board = new Board(new ArrayList<Tile>(boardSize));
        while (fileScanner.hasNextInt() && numTiles < boardSize) { board.addTile(new Tile(fileScanner.nextInt())); numTiles++; }

        return board;
    }
    private static void ArgumentError(){
        System.out.println("Incorrect command line options and arguments");
        System.out.println("[-d] BOARD_SIZE FILE_NAME(*.txt)");
        System.exit(1);
    }

    private static int ucsHeuristic(KeyValuePair pair){ return 0; }
    private static int aStarNormalHeuristic(KeyValuePair pair){
        int numOutOfOrder = 0;
        Board currState = pair.getBoard();
        Board finalState = currState.getFinalState();

        for (int i = 0; i < currState.getSize(); i++){
            if (!finalState.getCurrentTile().getValue().equals(currState.getCurrentTile().getValue())) { numOutOfOrder ++; }
            currState.MoveForward(1);
            finalState.MoveForward(1);
        }

        return numOutOfOrder;
    }
    private static int aStarCustomHeuristic(KeyValuePair pair){ return 0; }
}