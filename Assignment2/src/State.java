import java.util.ArrayList;

public class State implements Cloneable{
    ArrayList<Integer> board;

    public State(ArrayList<Integer> board){
        this.board = (ArrayList<Integer>) board.clone();
    }
    public State(State board){
        this.board = board.getBoard();
    }

    public int get(int position) { return this.board.get(position); }
    public void set(int index, int value) { this.board.set(index, value); }
    public ArrayList<Integer> getBoard(){ return (ArrayList<Integer>) this.board.clone(); }
    public int getSize() { return this.board.size(); }
    public Node getFinalBoard(){
        ArrayList<Integer> finalBoard = new State(this).getBoard();
        Sort(finalBoard);
        return new Node(finalBoard, 0);
    }

    public Object clone(){ return new State((ArrayList<Integer>) this.board.clone()); }

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
}
