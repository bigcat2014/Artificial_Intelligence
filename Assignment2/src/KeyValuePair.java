/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
public class KeyValuePair {
    private Board currentBoard;
    private int cost;

    public KeyValuePair(Board currentBoard, int cost){
        this.currentBoard = currentBoard;
        this.cost = cost;
    }

    public Board getBoard(){ return this.currentBoard; }
    public int getCost(){ return this.cost; }

    @Override
    public boolean equals(Object o){
        if(o == this) { return true; }
        if(!(o instanceof KeyValuePair)) { return false; }

        KeyValuePair pair = (KeyValuePair) o;

        return this.equals(pair);
    }
    public boolean equals(KeyValuePair pair){
        return getBoard().equals(pair.getBoard()); // && getCost() == pair.getCost();
    }
    @Override
    public int hashCode(){
        int result = 17;

        result = 31 * result + currentBoard.hashCode();
        //result = 31 * result + cost;
        return result;
    }

    public String toString(){ return "Board: " + this.currentBoard.toString() + "\nCost: " + this.cost; }
}
