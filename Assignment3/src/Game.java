import java.util.ArrayList;

public class Game {
    private int boardSize;

    public Game(String boardSize){
        try {
            this.boardSize = Integer.parseInt(boardSize);
        } catch (NumberFormatException e){
            this.boardSize = 8;
        }
    }

    public int boardSize() { return this.boardSize; }

    public void printBoard(){

    }

    public boolean isIllegalMove(Move currentMove){
        return false;
    }

    public boolean isGameOver(){
        return false;
    }

    public void update(Move currentMove, String turn){

    }
}
