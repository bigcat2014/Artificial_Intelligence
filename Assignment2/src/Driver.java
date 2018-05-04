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
        PathFinder uniformCostSearch = new PathFinder(Driver::ucsHeuristic, DEBUG);
        PathFinder aStarNormal = new PathFinder(Driver::aStarNormalHeuristic, DEBUG);
        PathFinder aStarCustom = new PathFinder(Driver::aStarCustomHeuristic, DEBUG);

        ParseArgs(args);

        KeyValuePair board = CreateBoard();
        if (DEBUG) { System.out.printf("Board Size: %d\n", board.getSize()); }
        if (DEBUG) { System.out.printf("File name: %s\n", fileName); }

        System.out.println("Uniform Cost search:\n");
        System.out.print("Initial board state: ");
        board.PrintBoard();
        int cost = uniformCostSearch.FindPath(board);
        if (cost != -1) {
            System.out.printf("Path found!\nCost: %d\n", cost);
            ArrayList<KeyValuePair> expanded = uniformCostSearch.getExpanded();
            ArrayList<KeyValuePair> fringe = uniformCostSearch.getFringe();
            System.out.printf("Number of expanded nodes: %d\n", expanded.size());
            for (KeyValuePair pair : expanded) { System.out.println(pair.toString()); }
            System.out.printf("Number of nodes in fringe: %d\n", fringe.size());
            for (KeyValuePair pair : fringe) { System.out.println(pair.toString()); }
        } else {
            System.out.println("Path not found.");
        }

        System.out.println("\nA* Normal Heuristic:\n");
        System.out.print("Initial board state: ");
        board.PrintBoard();
        cost = aStarNormal.FindPath(board);
        if (cost != -1) {
            System.out.printf("Path found!\nCost: %d\n", cost);
            ArrayList<KeyValuePair> expanded = aStarNormal.getExpanded();
            ArrayList<KeyValuePair> fringe = aStarNormal.getFringe();
            System.out.printf("Number of expanded nodes: %d\n", expanded.size());
            //for (KeyValuePair pair : expanded) { System.out.println(pair.toString()); }
            System.out.printf("Number of nodes in fringe: %d\n", fringe.size());
        } else {
            System.out.println("Path not found.");
        }

        System.out.println("\nA* Custom Heuristic:\n");
        System.out.print("Initial board state: ");
        board.PrintBoard();
        cost = aStarCustom.FindPath(board);
        if (cost != -1) {
            System.out.printf("Path found!\nCost: %d\n", cost);
            ArrayList<KeyValuePair> expanded = aStarCustom.getExpanded();
            ArrayList<KeyValuePair> fringe = aStarCustom.getFringe();
            System.out.printf("Number of expanded nodes: %d\n", expanded.size());
            //for (KeyValuePair pair : expanded) { System.out.println(pair.toString()); }
            System.out.printf("Number of nodes in fringe: %d\n", fringe.size());
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
    private static KeyValuePair CreateBoard(){
        File f;
        Scanner fileScanner = null;

        try {
            f = new File("src\\" + fileName);
            fileScanner = new Scanner(f);
        } catch (FileNotFoundException e){
            System.out.printf("File %s not found\n", fileName);
            System.exit(1);
        }

        fileScanner.useDelimiter(" ");

        ArrayList<Integer> tileList = new ArrayList<Integer>(boardSize);
        while (fileScanner.hasNextInt()) { tileList.add(fileScanner.nextInt()); }
        return new KeyValuePair(tileList, 0);
    }
    private static void ArgumentError(){
        System.out.println("Incorrect command line options and arguments");
        System.out.println("[-d] BOARD_SIZE FILE_NAME(*.txt)");
        System.exit(1);
    }

    private static int ucsHeuristic(KeyValuePair pair){ return 0; }
    private static int aStarNormalHeuristic(KeyValuePair pair){
        int index = 0;
        int val;
        int numOutOfOrder = 0;

        for (int i = 0; i < pair.getSize(); i++){
            val = pair.get();
            if (val != i + 1) { numOutOfOrder ++; }
            pair.MoveForward(1);
        }

        return numOutOfOrder;
    }
    private static int aStarCustomHeuristic(KeyValuePair pair){ return 0; }
}