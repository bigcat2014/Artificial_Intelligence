import java.util.ArrayList;

public final class UtilityEngine {
    private static final int KNIGHT_VAL = 320;
    private static final int PAWN_VAL = 100;
    private static final int[] weights = {1, 4, 15, 18, 50, 50};
    //        Pawn
//        0,  0,  0,  0,  0,  0,  0,  0,
//        50, 50, 50, 50, 50, 50, 50, 50,
//        10, 10, 20, 30, 30, 20, 10, 10,
//        5,  5, 10, 25, 25, 10,  5,  5,
//        0,  0,  0, 20, 20,  0,  0,  0,
//        5, -5,-10,  0,  0,-10, -5,  5,
//        5, 10, 10,-20,-20, 10, 10,  5,
//        0,  0,  0,  0,  0,  0,  0,  0
    private static final int[][] pawnTable =
            {{0, 5, 5, 0, 5, 10, 50, 0},
                    {0, 10, -5, 0, 5, 10, 50, 100},
                    {0, 10, -10, 0, 10, 20, 50, 100},
                    {0, -20, 0, 20, 25, 30, 50, 100},
                    {0, -20, 0, 20, 25, 30, 50, 100},
                    {0, 10, -10, 0, 10, 20, 50, 100},
                    {0, 10, -5, 0, 5, 10, 50, 100},
                    {0, 5, 5, 0, 5, 10, 50, 100}};

    //        Knight
//        -50,-40,-30,-30,-30,-30,-40,-50,
//        -40,-20,  0,  0,  0,  0,-20,-40,
//        -30,  0, 10, 15, 15, 10,  0,-30,
//        -30,  5, 15, 20, 20, 15,  5,-30,
//        -30,  0, 15, 20, 20, 15,  0,-30,
//        -30,  5, 10, 15, 15, 10,  5,-30,
//        -40,-20,  0,  5,  5,  0,-20,-40,
//        -50,-40,-30,-30,-30,-30,-40,-50,
    private static final int[][] knightTable =
            {{-50, -40, -30, -30, -30, -30, -40, -50},
                    {-40, -20, 5, 0, 5, 0, -20, -40},
                    {-30, 0, 10, 15, 15, 10, 0, -30},
                    {-30, 5, 15, 20, 20, 15, 0, -30},
                    {-30, 5, 15, 20, 20, 15, 0, -30},
                    {-30, 0, 10, 15, 15, 10, 0, -30},
                    {-40, -20, 5, 0, 5, 0, -20, -40},
                    {-50, -40, -30, -30, -30, -30, -40, -50}};

    private UtilityEngine() {
    }

    static int Utility(ChessPiece[][] board, ChessPiece.Team winner, boolean isGameOver, ChessPiece.Team team) {
        int utility = 0;
        int teamWeight = team == ChessPiece.Team.WHITE ? 1 : -1;

        if (isGameOver) {
            return winner == ChessPiece.Team.WHITE ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        utility += teamWeight * weights[0] * evalPositions(board, team);

        //utility -= weights[1] * pawnsBlocked(board, ChessPiece.Team.WHITE);
        utility += teamWeight * weights[1] * pawnsBlocked(board, team);

        //utility -= weights[2] * attackedByOne(board, ChessPiece.Team.WHITE);
        utility += teamWeight * weights[2] * attackedByOne(board, team);

        //utility -= weights[3] * attackedByMore(board, ChessPiece.Team.WHITE);
        utility += teamWeight * weights[3] * attackedByMore(board, team);

        //utility -= weights[4] * knightAttackers(board, ChessPiece.Team.WHITE);
//        utility += teamWeight * weights[4] * knightAttackers(board, team);

        //utility -= weights[5] * knightAttacksCount(board, ChessPiece.Team.WHITE);
        utility += teamWeight * weights[5] * knightAttacksCount(board, team);

        return utility;
    }

    private static int evalPositions(ChessPiece[][] board, ChessPiece.Team team) {
        int utility = 0;
        if (team == ChessPiece.Team.BLACK) {
            for (int y = 0; y < board.length; y++) {
                for (int x = 0; x < board[y].length; x++) {
                    if (board[x][y] instanceof Pawn && board[x][y].getTeam() != team) {
                        utility += (board[x][y].getTeam() == team ? pawnTable[x][y] : 0) * PAWN_VAL;
                    } else if (board[x][y] instanceof Knight && board[x][y].getTeam() != team) {
                        utility += (board[x][y].getTeam() == team ? knightTable[x][y] : 0) * KNIGHT_VAL;
                    }
                }
            }
        } else {
            for (int y = 0; y < board.length; y++) {
                for (int x = 0; x < board[y].length; x++) {
                    if (board[x][y] instanceof Pawn && board[x][y].getTeam() != team) {
                        utility += (board[x][y].getTeam() == team ? pawnTable[Game.BOARD_SIZE - x - 1][Game.BOARD_SIZE - y - 1] : 0) * PAWN_VAL;
                    } else if (board[x][y] instanceof Knight && board[x][y].getTeam() != team) {
                        utility += (board[x][y].getTeam() == team ? knightTable[Game.BOARD_SIZE - x - 1][Game.BOARD_SIZE - y - 1] : 0) * KNIGHT_VAL;
                    }
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
        boolean blocked = true;

        OrderedPair pawnMoveVals = PawnMove.FORWARD.getMove();
        x2 = x + (modifier * pawnMoveVals.getX());
        y2 = y + (modifier * pawnMoveVals.getY());
        if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
            if (board[x2][y2] != null) {
                pawnMoveVals = PawnMove.CAPTURE_LEFT.getMove();
                x2 = x + (modifier * pawnMoveVals.getX());
                y2 = y + (modifier * pawnMoveVals.getY());
                if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
                    blocked = board[x2][y2] == null;
                } else {
                    blocked = true;
                }

                pawnMoveVals = PawnMove.CAPTURE_RIGHT.getMove();
                x2 = x + (modifier * pawnMoveVals.getX());
                y2 = y + (modifier * pawnMoveVals.getY());
                if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
                    blocked &= board[x2][y2] == null;
                }
            }
        }

        return blocked;
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