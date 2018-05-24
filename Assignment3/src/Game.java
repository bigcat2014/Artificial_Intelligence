import java.util.ArrayList;

class Game {
    static final int BOARD_SIZE = 8;
    private ChessPiece[][] board;
    private ArrayList<ChessPiece> capturedPieces;

    Game() {
        this.board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
        this.capturedPieces = new ArrayList<ChessPiece>();

        for (int x = 0; x < BOARD_SIZE; x++) {
            board[x][BOARD_SIZE - 2] = new Pawn(ChessPiece.Team.WHITE);
        }
        board[1][BOARD_SIZE - 1] = new Knight(ChessPiece.Team.WHITE);

        for (int x = 0; x < BOARD_SIZE; x++) {
            board[x][1] = new Pawn(ChessPiece.Team.BLACK);
        }
        board[BOARD_SIZE - 2][0] = new Knight(ChessPiece.Team.BLACK);
    }

    void update(Move currentMove, String turn) {
        int x1 = currentMove.getX1() - 1;
        int y1 = currentMove.getY1() - 1;

        if (!isIllegalMove(currentMove)) {
            if (!board[x1][y1].getTeam().toString().equalsIgnoreCase(turn.substring(0, 1))) {
                return;
            }
            MovePiece(currentMove);
            if (board[x1][y1] != null) {
                capturedPieces.add(board[x1][y1]);
                board[x1][y1] = null;
            }
        }
    }

    boolean isIllegalMove(Move currentMove) {
        int x1 = currentMove.getX1() - 1;
        int y1 = currentMove.getY1() - 1;
        int x2 = currentMove.getX2() - 1;
        int y2 = currentMove.getY2() - 1;

        return !inBounds(x1, y1) ||
                !isPieceExistent(x1, y1) ||
                invalidMoveDirection(x1, y1, x2, y2) ||
                invalidForwardMove(x1, y1, x2, y2) ||
                invalidDiagonal(x1, y1, x2, y2) ||
                pawnJumpsPlayer(x1, y1, x2, y2) ||
                pawnIllegalCapture(x1, y1, x2, y2) ||
                landedOnFriendly(x1, y1, x2, y2);
    }

    boolean isGameOver() {
        return false;
    }

