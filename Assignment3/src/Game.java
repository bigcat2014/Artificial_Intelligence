public class Game {
    public static final int BOARD_SIZE = 8;
    private ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

    public Game() {
        for (int x = 0; x < BOARD_SIZE; x++) {
            board[BOARD_SIZE - 2][x] = new Pawn(ChessPiece.Team.WHITE);
        }
        board[BOARD_SIZE - 1][1] = new Knight(ChessPiece.Team.WHITE);

        for (int x = 0; x < BOARD_SIZE; x++) {
            board[1][x] = new Pawn(ChessPiece.Team.BLACK);
        }
        board[0][BOARD_SIZE - 2] = new Knight(ChessPiece.Team.BLACK);
    }

    public void printBoard() {
        StringBuilder boardString = new StringBuilder();
            for (int y = BOARD_SIZE - 1; y >= 0; y--) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    if (board[y][x] == null) {
                    boardString.append(" - ");
                } else {
                        boardString.append(board[y][x].toString());
                        boardString.append(board[y][x].getTeam().toString());
                    boardString.append(" ");
                }
                boardString.append(" ");
            }
            boardString.append("\n");
        }

        System.out.println(boardString.toString());
    }

    public boolean isIllegalMove(Move currentMove) {
        int x1 = currentMove.getX1();
        int y1 = currentMove.getY1();
        int x2 = currentMove.getX2();
        int y2 = currentMove.getY2();

        return !inBounds(x1, y1) &&
                invalidMoveDirection(x1, y1, x2, y2) &&
                invalidForwardMove(x1, y1, x2, y2) &&
                invalidDiagonal(x1, y1, x2, y2) &&
                pawnJumpsPlayer(x1, y1, x2, y2) &&
                pawnIllegalCapture(x1, y1, x2, y2) &&
                landedOnFriendly(x1, y1, x2, y2) &&
                !isPieceExistent(x1, y1);
    }

    boolean isGameOver() {
        return false;
    }

    void update(Move currentMove, String turn) {
        if (!isIllegalMove(currentMove)) {
            MovePiece(currentMove);
            board[currentMove.getY2()][currentMove.getX2()] = null;
        }
    }

    private boolean inBounds(int x, int y) {
        return (x > -1 && x < BOARD_SIZE && y > -1 && y < BOARD_SIZE);
    }

    private boolean invalidMoveDirection(int x1, int y1, int x2, int y2) {
        if (board[y1][x1] instanceof Pawn) {
            if (y2 - y1 == 1 && x1 == x2) {
                return false;
            } else if (y2 - y1 == 1 && (Math.abs(x1 - x2) == 1)) {
                return false;
            } else if (y2 - y1 == 1 && (Math.abs(x2 - x1) == 1)) {
                return false;
            }
            if (y2 - y1 == 2 && x1 == x2) {
                return false;
            } else if ((y1 == y2) && (x1 != x2)) { //Moves sideways
                return true;
            } else if (board[y1][x1].getTeam() == ChessPiece.Team.WHITE) { //White team
                if (y2 - y1 > 0) { //Moves Backwards
                    return true;
                }
            } else if (board[y1][x1].getTeam() == ChessPiece.Team.BLACK) { //Black team
                if (y1 - y2 > 0) { //Moves Backwards
                    return true;
                }
            }

        } else if (board[y1][x1] instanceof Knight) {

            if ((Math.abs(x2 - x1) == 1) && (Math.abs(y2 - y1) == 2)) {
                return false;
            } else if ((Math.abs(x2 - x1) == 2) && (Math.abs(y2 - y1) == 1)) {
                return false;
            } else if ((Math.abs(y2 - y1) == 1) && (Math.abs(x2 - x1) == 2)) {
                return false;
            } else if ((Math.abs(y2 - y1) == 2) && (Math.abs(x2 - x1) == 1)) {
                return false;
            }

        }
        return true;
    }

    private boolean invalidForwardMove(int x1, int y1, int x2, int y2) {
        if (board[y1][x1] instanceof Pawn) {
            if (board[y1][x1].getIsmoved()) {
                if ((y2 - y1) == 2 && x1 == x2) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean invalidDiagonal(int x1, int y1, int x2, int y2) {
        if (board[y1][x1] instanceof Pawn) {
            if ((Math.abs(x2 - x1) == Math.abs(PawnMove.CAPTURE_LEFT.getMove().getX())) && (Math.abs(y2 - y1) == Math.abs(PawnMove.CAPTURE_LEFT.getMove().getY()))) {
                return isPieceExistent(x2, y2);
            }
        }
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
        return board[x1][y1] != null;
    }

    private void MovePiece(Move move) {
        int x1 = move.getX1();
        int y1 = move.getY1();
        int x2 = move.getX2();
        int y2 = move.getX2();
        ChessPiece temp = board[y1][x1];
        board[y1][x1] = board[y2][x2];
        board[y2][x2] = temp;
    }
}
