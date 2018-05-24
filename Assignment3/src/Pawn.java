public class Pawn extends ChessPiece {
    public Pawn(ChessPiece.Team team) {
        super(team);
    }

    public Pawn(Pawn piece) {
        super(piece);
    }

    @Override
    public String toString() {
        return "P";
    }
}
