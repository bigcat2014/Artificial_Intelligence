public enum PawnMove implements ChessMove {
    FORWARD(new OrderedPair(0,1)),
    FORWARD_2(new OrderedPair(0, 2)),
    CAPTURE_LEFT(new OrderedPair(-1, 1)),
    CAPTURE_RIGHT(new OrderedPair(1, 1));

    private final OrderedPair _pair;
    PawnMove(OrderedPair value){ this._pair = value; }
    public OrderedPair getMove(){ return this._pair; }
}
