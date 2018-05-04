/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
import java.util.ArrayList;
import java.util.function.Function;

public class PathFinder {
    private final boolean DEBUG;

    private ArrayList<KeyValuePair> expanded;
    private ArrayList<KeyValuePair> fringe;

    private Function<KeyValuePair, Integer> heuristic;

    public PathFinder(Function<KeyValuePair, Integer> h, boolean debug){
        this.DEBUG = debug;
        this.heuristic = h;
        this.expanded = new ArrayList<KeyValuePair>();
        this.fringe = new ArrayList<KeyValuePair>();
    }

    public ArrayList<KeyValuePair> getExpanded(){ return this.expanded; }

    public ArrayList<KeyValuePair> getFringe() { return this.fringe; }

    public int FindPath(KeyValuePair currentBoard){
        KeyValuePair currentPair;
        String finalHash = KeyValuePair.hash(currentBoard.getFinalBoard());

        this.fringe.add(new KeyValuePair(currentBoard));
        do {
            if ((currentPair = getLowestCostPair()) == null) { return -1; }
            if (expand(currentPair, finalHash)) { return currentPair.getCost(); }
        } while (!fringe.isEmpty());
        return -1;
    }

    private int g(KeyValuePair pair){ return pair.getCost(); }
    private int h(KeyValuePair pair){ return heuristic.apply(pair); }

    private boolean expand(KeyValuePair pair, String finalHash) {
        this.fringe.remove(pair);
        this.expanded.add(new KeyValuePair(pair));
        if (KeyValuePair.hash(pair).equals(finalHash)) { return true; }
        getChildren(pair);
        return false;
    }
    private void getChildren(KeyValuePair pair){
        KeyValuePair child;
        int cost = pair.getCost();

        for (int i = 0; i < pair.getSize(); i++){
            child = new KeyValuePair(pair);
            child.MoveForward(i);
            child.Rotate();
            if(!(this.expanded.contains(child) || this.fringe.contains(child))) { this.fringe.add(new KeyValuePair(child, cost + 1)); }
            else{
                if (this.fringe.contains(child)){
                    int nodeIndex = this.fringe.indexOf(child);
                    KeyValuePair node = this.fringe.get(nodeIndex);
                    if (node.getCost() > cost){ node.setCost(cost); }
                }
            }
        }
    }

    private KeyValuePair getLowestCostPair(){
        if (this.fringe.isEmpty()) { return null; }
        KeyValuePair currentPair = this.fringe.get(0);
        KeyValuePair returnPair = currentPair;
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
}
