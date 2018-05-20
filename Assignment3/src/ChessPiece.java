public class ChessPiece<T extends Enum<T> & ChessMove> {
    protected Team pieceTeam;
    protected Boolean ismoved=false;

    public ChessPiece(Team team){
        this.pieceTeam = team;
    }


    public Boolean getIsmoved(){
        return ismoved;
    }
    public void move(ChessPiece piece){
        this.ismoved=true;
    }

    public enum Team {
        WHITE("W"),
        BLACK("B");

        private final String team;
        Team(String team){ this.team = team; }
        @Override
        public String toString(){ return this.team; }
    }

    public String getTeam() { return this.pieceTeam.toString(); }
}
