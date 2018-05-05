import java.util.ArrayList;
import java.util.Stack;

/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
public class Node {
    private final int SWAP_NUM = 4;
    private State board;
    private int position;
    private int cost;
    private String fringe;

    public Node(ArrayList<Integer> currentBoard, int cost){
        this.board = new State(currentBoard);
        this.position = 0;
        this.cost = cost;
        this.fringe = "";
    }
    public Node(Node pair, int cost){
        this.board = pair.getState();
        this.position = pair.getPosition();
        this.cost = cost;
        this.fringe = pair.getFringe();
    }
    public Node(Node pair){
        this.board = pair.getState();
        this.position = pair.getPosition();
        this.cost = pair.getCost();
        this.fringe = pair.getFringe();
    }

    public int get() { return this.board.get(this.position); }
    public ArrayList<Integer> getBoard(){ return this.board.getBoard(); }
    public State getState() { return (State) this.board.clone(); }
    public int getSize() { return this.board.getSize(); }
    public int getPosition() { return this.position; }
    public void setPosition(int position) { this.position = position; }
    public int getCost(){ return this.cost; }
    public void setCost(int cost) { this.cost = cost; }
    public String getFringe() {return this.fringe; }
    public void setFringe(String fringe) { this.fringe = fringe; }
    public Node getFinalBoard(){
        return this.board.getFinalBoard();
    }

    public void Rotate(){ RotatePieces(this.position); }
    private void RotatePieces(int startIndex){
        Stack<Integer> tiles = new Stack<Integer>();
        int index = startIndex;
        int swapNum = SWAP_NUM > this.board.getSize() ? this.board.getSize() : SWAP_NUM;

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
    public int Next(int startIndex){ return (startIndex + 1) % this.board.getSize(); }

    @Override
    public boolean equals(Object o){
        if(o == this) { return true; }
        if(!(o instanceof Node)) { return false; }

        Node pair = (Node) o;
        return this.equals(pair);
    }
    public boolean equals(Node pair){ return hash(this).equals(hash(pair)); }
    public static String hash(Node pair){
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
    public String toString(){ return getBoardString() + "\tCost: " + this.cost; }
    public String getBoardString(){
        StringBuilder str = new StringBuilder();
        int index = this.position;

        for (int unused : this.board.getBoard()) {
            str.append(this.board.get(index));
            str.append(" ");
            index = Next(index);
        }
        return str.toString();
    }
}