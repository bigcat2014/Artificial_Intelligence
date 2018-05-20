public class ChessPiece<T extends Enum<T> & ChessMove> {
    protected Team pieceTeam;

    public ChessPiece(Team team){
        this.pieceTeam = team;
    }

    public enum Team {
        WHITE("W"),
        BLACK("B");

        private final String team;
        Team(String team){ this.team = team; }
        @Override
        public String toString(){ return this.team; }
    }

    public Team getTeam() { return this.pieceTeam; }
}
