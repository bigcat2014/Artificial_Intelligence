import java.util.ArrayList;

public class Game {
    public static final int BOARD_SIZE = 8;

    public void printBoard() {
    }

    public boolean isIllegalMove(Move currentMove) {
        return false;
    }

    public boolean isGameOver() {
        return false;
    }

    public void update(Move currentMove, String turn) {

    }

    private boolean inBounds(int x, int y) {
        return (x > -1 && x < BOARD_SIZE && y > -1 && y < BOARD_SIZE);
    }

    private boolean invalidMoveDirection(int x1, int y1, int x2, int y2){
        return false;
    }

    private boolean invalidForwardMove(int x1, int x2, int y1, int y2){
        return false;
    }
}
