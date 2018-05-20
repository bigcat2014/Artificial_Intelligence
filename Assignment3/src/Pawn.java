public class Pawn extends ChessPiece<PawnMove> {
    public Pawn(ChessPiece.Team team){
        super(team);
    }

    @Override
    public String toString() { return "P"; }
}
