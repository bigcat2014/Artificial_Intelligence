public class ChessPiece<T extends Enum<T> & ChessMove> {
    protected OrderedPair position;

    public ChessPiece(int x, int y){
        this.position = new OrderedPair(x, y);
    }

    public void Move(T move){
        OrderedPair pair = move.getMove();
        this.position.incX(pair.getX());
        this.position.incY(pair.getY());
    }
}
