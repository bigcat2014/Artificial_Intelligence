public class Player {
    private Move currentMove;
    public Player(String arg, int boardSize, int maxMoveTime){

    }

    public Move getMove(){
        return new Move(0,0,0,0);
    }

    public void update(Move currentMove){
        this.currentMove = currentMove;
    }
}
