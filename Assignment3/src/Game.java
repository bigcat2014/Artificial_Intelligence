public class Game {
    public static final int BOARD_SIZE = 8;
    private ChessPiece board[][]=new ChessPiece[BOARD_SIZE][BOARD_SIZE];

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

    private boolean invalidMoveDirection(int x1, int y1, int x2, int y2) {
        if (board[y1][x1] instanceof Pawn) {
            if ((y1 == y2) && (x1 != x2)) { //Moves sideways
                return true;
            }
            if (board[y1][x1].getTeam()==ChessPiece.Team.WHITE) { //White team
                if (y2 - y1 > 0) { //Moves Backwards
                    return true;
                }
            } else if (board[y1][x1].getTeam()==ChessPiece.Team.BLACK) { //Black team
                if (y1 - y2 > 0) { //Moves Backwards
                    return true;
                }
            }

        }
        else if (board[y1][x1] instanceof  Knight){
            if((Math.abs(x2-x1)==1) && (Math.abs(y2-y1)==2)){
                return false;
            }
            else if((Math.abs(x2-x1)==2) && (Math.abs(y2-y1)==1)){
                return false;
            }
            else if((Math.abs(y2-y1)==1) && (Math.abs(x2-x1)==2)) {
                return false;
            }
            else if((Math.abs(y2-y1)==2) && (Math.abs(x2-x1)==1)) {
                return false;
            }

        }
            return  true;
    }

    private boolean invalidForwardMove(int x1, int x2, int y1, int y2){
        if (board[y1][x1] instanceof Pawn) {

        }
        return false;
    }

    private boolean invalidDiagonal(int x1, int y1, int x2, int y2) {
        return false;
    }

    private boolean pawnJumpsPlayer(int x1, int y1, int x2, int y2) {
        return false;
    }

    private boolean pawnIllegalCapture(int x1, int y1, int x2, int y2) {
        return false;
    }

    private boolean landedOnFriendly(int x1, int y1, int x2, int y2) {
        return false;
    }

    private boolean isPieceExistent(int x1, int y1) {
        if(board[x1][y1]==null) {
            return false;
        }
        return true;
    }

    private void MovePiece(int x1, int y1, int x2, int y2){
        ChessPiece temp = board[y1][x1];
        board[y1][x1] = board[y2][x2];
        board[y2][x2] = temp;
    }
}
