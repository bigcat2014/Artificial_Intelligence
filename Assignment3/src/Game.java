public class Game {
    public static final int BOARD_SIZE = 8;
    private ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

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
      if(board[x1][y1] instanceof Pawn){
            if(( y1 == y2 ) && (x1 != x2 )){ //Moves sideways
                return true;
            }

        }
        return false;
    }

    private boolean invalidForwardMove(int x1, int x2, int y1, int y2) {
        //if knight return false
        // pawn checks team and the coordinate is forward to . y2-y1<1
        return false;
    }
    private boolean invalidDiagonal(int x1, int y1, int x2, int y2){
        return false;
    }

    private boolean pawnMovedBackwards(int x1, int y1, int x2, int y2){
        return false;
    }

    private boolean pawnJumpsPlayer(int x1, int y1, int x2, int y2){
        return false;
    }

    private boolean pawnIllegalCapture(int x1, int y1, int x2, int y2){
        return false;
    }

    private boolean landedOnFriendly(int x1, int y1, int x2, int y2){
        return false;
    }

    private boolean isPieceExistent(int x1, int x2, int y1, int y2){
        return false;
    }
}
