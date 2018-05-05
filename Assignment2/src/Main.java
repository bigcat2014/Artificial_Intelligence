/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Main {
    private static boolean DEBUG = false;
    private static int boardSize = 0;
    private static String fileName = "";

    public static void main(String args[]){
        AStar uniformCostSearch = new AStar(Main::ucsHeuristic);
        AStar aStarNormal = new AStar(Main::aStarNormalHeuristic);
        AStar aStarCustom = new AStar(Main::aStarCustomHeuristic);

        ParseArgs(args);

        Node board = CreateBoard();
        if (DEBUG) { System.out.printf("Board Size: %d\n", board.getSize()); }
        if (DEBUG) { System.out.printf("File name: %s\n", fileName); }

        System.out.println("Uniform Cost search:\n");
        int cost = uniformCostSearch.FindPath(board);
        if (cost != -1) {
            System.out.println("Path found!");

            ArrayList<Node> expanded = uniformCostSearch.getExpanded();
            ArrayList<String> finalPath = getPath(expanded, Main::ucsHeuristic);
            for (String str : finalPath) {
                System.out.println(str);
            }
            System.out.printf("\nNumber of nodes expanded: %d\n", expanded.size());

            ArrayList<Node> fringe = uniformCostSearch.getFringe();
            System.out.printf("Number of nodes in fringe: %d\n", fringe.size());
            System.out.printf("Path cost: %d\n", cost);
        } else {
            System.out.println("Path not found.");
        }
        System.out.println("\n\nA* with provided heuristic:\n");
        cost = aStarNormal.FindPath(board);
        if (cost != -1) {
            System.out.println("Path found!");

            ArrayList<Node> expanded = aStarNormal.getExpanded();
            ArrayList<String> finalPath = getPath(expanded, Main::aStarNormalHeuristic);
            for (String str : finalPath) {
                System.out.println(str);
            }
            System.out.printf("\nNumber of nodes expanded: %d\n", expanded.size());

            ArrayList<Node> fringe = aStarNormal.getFringe();
            System.out.printf("Number of nodes in fringe: %d\n", fringe.size());
            System.out.printf("Path cost: %d\n", cost);
        } else {
            System.out.println("Path not found.");
        }

        System.out.println("\n\nA* with Custom heuristic:\n");
        cost = aStarCustom.FindPath(board);
        if (cost != -1) {
            System.out.println("Path found!");

            ArrayList<Node> expanded = aStarCustom.getExpanded();
            ArrayList<String> finalPath = getPath(expanded, Main::aStarCustomHeuristic);
            for (String str : finalPath) {
                System.out.println(str);
            }
            System.out.printf("\nNumber of nodes expanded: %d\n", expanded.size());

            ArrayList<Node> fringe = aStarCustom.getFringe();
            System.out.printf("Number of nodes in fringe: %d\n", fringe.size());
            System.out.printf("Path cost: %d\n", cost);
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
    private static Node CreateBoard(){
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
        return new Node(tileList, 0);
    }
    private static ArrayList<String> getPath(ArrayList<Node> list, Function<Node, Float> h){
        ArrayList<String> returnList = new ArrayList<String>();
        Node prevState = list.get(list.size() - 1);
        Node currentState = new Node(prevState);
        int cost = currentState.getCost();
        int parentIndex;
        int prevPosition;
        int index;
        int num;

        returnList.add(0, "FinalState:\t\t" + currentState.toString());

        while(cost != 1) {
            if (DEBUG) {
                returnList.add(0, "Board State:\t" + currentState.getBoardString() + "\n");
                returnList.add(0, "Rotate\t");
            } else {
                returnList.add(0, "Board State:\t" + currentState.toString());
            }

            currentState.Rotate();

            parentIndex = list.indexOf(currentState);
            prevState = list.get(parentIndex);
            prevPosition = prevState.getPosition();

            if (DEBUG) {
                returnList.add(0, "Expanded State:\t" + currentState.getBoardString() + "\tHeuristic: " + h.apply(prevState));
                returnList.add(0, prevState.getFringe());
            } else {
                returnList.add(0, "Rotate\t");
            }

            num = currentState.get();

            cost = prevState.getCost();

            index = -1;
            while(index < list.size()){
                index++;
                if (prevState.get() == num){ break; }
                prevState.MoveForward(1);
            }

            prevState.setPosition(prevPosition);
            if (index < list.size() && index != 0) {
                if (DEBUG) {
                    returnList.add(0, "Board State:\t" + currentState.getBoardString() + "\n");
                    returnList.add(0, "Move forward " + index + " Spaces");
                    returnList.add(0, "Expanded State:\t" + prevState.getBoardString() + "\tHeuristic: " + h.apply(prevState));
                    returnList.add(0, prevState.getFringe());
                } else {
                    returnList.add(0, "Board State:\t" + currentState.toString());
                    returnList.add(0, "Move forward " + index + " Spaces");
                }
            }
            currentState = prevState;
        }

        if (DEBUG) {
            returnList.add(0, "Board State:\t" + currentState.getBoardString() + "\n");
            returnList.add(0, "Rotate\t");
        } else {
            returnList.add(0, "Board State:\t" + currentState.toString());
        }

        currentState.Rotate();

        parentIndex = list.indexOf(currentState);
        prevState = list.get(parentIndex);
        prevPosition = prevState.getPosition();

        if (DEBUG) {
            returnList.add(0, "Expanded State:\t" + currentState.getBoardString() + "\tHeuristic: " + h.apply(prevState));
            returnList.add(0, prevState.getFringe());
        } else {
            returnList.add(0, "Rotate\t");
        }

        num = currentState.get();

        index = -1;
        while(index < list.size()){
            index++;
            if (prevState.get() == num){ break; }
            prevState.MoveForward(1);
        }

        prevState.setPosition(prevPosition);
        if (index < list.size() && index != 0) {
            if (DEBUG) {
                returnList.add(0, "Board State:\t" + currentState.getBoardString() + "\n");
                returnList.add(0, "Move forward " + index + " Spaces");
                returnList.add(0, "Expanded State:\t" + prevState.getBoardString() + "\tHeuristic: " + h.apply(prevState));
                returnList.add(0, prevState.getFringe());
            } else {
                returnList.add(0, "Board State:\t" + currentState.toString());
                returnList.add(0, "Move forward " + index + " Spaces");
            }
        }
        returnList.add(0, "Initial State:\t" + prevState.toString() + "\n");

        return returnList;
    }
    private static void ArgumentError(){
        System.out.println("Incorrect command line options and arguments");
        System.out.println("[-d] BOARD_SIZE FILE_NAME(*.txt)");
        System.exit(1);
    }

    private static float ucsHeuristic(Node pair){ return 0; }
    private static float aStarNormalHeuristic(Node pair){
        Node pairClone = new Node(pair);
        int numOutOfOrder = 0;

        for (int i = 0; i < pairClone.getSize(); i++){
            if (pairClone.get() != i + 1) { numOutOfOrder ++; }
            pairClone.MoveForward(1);
        }

        return numOutOfOrder;
    }
    private static float aStarCustomHeuristic(Node pair){
        Node pairClone = new Node(pair);
        int dist = 0;
        int val;
        int maxVal = 0;

        for (int i = 1; i <= pairClone.getSize(); i++){
            val = pairClone.get();
            dist += Math.abs(val - i);
            if (val > maxVal) { maxVal = val; }
            pairClone.MoveForward(1);
        }

        return dist / maxVal;
    }
}