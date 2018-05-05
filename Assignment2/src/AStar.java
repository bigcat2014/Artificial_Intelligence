/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
import java.util.ArrayList;
import java.util.function.Function;

public class AStar {
    private ArrayList<Node> expanded;
    private ArrayList<Node> fringe;

    private Function<Node, Float> heuristic;

    public AStar(Function<Node, Float> h){
        this.heuristic = h;
        this.expanded = new ArrayList<Node>();
        this.fringe = new ArrayList<Node>();
    }

    public ArrayList<Node> getExpanded(){ return this.expanded; }

    public ArrayList<Node> getFringe() { return this.fringe; }

    public int FindPath(Node currentBoard){
        Node currentPair;
        int finalCost = -1;
        String finalHash = Node.hash(currentBoard.getFinalBoard());

        this.fringe.add(new Node(currentBoard));
        do {
            if ((currentPair = getLowestCostPair()) == null) { break; }
            if (expand(currentPair, finalHash)) { finalCost = currentPair.getCost(); break; }
        } while (!fringe.isEmpty());
        return finalCost;
    }

    private int g(Node pair){ return pair.getCost(); }
    private float h(Node pair){ return heuristic.apply(pair); }

    private boolean expand(Node pair, String finalHash) {
        Node expandedNode = new Node(pair);
        expandedNode.setFringe(getFringeString(this.fringe));

        this.fringe.remove(pair);
        this.expanded.add(expandedNode);
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
        float totalCost;
        float cost = g(currentPair) + h(currentPair);

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

    private String getFringeString(ArrayList<Node> fringe){
        StringBuilder returnString = new StringBuilder();
        Node currentNode;
        returnString.append("*************** Begin Fringe List *************\n");
        for (int i = 0; i < fringe.size(); i++){
            currentNode = fringe.get(i);
            returnString.append("Fringe node: ");
            returnString.append(i + 1);
            returnString.append(";\tstate: ");
            returnString.append(currentNode.getBoardString());
            returnString.append(";\tHeuristic: ");
            returnString.append(h(currentNode));
            returnString.append("\n");
        }
        returnString.append("**************** End Fringe List **************");

        return returnString.toString();
    }
}
