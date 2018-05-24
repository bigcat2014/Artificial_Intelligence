import java.util.ArrayList;

public class Player {
    private String name;
    protected ChessPiece[][] board;
    protected ArrayList<ChessPiece> capturedPieces;
    protected ChessPiece.Team winner;
    protected ArrayList<Move> movesList;
//    protected Move currentMove;

    protected final int FINAL_DEPTH = 5;
    protected int depth = 0;


    public Player(String name) {
        this.name = name;
    }

    boolean isGameOver() {
        boolean gameOver = false;
        for (ChessPiece piece : this.capturedPieces) {
            if (piece instanceof Knight) {
                this.winner = piece.getTeam() == ChessPiece.Team.WHITE ? ChessPiece.Team.BLACK : ChessPiece.Team.WHITE;
                return true;
            }
        }
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            if (this.board[i][Game.BOARD_SIZE - 1] instanceof Pawn) {
                gameOver = this.board[i][Game.BOARD_SIZE - 1].getTeam() == ChessPiece.Team.BLACK;
                if (gameOver) {
                    winner = this.board[i][Game.BOARD_SIZE - 1].getTeam();
                    break;
                }
            }
        }
        if (!gameOver) {
            for (int i = 0; i < Game.BOARD_SIZE; i++) {
                if (this.board[i][0] instanceof Pawn) {
                    gameOver = this.board[i][0].getTeam() == ChessPiece.Team.WHITE;
                    if (gameOver) {
                        winner = this.board[i][0].getTeam();
                        break;
                    }
                }
            }
        }
        return gameOver;
    }

    private int Utility(ChessPiece[][] state) {
        return 0;
    }

    private ArrayList<ChessPiece[][]> Successors(ChessPiece[][] state) {
        ArrayList<ChessPiece[][]> successors = new ArrayList<ChessPiece[][]>();
        successors.add(state);
        return successors;
    }

    protected int Max(ChessPiece[][] state, int alpha, int beta) {
//         TODO: Implement Max of min-max search
        this.depth++;
        if (isGameOver()) {
            return Utility(state);
        }
        int v = Integer.MIN_VALUE;
        ArrayList<ChessPiece[][]> successors = Successors(state);
        for (ChessPiece[][] successor : successors) {
            v = Math.max(v, Min(successor, alpha, beta));
            if (v >= beta) {
                break;
            }
            alpha = Math.max(v, alpha);
        }
        return v;
    }

    protected int Min(ChessPiece[][] state, int alpha, int beta) {
//         TODO: Implement Max of min-max search
        this.depth++;
        if (isGameOver()) {
            return Utility(state);
        }
        int v = Integer.MAX_VALUE;
        ArrayList<ChessPiece[][]> successors = Successors(state);
        for (ChessPiece[][] successor : successors) {
            v = Math.min(v, Max(successor, alpha, beta));
            if (v < alpha) {
                break;
            }
            beta = Math.min(v, beta);
        }
        return v;
    }

//    public void update(Move currentMove) {
//        this.currentMove = currentMove;
//    }

    void update(Move currentMove) {
        int x1 = currentMove.getX1() - 1;
        int y1 = currentMove.getY1() - 1;

        MovePiece(currentMove);
        if (this.board[x1][y1] != null) {
            this.capturedPieces.add(this.board[x1][y1]);
            this.board[x1][y1] = null;
        }
    }

    private void MovePiece(Move move) {
        this.movesList.add(move);
        int x1 = move.getX1() - 1;
        int y1 = move.getY1() - 1;
        int x2 = move.getX2() - 1;
        int y2 = move.getY2() - 1;
        ChessPiece temp = this.board[x1][y1];
        this.board[x1][y1] = this.board[x2][y2];
        this.board[x2][y2] = temp;
    }
}
