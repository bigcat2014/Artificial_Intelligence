public class Knight extends ChessPiece<KnightMove> {
    public Knight(int x, int y){
        super(x, y);
    }

    public void Move(KnightMove move){
        OrderedPair pair = move.getMove();
        this.position.incX(pair.getX());
        this.position.incY(pair.getY());
    }
}
