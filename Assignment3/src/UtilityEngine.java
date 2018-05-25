import java.util.ArrayList;

public final class UtilityEngine {
    private static final int KNIGHT_VAL = 9;
    private static final int PAWN_VAL = 1;
    private static final int[] weights = {1, 4, 8, 18, 50, 100};

    private UtilityEngine() {
    }

    static int Utility(ChessPiece[][] board, ChessPiece.Team winner, boolean isGameOver) {
        int utility = 0;
        if (isGameOver) {
            return winner == ChessPiece.Team.WHITE ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        utility += weights[0] * numPiecesLeft(board);

        utility -= weights[1] * pawnsBlocked(board, ChessPiece.Team.WHITE);
        utility += weights[1] * pawnsBlocked(board, ChessPiece.Team.BLACK);

        utility -= weights[2] * attackedByOne(board, ChessPiece.Team.WHITE);
        utility += weights[2] * attackedByOne(board, ChessPiece.Team.BLACK);

        utility -= weights[3] * attackedByMore(board, ChessPiece.Team.WHITE);
        utility += weights[3] * attackedByMore(board, ChessPiece.Team.BLACK);

        utility -= weights[4] * knightAttackers(board, ChessPiece.Team.WHITE);
        utility += weights[4] * knightAttackers(board, ChessPiece.Team.BLACK);

        utility -= weights[5] * knightAttacksCount(board, ChessPiece.Team.WHITE);
        utility += weights[5] * knightAttacksCount(board, ChessPiece.Team.BLACK);

        return utility;
    }

    private static int numPiecesLeft(ChessPiece[][] board) {
        int utility = 0;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[x][y] instanceof Pawn) {
                    utility += (board[x][y].getTeam() == ChessPiece.Team.WHITE ? 1 : -1) * PAWN_VAL;
                } else if (board[x][y] instanceof Knight) {
                    utility += (board[x][y].getTeam() == ChessPiece.Team.WHITE ? 1 : -1) * KNIGHT_VAL;
                }
            }
        }
        return utility;
    }

    private static int pawnsBlocked(ChessPiece[][] board, ChessPiece.Team team) {
        int utility = 0;

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[x][y] instanceof Pawn && board[x][y].getTeam() != team) {
                    utility += pawnBlocked(board, x, y, team) ? 1 : 0;
                }
            }
        }
        return utility;
    }

    private static boolean pawnBlocked(ChessPiece[][] board, int x, int y, ChessPiece.Team team) {
        int x2;
        int y2;
        int modifier = team == ChessPiece.Team.WHITE ? -1 : 1;

        OrderedPair pawnMoveVals = PawnMove.FORWARD.getMove();
        x2 = x + (modifier * pawnMoveVals.getX());
        y2 = y + (modifier * pawnMoveVals.getY());
        if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
            return board[x2][y2] != null;
        }
        return true;
    }

    private static int attackedByOne(ChessPiece[][] board, ChessPiece.Team team) {
        int utility = 0;

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[x][y] != null && board[x][y].getTeam() == team) {
                    utility += squareAttackedBy(board, x, y, team) == 1 ? 1 : 0;
                }
            }
        }
        return utility;
    }

    private static int attackedByMore(ChessPiece[][] board, ChessPiece.Team team) {
        int utility = 0;

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[x][y] != null && board[x][y].getTeam() == team) {
                    utility += squareAttackedBy(board, x, y, team) >= 2 ? 1 : 0;
                }
            }
        }
        return utility;
    }

    private static int squareAttackedBy(ChessPiece[][] board, int x, int y, ChessPiece.Team team) {
        int x2;
        int y2;
        int attackedBy = 0;
        int modifier = team == ChessPiece.Team.WHITE ? -1 : 1;

        OrderedPair pawnMoveVals = PawnMove.CAPTURE_RIGHT.getMove();
        x2 = x + (modifier * pawnMoveVals.getX());
        y2 = y + (modifier * pawnMoveVals.getY());
        if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
            if (board[x2][y2] instanceof Pawn && board[x2][y2].getTeam() != team) {
                attackedBy++;
            }
        }

        pawnMoveVals = PawnMove.CAPTURE_LEFT.getMove();
        x2 = x + (modifier * pawnMoveVals.getX());
        y2 = y + (modifier * pawnMoveVals.getY());
        if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
            if (board[x2][y2] instanceof Pawn && board[x2][y2].getTeam() != team) {
                attackedBy++;
            }
        }

        KnightMove currMove = KnightMove.LEFT_UP;
        for (int i = 0; i < KnightMove.getNumMoves(); i++) {
            OrderedPair knightMoveVals = currMove.getMove();
            x2 = x + knightMoveVals.getX();
            y2 = y + knightMoveVals.getY();
            if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
                if (board[x2][y2] instanceof Knight && board[x2][y2].getTeam() != team) {
                    attackedBy++;
                }
            }
            currMove = currMove.next();
        }
        return attackedBy;
    }

    private static int knightAttackers(ChessPiece[][] board, ChessPiece.Team team) {
        int numAttackers = 0;

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[x][y] instanceof Knight && board[x][y].getTeam() == team) {
                    numAttackers = numAttackers(board, x, y, team);
                    break;
                }
            }
        }
        return numAttackers;
    }

    private static int numAttackers(ChessPiece[][] board, int x, int y, ChessPiece.Team team) {
        int x2;
        int y2;
        ArrayList<ChessPiece> attackers = new ArrayList<ChessPiece>();
        int modifier = team == ChessPiece.Team.WHITE ? -1 : 1;

        OrderedPair pawnMoveVals = PawnMove.CAPTURE_RIGHT.getMove();
        x2 = x + (modifier * pawnMoveVals.getX());
        y2 = y + (modifier * pawnMoveVals.getY());
        if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
            if (board[x2][y2] instanceof Pawn && board[x2][y2].getTeam() != team) {
                if (!attackers.contains(board[x2][y2])) {
                    attackers.add(board[x2][y2]);
                }
            }
        }

        pawnMoveVals = PawnMove.CAPTURE_LEFT.getMove();
        x2 = x + (modifier * pawnMoveVals.getX());
        y2 = y + (modifier * pawnMoveVals.getY());
        if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
            if (board[x2][y2] instanceof Pawn && board[x2][y2].getTeam() != team) {
                if (!attackers.contains(board[x2][y2])) {
                    attackers.add(board[x2][y2]);
                }
            }
        }

        KnightMove currMove = KnightMove.LEFT_UP;
        for (int i = 0; i < KnightMove.getNumMoves(); i++) {
            OrderedPair knightMoveVals = currMove.getMove();
            x2 = x + knightMoveVals.getX();
            y2 = y + knightMoveVals.getY();
            if (!MoveValidation.isIllegalMove(board, new Move(x, y, x2, y2), team)) {
                if (board[x2][y2] instanceof Knight && board[x2][y2].getTeam() != team) {
                    if (!attackers.contains(board[x2][y2])) {
                        attackers.add(board[x2][y2]);
                    }
                }
            }
            currMove = currMove.next();
        }

        return attackers.size();
    }

    private static int knightAttacksCount(ChessPiece[][] board, ChessPiece.Team team) {
        int numAttacks = 0;

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[x][y] instanceof Knight && board[x][y].getTeam() == team) {
                    numAttacks = squareAttackedBy(board, x, y, team);
                    break;
                }
            }
        }
        return numAttacks;
    }

}