    void printBoard() {
        StringBuilder boardString = new StringBuilder();
        for (int y = BOARD_SIZE - 1; y >= 0; y--) {
            for (int x = 0; x < BOARD_SIZE; x++) {
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

    private void MovePiece(Move move) {
        int x1 = move.getX1() - 1;
        int y1 = move.getY1() - 1;
        int x2 = move.getX2() - 1;
        int y2 = move.getY2() - 1;
        ChessPiece temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }

    private boolean inBounds(int x, int y) {
        return (x > -1 && x < BOARD_SIZE && y > -1 && y < BOARD_SIZE);
    }
    private boolean invalidMoveDirection(int x1, int y1, int x2, int y2) {
        if (board[x1][y1] instanceof Pawn) {
            return invalidPawnMove(x1, y1, x2, y2);
        } else if (board[y1][x1] instanceof Knight) {
            return invalidKnightMove(x1, y1, x2, y2);
        }
        return true;
    }

    private boolean invalidPawnMove(int x1, int y1, int x2, int y2) {
        if (!(board[x1][y1] instanceof Pawn)) {
            return true;
        }
        PawnMove currMove = PawnMove.FORWARD;
        if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
            for (int i = 0; i < 3; i++) {
                OrderedPair move = currMove.getMove();
                if (y2 - y1 - move.getY() == 0 &&
                        x2 - x1 - move.getX() == 0) {
                    return false;
                }
                currMove = currMove.next();
            }
        } else if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
            for (int i = 0; i < 3; i++) {
                OrderedPair move = currMove.getMove();
                if (y2 - y1 + move.getY() == 0 &&
                        x2 - x1 + move.getX() == 0) {
                    return false;
                }
                currMove = currMove.next();
            }
        }
        return true;
    }

    private boolean invalidKnightMove(int x1, int y1, int x2, int y2) {
        if (!(board[x1][y1] instanceof Knight)) {
            return true;
        }
        KnightMove currMove = KnightMove.LEFT_UP;

        if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
            for (int i = 0; i < 7; i++) {
                OrderedPair move = currMove.getMove();
                if (y2 - y1 - move.getY() == 0 &&
                        x2 - x1 - move.getX() == 0) {
                    return false;
                }
                currMove = currMove.next();
            }
        } else if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
            for (int i = 0; i < 7; i++) {
                OrderedPair move = currMove.getMove();
                if (y2 - y1 + move.getY() == 0 &&
                        x2 - x1 + move.getX() == 0) {
                    return false;
                }
                currMove = currMove.next();
            }
        }
        return true;
    }
    private boolean invalidForwardMove(int x1, int y1, int x2, int y2) {
        if (board[x1][y1] instanceof Pawn) {
            if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
                if ((x2 - x1 - PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    return board[x1][y1].getIsmoved();
                }
            }
            if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
                if ((x2 - x1 + PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    return board[x1][y1].getIsmoved();
                }
            }
        }
        return false;
    }
    private boolean invalidDiagonal(int x1, int y1, int x2, int y2) {
        if (board[x1][y1] instanceof Pawn) {
            if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
                if ((x2 - x1 - PawnMove.CAPTURE_LEFT.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.CAPTURE_LEFT.getMove().getY() == 0)) {
                    return isPieceExistent(x2, y2);
                }
                if ((x2 - x1 - PawnMove.CAPTURE_RIGHT.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.CAPTURE_RIGHT.getMove().getY() == 0)) {
                    return isPieceExistent(x2, y2);
                }
            }
            if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
                if ((x2 - x1 + PawnMove.CAPTURE_LEFT.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.CAPTURE_LEFT.getMove().getY() == 0)) {
                    return isPieceExistent(x2, y2);
                }
                if ((x2 - x1 + PawnMove.CAPTURE_RIGHT.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.CAPTURE_RIGHT.getMove().getY() == 0)) {
                    return isPieceExistent(x2, y2);
                }
            }
        }
        return false;
    }
    private boolean pawnJumpsPlayer(int x1, int y1, int x2, int y2) {
        if (board[x1][y1] instanceof Pawn) {
            if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
                if ((x2 - x1 - PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    OrderedPair move = PawnMove.FORWARD.getMove();
                    return isPieceExistent(x1 + move.getX(), y1 + move.getY());
                }
            }
            if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
                if ((x2 - x1 + PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    OrderedPair move = PawnMove.FORWARD.getMove();
                    return isPieceExistent(x1 - move.getX(), y1 - move.getY());
                }
            }
        }
        return false;
    }
    private boolean pawnIllegalCapture(int x1, int y1, int x2, int y2) {
        if (board[x1][y1] instanceof Pawn) {
            if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
                if ((x2 - x1 - PawnMove.FORWARD.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.FORWARD.getMove().getY() == 0)) {
                    return isPieceExistent(x2, y2);
                }
                if ((x2 - x1 - PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    return isPieceExistent(x2, y2);
                }
            }
            if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
                if ((x2 - x1 + PawnMove.FORWARD.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.FORWARD.getMove().getY() == 0)) {
                    return isPieceExistent(x2, y2);
                }
                if ((x2 - x1 + PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    return isPieceExistent(x2, y2);
                }
            }
        }
        return false;
    }
    private boolean landedOnFriendly(int x1, int y1, int x2, int y2) {
        if(isPieceExistent(x2,y2)){
            return board[x1][y1].getTeam() == board[x2][y2].getTeam();
        }
        return false;
    }
    private boolean isPieceExistent(int x1, int y1) {
        return board[x1][y1] != null;
    }
}
