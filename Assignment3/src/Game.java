import java.util.ArrayList;

public class Game {
    public static final int BOARD_SIZE = 8;

    public void printBoard() {
        for (int row = 0; row < BOARD_SIZE; row++)
        {
            System.out.println("");
            System.out.println("---------------------------------");

            for (int column = 0; column < BOARD_SIZE; column++)
            {
                System.out.print("- " + " " + " ");
            }
            System.out.print("-");
        }
        System.out.println("");
        System.out.println("---------------------------------");
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
