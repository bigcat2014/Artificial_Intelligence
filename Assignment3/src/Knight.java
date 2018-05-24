public class Knight extends ChessPiece {
    public Knight(ChessPiece.Team team) {
        super(team);
    }

    public Knight(Knight piece) {
        super(piece);
    }

    @Override
    public String toString() {
        return "K";
    }
}
