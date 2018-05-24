public class Knight extends ChessPiece {
    //Knight Constructor with parameter team
    public Knight(ChessPiece.Team team) {
        super(team);
    }
    //Knight Constructor with parameter piece
    public Knight(Knight piece) {
        super(piece);
    }

    @Override
    public String toString() {
        return "K";
    }
}
