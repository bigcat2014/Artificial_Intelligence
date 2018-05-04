import java.util.ArrayList;
import java.util.Stack;

/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
public class KeyValuePair {
    private final int SWAP_NUM = 4;
    private ArrayList<Integer> board;
    private int position;
    private int cost;

    public KeyValuePair(ArrayList<Integer> currentBoard, int cost){
        this.board = new ArrayList<Integer>(currentBoard);
        this.cost = cost;
    }
    public KeyValuePair(KeyValuePair pair, int cost){
        this.board = new ArrayList<Integer>(pair.getBoard());
        this.position = pair.getPosition();
        this.cost = cost;
    }
    public KeyValuePair(KeyValuePair pair){
        this.board = new ArrayList<Integer>(pair.getBoard());
        this.position = pair.getPosition();
        this.cost = pair.getCost();
    }

    public int get() { return this.board.get(this.position); }
    public ArrayList<Integer> getBoard(){ return new ArrayList<Integer>(this.board); }
    public int getSize() { return this.board.size(); }
    public int getPosition() { return this.position; }
    public int getCost(){ return this.cost; }
    public void setCost(int cost) { this.cost = cost; }
    public KeyValuePair getFinalBoard(){
        ArrayList<Integer> finalBoard = new KeyValuePair(this).getBoard();
        Sort(finalBoard);
        return new KeyValuePair(finalBoard, 0);
    }

    private void Sort(ArrayList<Integer> board){ Sort(board, 0, board.size() - 1);}
    private void Sort(ArrayList<Integer> board, int low, int high){
        int i = low;// set i as the low index of the array
        int j = high;// set j as the high index of the array
        // calculate pivot number, the middle
        int pivot = board.get(low+(high-low)/2);
        // Divide into two sortedArrays
        while (i <= j) {
            // Find a number from the left that is greater than the pivot
            while (board.get(i) < pivot) {
                i++;
            }
            // find a number from the right that is less than the pivot
            while (board.get(j) > pivot) {
                j--;
            }
            // swap the numbers found
            if (i <= j) {
                swap(board, i, j);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call sorting method recursively
        if (low < j)
            Sort(board, low, j);
        if (i < high)
            Sort(board, i, high);
    }
    private void swap(ArrayList<Integer> board, int i, int j) {
        int temp = board.get(i);
        board.set(i, board.get(j));
        board.set(j, temp);
    }

    public void Rotate(){ RotatePieces(this.position); }
    private void RotatePieces(int startIndex){
        Stack<Integer> tiles = new Stack<Integer>();
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

    public void MoveForward(int numSpaces) { for (int i = 0; i < numSpaces; i++) { this.position = Next(this.position); } }
    public int Next(int startIndex){ return (startIndex + 1) % this.board.size(); }

    @Override
    public boolean equals(Object o){
        if(o == this) { return true; }
        if(!(o instanceof KeyValuePair)) { return false; }

        KeyValuePair pair = (KeyValuePair) o;
        return this.equals(pair);
    }
    public boolean equals(KeyValuePair pair){ return hash(this).equals(hash(pair)); }
    public static String hash(KeyValuePair pair){
        StringBuilder hash = new StringBuilder();
        ArrayList<Integer> list = pair.getBoard();
        int onePosition = -1;
        int index = -1;

        while(index < list.size()){
            index++;
            if (onePosition == -1){
                if (list.get(index) == 1){
                    onePosition = index;
                    index = 0;
                }
                continue;
            }
            hash.append(String.format("%02d", list.get(onePosition)));
            onePosition = pair.Next(onePosition);
        }
        return hash.toString();
    }

    @Override
    public String toString(){ return "Board: " + getBoardString() + "\nCost: " + this.cost; }
    private String getBoardString(){
        StringBuilder str = new StringBuilder();
        int index = this.position;

        for (int unused : this.board) {
            str.append(this.board.get(index));
            str.append(" ");
            index = Next(index);
        }
        return str.toString();
    }

    public void PrintBoard(){ System.out.println(this.getBoardString()); }
}