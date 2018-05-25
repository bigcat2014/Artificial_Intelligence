public class ChessPiece {
    //Object variables
    protected Team pieceTeam;
    protected Boolean isMoved = false;

    //Constructor with parameter team
    public ChessPiece(Team team) {
        this.pieceTeam = team;
    }

    //Constructor with parameter piece
    public ChessPiece(ChessPiece piece) {
        this.pieceTeam = piece.getTeam();
        this.isMoved = piece.getIsmoved();
    }

    //Returns whether or not a piece has been moved
    public Boolean getIsmoved() {
        return this.isMoved;
    }

    //movign a piece method
    public void move() {
        this.isMoved = true;
    }

    //Enum for Black and White teams
    public enum Team {
        WHITE("W"),
        BLACK("B"),
        DRAW("Draw");

        private final String team;

        Team(String team) {
            this.team = team;
        }

        @Override
        public String toString() {
            return this.team;
        }
    }

    //Returns team
    public Team getTeam() {
        return this.pieceTeam;
    }
}
