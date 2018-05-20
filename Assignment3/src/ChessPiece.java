public class ChessPiece {
    protected Team pieceTeam;
    protected Boolean isMoved = false;

    public ChessPiece(Team team) {
        this.pieceTeam = team;
    }

    public Boolean getIsmoved() {
        return isMoved;
    }

    public void move(ChessPiece piece) {
        this.isMoved = true;
    }

    public enum Team {
        WHITE("W"),
        BLACK("B");

        private final String team;

        Team(String team) {
            this.team = team;
        }

        @Override
        public String toString() {
            return this.team;
        }
    }

    public Team getTeam() {
        return this.pieceTeam;
    }
}
