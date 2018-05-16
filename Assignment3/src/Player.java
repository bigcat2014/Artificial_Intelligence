import java.util.ArrayList;

public class Player{
    protected String name;
    protected ArrayList<ChessPiece> pieces;
    protected Move currentMove;
    protected int depth = 0;

    public Player(String name){
        this.name = name;
    }

    private int Utility(Game state){
        return 0;
    }

    private ArrayList<Game> Successors(Game state){
        ArrayList<Game> successors = new ArrayList<Game>();
        successors.add(state);
        return successors;
    }

    private int Max(Game state, int alpha, int beta){
//         TODO: Implement Max of min-max search
//        this.depth ++;
//        if (isGameOver()) { return Utility(state); }
        int v = Integer.MIN_VALUE;
//        ArrayList<Game> successors = Successors(state);
//        for (Game successor : successors){
//            v = Math.max(v, Min(successor, alpha, beta));
//            if (v >= beta) { break; }
//            alpha = Math.max(v, alpha);
//        }
        return v;
    }
    private int Min(Game state, int alpha, int beta){
//         TODO: Implement Max of min-max search
//        this.depth++;
//        if (isGameOver()) { return Utility(state); }
        int v = Integer.MAX_VALUE;
//        ArrayList<Game> successors = Successors(state);
//        for (Game successor : successors){
//            v = Math.min(v, Max(successor, alpha, beta));
//            if (v < alpha) { break; }
//            beta = Math.min(v, beta);
//        }
        return v;
    }

    public Move getMove(){
        // TODO: Implement alpha - beta algorithm
        return new Move(0,0,0,0);
    }

    public void update(Move currentMove){
        this.currentMove = currentMove;
    }
}
