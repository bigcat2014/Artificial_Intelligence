public class Pawn extends ChessPiece {
    //Constructor with team parameter
    public Pawn(ChessPiece.Team team) {
        super(team);
    }
    //Constructor with piece parameter
    public Pawn(Pawn piece) {
        super(piece);
    }
    //ToString method for printing
    @Override
    public String toString() {
        return "P";
    }
}
