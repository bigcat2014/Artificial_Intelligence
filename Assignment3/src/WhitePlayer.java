import java.util.ArrayList;

public class WhitePlayer extends Player{
    public WhitePlayer(String name){
        super(name);
        for (int x = 0; Game.BOARD_SIZE > x; x++) {
            this.board[x][Game.BOARD_SIZE - 2] = new Pawn(ChessPiece.Team.WHITE);
        }
        this.board[1][Game.BOARD_SIZE - 1] = new Knight(ChessPiece.Team.WHITE);

        for (int x = 0; x < Game.BOARD_SIZE; x++) {
            this.board[x][1] = new Pawn(ChessPiece.Team.BLACK);
        }
        this.board[Game.BOARD_SIZE - 2][0] = new Knight(ChessPiece.Team.BLACK);
    }

    protected int Max(ChessPiece[][] state, int alpha, int beta) {
        if (isGameOver() || this.depth++ > MAX_DEPTH) {
            return Utility(state);
        }
        int v = Integer.MIN_VALUE;
        ArrayList<Move> successors = Successors(ChessPiece.Team.WHITE);
        if (successors.size() == 0) {
            return Utility(state);
        }
        for (Move successor : successors) {
            ChessPiece[][] tempBoard = newBoard(state);
            MovePiece(tempBoard, successor);
            v = Math.max(v, Min(tempBoard, alpha, beta));
            if (v >= beta) {
                break;
            }
            if (v > alpha) {
                alpha = v;
            }
        }
        return v;
    }

    protected int Min(ChessPiece[][] state, int alpha, int beta) {
        if (isGameOver() || this.depth++ > MAX_DEPTH) {
            return -Utility(state);
        }
        int v = Integer.MAX_VALUE;
        ArrayList<Move> successors = Successors(ChessPiece.Team.BLACK);
        if (successors.size() == 0) {
            return -Utility(state);
        }
        for (Move successor : successors) {
            ChessPiece[][] tempBoard = newBoard(state);
            MovePiece(tempBoard, successor);
            v = Math.min(v, Max(tempBoard, alpha, beta));
            if (v <= alpha) {
                break;
            }
            if (v < beta) {
                beta = v;
            }
        }
        return v;
    }

    public Move getMove() {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (isGameOver() || this.depth++ > MAX_DEPTH) {
            return INVALID_MOVE;
        }
        int v = Integer.MIN_VALUE;
        ArrayList<Move> successors = Successors(ChessPiece.Team.WHITE);
        if (successors.size() == 0) {
            return INVALID_MOVE;
        }
        Move best = successors.get(0);
        for (Move successor : successors) {
            ChessPiece[][] tempBoard = newBoard(this.board);
            MovePiece(tempBoard, successor);
            v = Math.max(v, Min(tempBoard, alpha, beta));
            if (v >= beta) {
                break;
            }
            if (v > alpha) {
                alpha = v;
                best = successor;
            }
        }
        return best;
//        Max(this.board, Integer.MIN_VALUE, Integer.MAX_VALUE);
//        return this.bestMove;
    }
}
