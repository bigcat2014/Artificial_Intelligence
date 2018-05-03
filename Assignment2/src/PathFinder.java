/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
import java.util.ArrayList;

public class PathFinder {
    private ArrayList<KeyValuePair> expanded;
    private ArrayList<KeyValuePair> fringe;
    private int currentCost;

    private Heuristic heuristic;

    public PathFinder(Heuristic h){
        this.heuristic = h;
        this.currentCost = 0;
        this.expanded = new ArrayList<KeyValuePair>();
        this.fringe = new ArrayList<KeyValuePair>();
    }


    public int FindPath(Board currentBoard){
        KeyValuePair currentPair;


        this.fringe.add(new KeyValuePair(currentBoard, 0));
        do {
            if ((currentPair = getLowestCostPair()) == null) { return -1; }
            if (expand(currentPair)) { return currentPair.getCost(); }
        } while (!fringe.isEmpty());
        return -1;
    }

    private int g(KeyValuePair pair){ return pair.getCost(); }
    private int h(KeyValuePair pair){ return heuristic.h(pair); }

    private boolean expand(KeyValuePair pair) {
        Board currentBoard = pair.getBoard();
        int cost = pair.getCost();

        if (currentBoard.equals(currentBoard.getFinalState())) { return true; }
        this.fringe.remove(currentBoard);
        this.expanded.add(new KeyValuePair(currentBoard, cost));
        getChildren(currentBoard, cost);
        return false;
    }
    private void getChildren(Board currentBoard, int currentCost){
        Board childBoard;
        for (int i = 0; i < currentBoard.getSize(); i++){
            childBoard = new Board(currentBoard);
            childBoard.MoveForward(i);
            childBoard.Rotate();
            this.fringe.add(new KeyValuePair(childBoard, currentCost + 1));
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
