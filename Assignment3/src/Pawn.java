public class Pawn extends ChessPiece<PawnMove> {
    public Pawn(int x, int y){
        super(x, y);
    }

    public void Move(PawnMove move){
        OrderedPair pair = move.getMove();
        this.position.incX(pair.getX());
        this.position.incY(pair.getY());
    }
}
