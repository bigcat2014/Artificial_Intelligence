/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
import java.util.ArrayList;
import java.util.Stack;

public class Board{
    private final int SWAP_NUM = 4;
    private ArrayList<Tile> board;
    private ArrayList<Tile> finalState;
    private int currPosition;

    public Board(int size){
        this.board = new ArrayList<Tile>(size);
        for ( int i = 1; i <= size; i++) { this.board.add(new Tile(i)); }

        this.currPosition = 0;
    }
    public Board(ArrayList<Tile> board){
        this.board = board;
        this.currPosition = 0;
    }
    public Board(Board board){
        this.board = new ArrayList<Tile>(board.getList());
        this.currPosition = board.getCurrPosition();
        this.finalState = new ArrayList<Tile>(board.getFinalState().getList());
    }

    public int getSize() { return this.board.size(); }
    public ArrayList<Tile> getList() { return this.board; }
    public int getCurrPosition() { return this.currPosition; }
    public Tile getCurrentTile() { return this.board.get(this.currPosition); }
    public Board getFinalState() {
        if (finalState == null) {
            this.finalState = new ArrayList<Tile>(this.board);
            Sort();
            return new Board(this.finalState);
        } else {
            return new Board(this.finalState);
        }
    }

    public void addTile(Tile t) { this.board.add(t); }

    public void Rotate(){ RotatePieces(this.currPosition); }

    public void MoveForward(int numSpaces){ Move(numSpaces, true); }
    public void MoveBackwards(int numSpaces){ Move(numSpaces, false); }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        int index = this.currPosition;
        for (Tile _t : this.board) {
            str.append(this.board.get(index).getValue());
            str.append(" ");
            index = Next(index);
        }
        return str.toString();
    }
    public void PrintBoard(){
        System.out.println(this.toString());
    }

    @Override
    public boolean equals(Object o){
        if(o == this) { return true; }
        if(o instanceof Board) {
            Board board = (Board) o;
            return this.equals(board);
        } else if (o instanceof KeyValuePair){
            KeyValuePair pair = (KeyValuePair) o;
            return this.equals(pair);
        }
        else { return false; }
    }
    private boolean equals(Board board){
        if (this.board.size() != board.getSize()) { return false; }
        ArrayList<Tile> boardArray = new ArrayList<Tile>(board.getList());

        for (int i = 0; i < this.board.size(); i++){ if(!this.board.get(i).equals(boardArray.get(i))) { return false; } }
        return this.currPosition == board.getCurrPosition();
    }
    private boolean equals(KeyValuePair pair){
        if (this.board.size() != pair.getBoard().getSize()) { return false; }
        ArrayList<Tile> boardArray = new ArrayList<Tile>(pair.getBoard().getList());

        for (int i = 0; i < this.board.size(); i++){ if(!this.board.get(i).equals(boardArray.get(i))) { return false; } }
        return this.currPosition == pair.getBoard().getCurrPosition();
    }
    @Override
    public int hashCode(){
        int result = 17;

        result = 31 * result + board.hashCode();
        result = 31 * result + currPosition;
        return result;
    }


    private void RotatePieces(int startIndex){
        Stack<Tile> tiles = new Stack<Tile>();
        int index = startIndex;
        int swapNum = SWAP_NUM > this.board.size() ? this.board.size() : SWAP_NUM;

        for (int i = 0; i < swapNum; i++) {
            tiles.push((this.board.get(index)));
            index = Next(index);
        }

        index = startIndex;
        for (int i = 0; i < swapNum; i++) {
            this.board.set(index, tiles.pop());
            index = Next(index);
        }
    }

    private int Next(int startIndex){
        // Circular position increment
        return (startIndex + 1) % this.board.size();
    }
    private int Previous(int startIndex){
        // Circular position decrement
        return startIndex - 1 < 0 ? startIndex - 1 + this.board.size() : startIndex - 1;
    }

    private void Move(int numSpaces, boolean forward){
        if (forward) {
            for (int i = 0; i < numSpaces; i++) {
                this.currPosition = Next(this.currPosition);
            }
        } else {
            this.currPosition = Previous(this.currPosition);
        }
    }

    private void Sort(){
        Sort(0, this.board.size() - 1);
    }
    private void Sort(int low, int high){
        int i = low;// set i as the low index of the array
        int j = high;// set j as the high index of the array
        // calculate pivot number, the middle
        int pivot = this.finalState.get(low+(high-low)/2).getValue();
        // Divide into two sortedArrays
        while (i <= j) {
            // Find a number from the left that is greater than the pivot
            while (this.finalState.get(i).getValue() < pivot) {
                i++;
            }
            // find a number from the right that is less than the pivot
            while (this.finalState.get(j).getValue() > pivot) {
                j--;
            }
            // swap the numbers found
            if (i <= j) {
                swap(i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call sorting method recursively
        if (low < j)
            Sort(low, j);
        if (i < high)
            Sort(i, high);
    }
    private void swap(int i, int j) {
        Tile temp = this.finalState.get(i);
        this.finalState.set(i, this.finalState.get(j));
        this.finalState.set(j, temp);
    }
}