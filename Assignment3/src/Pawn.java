public class Pawn extends ChessPiece {
    public Pawn(ChessPiece.Team team){
        super(team);
    }

    @Override
    public String toString() { return "P"; }
}
