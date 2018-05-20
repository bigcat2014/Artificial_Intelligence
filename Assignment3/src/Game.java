public class Game {
    public static final int BOARD_SIZE = 8;
    private ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

    public Game(){
        for (int y = 0; y < BOARD_SIZE; y++){
            board[BOARD_SIZE - 2][y] = new Pawn(ChessPiece.Team.WHITE);
        }
        board[BOARD_SIZE - 1][1] = new Knight(ChessPiece.Team.WHITE);

        for (int y = 0; y < BOARD_SIZE; y++){
            board[1][y] = new Pawn(ChessPiece.Team.BLACK);
        }
        board[0][BOARD_SIZE - 2] = new Knight(ChessPiece.Team.BLACK);
    }

    public void printBoard() {
        StringBuilder boardString = new StringBuilder();
        for (int x = BOARD_SIZE-1; x >= 0; x--){
            for (int y = BOARD_SIZE-1; y >= 0; y--){
                if (board[x][y] == null) {
                    boardString.append(" - ");
                } else {
                    boardString.append(board[x][y].toString());
                    boardString.append(board[x][y].getTeam().toString());
                    boardString.append(" ");
                }
                boardString.append(" ");
            }
            boardString.append("\n");
        }

        System.out.println(boardString.toString());
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
            if (board[y1][x1].getTeam().toString().equalsIgnoreCase("W")) { //White team
                if (y1 - y2 > 0) { //Moves Backwards
                    return true;
                }
            } else if (board[y1][x1].getTeam().toString().equalsIgnoreCase("B")) { //Black team
                if (y2 - y1 > 0) { //Moves Backwards
                    return true;
                }
            }

        }
        else if (board[y1][x1] instanceof  Knight){
            if((Math.abs(x2-x1)==1) && (Math.abs(y2-y1)!=2)){
                return true;
            }
            else if((Math.abs(x2-x1)==2) && (Math.abs(y2-y1)!=1)){
                return true;
            }

        }
        return false;
    }

    private boolean invalidForwardMove(int x1, int x2, int y1, int y2){
        //if knight return false
        // pawn checks team and the coordinate is forward to . y2-y1<1
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
