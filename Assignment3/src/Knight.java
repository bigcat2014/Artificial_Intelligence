public class Knight extends ChessPiece<KnightMove> {
    public Knight(ChessPiece.Team team){
        super(team);
    }

    @Override
    public String toString() { return "K"; }
}
