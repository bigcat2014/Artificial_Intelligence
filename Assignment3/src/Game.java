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

    //TODO error checking for moves
    /*
    Pawn cannot move backwards
    Pawn cannot move diagonal unless its capturing a pawn/Knight
    Pawn cant move two spaces after initial move
    Pawn cannot move over another player
    Pawn cannot capture moving forward
    Pawn cannot land on friendly piece
    Pawn cannomt move outside move set
     */
    private boolean inBounds(int x, int y) {
        return (x > -1 && x < BOARD_SIZE && y > -1 && y < BOARD_SIZE);

    }
}
