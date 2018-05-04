/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
import java.util.ArrayList;
import java.util.function.Function;

public class AStar {
    private final boolean DEBUG;

    private ArrayList<Node> expanded;
    private ArrayList<Node> fringe;

    private Function<Node, Integer> heuristic;

    public AStar(Function<Node, Integer> h, boolean debug){
        this.DEBUG = debug;
        this.heuristic = h;
        this.expanded = new ArrayList<Node>();
        this.fringe = new ArrayList<Node>();
    }

    public ArrayList<Node> getExpanded(){ return this.expanded; }

    public ArrayList<Node> getFringe() { return this.fringe; }

//    Initial state of board:
//            *************** Begin Fringe List *************
//    Fringe node 1 with state and heuristic
//    Fringe node 2 with state and heuristic
//    Fringe node 3 with state and heuristic
//    Fringe node 4 with state and heuristic
//    *************** End Fringe List *************
//    Node among the fringe selected for expansion -Heuristic value
//    Move made (rotate or shift n positions)
//    New state of the board
//    *************** Begin Fringe List *************
//    Fringe node 1 with state and heuristic
//    Fringe node 2 with state and heuristic
//    Fringe node 3 with state and heuristic
//    Fringe node 4 with state and heuristic
//    Fringe node 5 with state and heuristic
//    Fringe node 6 with state and heuristic
//    *************** End Fringe List *************
//    Node among the fringe selected for expansion -Heuristic value
//    Move made (rotate or shift left/right n positions)
//    New state of the board

    public int FindPath(Node currentBoard){
//        ArrayList<String> moves;
//        ArrayList<Node> prevFringe;
//        Node previousPair = null;
        Node currentPair;
        int finalCost = -1;
        String finalHash = Node.hash(currentBoard.getFinalBoard());

        this.fringe.add(new Node(currentBoard));
//        prevFringe = (ArrayList<Node>) this.fringe.clone();
//        System.out.println("Initial State: " + currentBoard);
        do {
            if ((currentPair = getLowestCostPair()) == null) { break; }

//            if (previousPair != null) {
//                moves = printMove(new Node(previousPair), new Node(currentPair));
//                for (String str : moves) { System.out.print(str); }
//            } else {
//                System.out.print(getFringeString(prevFringe));
//                System.out.println("Node selected for expansion: " + currentPair.getBoardString() + "\tHeuristic: " + h(currentPair));
//            }
//            previousPair = currentPair;
//            prevFringe = (ArrayList<Node>) this.fringe.clone();

            if (expand(currentPair, finalHash)) { finalCost = currentPair.getCost(); break; }
        } while (!fringe.isEmpty());
        return finalCost;
    }

    private int g(Node pair){ return pair.getCost(); }
    private int h(Node pair){ return heuristic.apply(pair); }

    private boolean expand(Node pair, String finalHash) {
        this.fringe.remove(pair);
        this.expanded.add(new Node(pair));
        if (Node.hash(pair).equals(finalHash)) { return true; }
        getChildren(pair);
        return false;
    }
    private void getChildren(Node pair){
        Node child;
        int cost = pair.getCost();

        for (int i = 0; i < pair.getSize(); i++){
            child = new Node(pair);
            child.MoveForward(i);
            child.Rotate();
            if(!(this.expanded.contains(child) || this.fringe.contains(child))) { this.fringe.add(new Node(child, cost + 1)); }
            else{
                if (this.expanded.contains(child)){
                    int nodeIndex = this.expanded.indexOf(child);
                    Node node = this.expanded.get(nodeIndex);
                    if (node.getCost() > cost + 1){ node.setCost(cost + 1); }
                }
                else {
                    int nodeIndex = this.fringe.indexOf(child);
                    Node node = this.fringe.get(nodeIndex);
                    if (node.getCost() > cost + 1){ node.setCost(cost + 1); }
                }
            }
        }
    }

    private Node getLowestCostPair(){
        if (this.fringe.isEmpty()) { return null; }
        Node currentPair = this.fringe.get(0);
        Node returnPair = currentPair;
        int totalCost;
        int cost = g(currentPair) + h(currentPair);

        for (int i = 1; i < this.fringe.size(); i++){
            currentPair = this.fringe.get(i);
            totalCost = g(currentPair) + h(currentPair);
            if (totalCost < cost) {
                returnPair = currentPair;
                cost = totalCost;
            }
        }
        return returnPair;
    }

//    private String getFringeString(ArrayList<Node> fringe){
//        StringBuilder returnString = new StringBuilder();
//        Node currentNode;
//        returnString.append("*************** Begin Fringe List *************\n");
//        for (int i = 0; i < fringe.size(); i++){
//            currentNode = fringe.get(i);
//            returnString.append("Fringe node: ");
//            returnString.append(i + 1);
//            returnString.append(";\tstate: ");
//            returnString.append(currentNode.getBoardString());
//            returnString.append(";\tHeuristic: ");
//            returnString.append(h(currentNode));
//            returnString.append("\n");
//        }
//        returnString.append("**************** End Fringe List **************\n");
//
//        return returnString.toString();
//    }
//    public ArrayList<String> printMove(Node prevState, Node currentState){
//        ArrayList<String> returnList = new ArrayList<String>();
//
//        int cost = prevState.getCost();
//        int prevPosition;
//        int index;
//        int num;
//
//        returnList.add(0, "Node selected for expansion: " + currentState.getBoardString() + "\tHeuristic: " + h(prevState) + "\n");
//        returnList.add(0, "\n" + getFringeString(this.fringe));
//        returnList.add(0, "Board State:\t" + currentState.getBoardString() + "\tCost: " + cost + "\n\n");
//        returnList.add(0, "Rotate\n");
//        prevPosition = prevState.getPosition();
//
//        num = currentState.get();
//
//        cost = prevState.getCost();
//
//        index = -1;
//        while(index < currentState.getSize()){
//            index++;
//            if (currentState.get() == num){ break; }
//            currentState.MoveForward(1);
//        }
//        if (index != 0) {
//            prevState.setPosition(prevPosition);
//            returnList.add(0, "Node selected for expansion: " + prevState.getBoardString() + "\tHeuristic: " + h(prevState));
//            returnList.add(0, "\n " + getFringeString(this.fringe));
//            returnList.add(0, "Board State:\t" + prevState.getBoardString() + "\tCost: " + cost + "\n");
//            returnList.add(0, "Move forward " + index + " Spaces\n");
//        }
//
//        return returnList;
//    }
}
