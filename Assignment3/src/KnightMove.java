public enum KnightMove implements ChessMove {
    LEFT_UP(new OrderedPair(-2,1)),
    UP_LEFT(new OrderedPair(-1,2)),
    UP_RIGHT(new OrderedPair(1,2)),
    RIGHT_UP(new OrderedPair(2,1)),
    RIGHT_DOWN(new OrderedPair(2,-1)),
    DOWN_RIGHT(new OrderedPair(1,-2)),
    DOWN_LEFT(new OrderedPair(-1,-2)),
    LEFT_DOWN(new OrderedPair(-2,-1));

    private final OrderedPair _pair;
    KnightMove(OrderedPair value){ this._pair = value; }
    public OrderedPair getMove(){ return this._pair; }
}
