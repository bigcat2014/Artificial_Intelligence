public final class MoveValidation {
    private MoveValidation() {
    }

    //Checks if Move passes all illegal checks and is Valid
    static boolean isIllegalMove(ChessPiece[][] board, Move currentMove, ChessPiece.Team turn) {
        int x1 = currentMove.getX1() - 1;
        int y1 = currentMove.getY1() - 1;
        int x2 = currentMove.getX2() - 1;
        int y2 = currentMove.getY2() - 1;

        return !inBounds(board, x1, y1, x2, y2) ||
                wrongTeam(board, x1, y1, turn) ||
                !isPieceExistent(board, x1, y1) ||
                invalidMoveDirection(board, x1, y1, x2, y2) ||
                invalidForwardMove(board, x1, y1, x2, y2) ||
                invalidDiagonal(board, x1, y1, x2, y2) ||
                pawnJumpsPlayer(board, x1, y1, x2, y2) ||
                pawnIllegalCapture(board, x1, y1, x2, y2) ||
                landedOnFriendly(board, x1, y1, x2, y2);
    }

    private static boolean wrongTeam(ChessPiece[][] board, int x, int y, ChessPiece.Team turn) {
        if (board[x][y] == null) {
            return true;
        }
        return (board[x][y].getTeam() != turn);
    }

    //Checks if piece remains inbounds after move
    private static boolean inBounds(ChessPiece[][] board, int x1, int y1, int x2, int y2) {
        return (x1 > -1 && x1 < board.length && y1 > -1 && y1 < board[x1].length) &&
                (x2 > -1 && x2 < board.length && y2 > -1 && y2 < board[x2].length);
    }

    //Checks if move is going in the correct direction
    private static boolean invalidMoveDirection(ChessPiece[][] board, int x1, int y1, int x2, int y2) {
        if (board[x1][y1] instanceof Pawn) {
            return invalidPawnMove(board, x1, y1, x2, y2);
        } else if (board[x1][y1] instanceof Knight) {
            return invalidKnightMove(board, x1, y1, x2, y2);
        }
        return true;
    }

    //Checks if pawn is attempting an Illegal move
    private static boolean invalidPawnMove(ChessPiece[][] board, int x1, int y1, int x2, int y2) {
        if (!(board[x1][y1] instanceof Pawn)) {
            return true;
        }
        PawnMove currMove = PawnMove.FORWARD;
        if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
            for (int i = 0; i < PawnMove.getNumMoves(); i++) {
                OrderedPair move = currMove.getMove();
                if (y2 - y1 - move.getY() == 0 &&
                        x2 - x1 - move.getX() == 0) {
                    return false;
                }
                currMove = currMove.next();
            }
        } else if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
            for (int i = 0; i < PawnMove.getNumMoves(); i++) {
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

    //Checks if Knight is attempting an Illegal move
    private static boolean invalidKnightMove(ChessPiece[][] board, int x1, int y1, int x2, int y2) {
        if (!(board[x1][y1] instanceof Knight)) {
            return true;
        }
        KnightMove currMove = KnightMove.LEFT_UP;

        if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
            for (int i = 0; i < KnightMove.getNumMoves(); i++) {
                OrderedPair move = currMove.getMove();
                if (y2 - y1 - move.getY() == 0 &&
                        x2 - x1 - move.getX() == 0) {
                    return false;
                }
                currMove = currMove.next();
            }
        } else if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
            for (int i = 0; i < KnightMove.getNumMoves(); i++) {
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

    //Checks if pawn is attempting an illegal FORWARD move such as jumping two spots after initial move
    private static boolean invalidForwardMove(ChessPiece[][] board, int x1, int y1, int x2, int y2) {
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

    //Checks if pawn is attempting an illegal diagonal move
    private static boolean invalidDiagonal(ChessPiece[][] board, int x1, int y1, int x2, int y2) {
        if (board[x1][y1] instanceof Pawn) {
            if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
                if ((x2 - x1 - PawnMove.CAPTURE_LEFT.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.CAPTURE_LEFT.getMove().getY() == 0)) {
                    return !isPieceExistent(board, x2, y2);
                }
                if ((x2 - x1 - PawnMove.CAPTURE_RIGHT.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.CAPTURE_RIGHT.getMove().getY() == 0)) {
                    return !isPieceExistent(board, x2, y2);
                }
            }
            if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
                if ((x2 - x1 + PawnMove.CAPTURE_LEFT.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.CAPTURE_LEFT.getMove().getY() == 0)) {
                    return !isPieceExistent(board, x2, y2);
                }
                if ((x2 - x1 + PawnMove.CAPTURE_RIGHT.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.CAPTURE_RIGHT.getMove().getY() == 0)) {
                    return !isPieceExistent(board, x2, y2);
                }
            }
        }
        return false;
    }

    //Checks if pawn attempts to jump over a piece to make a move
    private static boolean pawnJumpsPlayer(ChessPiece[][] board, int x1, int y1, int x2, int y2) {
        if (board[x1][y1] instanceof Pawn) {
            if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
                if ((x2 - x1 - PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    OrderedPair move = PawnMove.FORWARD.getMove();
                    return isPieceExistent(board, x1 + move.getX(), y1 + move.getY());
                }
            }
            if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
                if ((x2 - x1 + PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    OrderedPair move = PawnMove.FORWARD.getMove();
                    return isPieceExistent(board, x1 - move.getX(), y1 - move.getY());
                }
            }
        }
        return false;
    }

    //Checks if pawn is attempting to capture a piece that's nonexistent
    private static boolean pawnIllegalCapture(ChessPiece[][] board, int x1, int y1, int x2, int y2) {
        if (board[x1][y1] instanceof Pawn) {
            if (board[x1][y1].getTeam() == ChessPiece.Team.BLACK) {
                if ((x2 - x1 - PawnMove.FORWARD.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.FORWARD.getMove().getY() == 0)) {
                    return isPieceExistent(board, x2, y2);
                }
                if ((x2 - x1 - PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 - PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    return isPieceExistent(board, x2, y2);
                }
            }
            if (board[x1][y1].getTeam() == ChessPiece.Team.WHITE) {
                if ((x2 - x1 + PawnMove.FORWARD.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.FORWARD.getMove().getY() == 0)) {
                    return isPieceExistent(board, x2, y2);
                }
                if ((x2 - x1 + PawnMove.FORWARD_2.getMove().getX() == 0) &&
                        (y2 - y1 + PawnMove.FORWARD_2.getMove().getY() == 0)) {
                    return isPieceExistent(board, x2, y2);
                }
            }
        }
        return false;
    }

    //Checks if there's a friendly piece in the spot another piece is moving too
    private static boolean landedOnFriendly(ChessPiece[][] board, int x1, int y1, int x2, int y2) {
        if (isPieceExistent(board, x2, y2)) {
            return board[x1][y1].getTeam() == board[x2][y2].getTeam();
        }
        return false;
    }

    //Checks if piece exists
    private static boolean isPieceExistent(ChessPiece[][] board, int x1, int y1) {
        return board[x1][y1] != null;
    }
}
