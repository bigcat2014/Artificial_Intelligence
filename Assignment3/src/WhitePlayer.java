import java.util.ArrayList;
import java.util.Random;

public class WhitePlayer extends Player{
    public WhitePlayer(String name){
        super(name);
    }

    protected int Max(ChessPiece[][] state, int alpha, int beta) {
        if (isGameOver(this.board) || ++this.depth > MAX_DEPTH) {
            return Utility(state, ChessPiece.Team.WHITE);
        }
        int v = Integer.MIN_VALUE;
        ArrayList<Move> successors = Successors(state, ChessPiece.Team.WHITE);
        if (successors.size() == 0) {
            return Utility(state, ChessPiece.Team.WHITE);
        }
        for (Move successor : successors) {
            ChessPiece[][] tempBoard = newBoard(state);
            MovePiece(tempBoard, successor);
            v = Math.max(v, Min(tempBoard, alpha, beta));
            this.depth--;
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
        if (isGameOver(this.board) || ++this.depth > MAX_DEPTH) {
            return Utility(state, ChessPiece.Team.WHITE);
        }
        int v = Integer.MAX_VALUE;
        ArrayList<Move> successors = Successors(state, ChessPiece.Team.BLACK);
        if (successors.size() == 0) {
            return Utility(state, ChessPiece.Team.WHITE);
        }
        for (Move successor : successors) {
            ChessPiece[][] tempBoard = newBoard(state);
            MovePiece(tempBoard, successor);
            v = Math.min(v, Max(tempBoard, alpha, beta));
            this.depth--;
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
        Random rand = new Random();
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (++this.depth > MAX_DEPTH) {
            return INVALID_MOVE;
        }
        int v = Integer.MIN_VALUE;
        ArrayList<Move> successors = Successors(this.board, ChessPiece.Team.WHITE);
        if (successors.size() == 0) {
            return INVALID_MOVE;
        }
        Move best = successors.get(rand.nextInt(successors.size()));
        for (Move successor : successors) {
            ChessPiece[][] tempBoard = newBoard(this.board);
            MovePiece(tempBoard, successor);
            v = Math.max(v, Min(tempBoard, alpha, beta));
            this.depth--;
            if (v >= beta) {
                break;
            }
            if (v > alpha) {
                alpha = v;
                best = successor;
            }
        }
        this.depth = 0;
        this.turn = this.turn == ChessPiece.Team.WHITE ? ChessPiece.Team.BLACK : ChessPiece.Team.WHITE;
        MovePiece(this.board, best);
        return best;
    }
}
