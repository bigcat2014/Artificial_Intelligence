public enum KnightMove {
    //Lists different types of moves to be made by Knight
    LEFT_UP(new OrderedPair(-2, 1)) {
        @Override
        public KnightMove previous() {
            return values()[7];
        }
    },
    UP_LEFT(new OrderedPair(-1, 2)),
    UP_RIGHT(new OrderedPair(1, 2)),
    RIGHT_UP(new OrderedPair(2, 1)),
    RIGHT_DOWN(new OrderedPair(2, -1)),
    DOWN_RIGHT(new OrderedPair(1, -2)),
    DOWN_LEFT(new OrderedPair(-1, -2)),
    LEFT_DOWN(new OrderedPair(-2, -1)) {
        @Override
        public KnightMove next() {
            return values()[0];
        }
    },;
    // ordered pair variable
    private final OrderedPair _pair;
    //Constructor with value parameter
    KnightMove(OrderedPair value) {
        this._pair = value;
    }
    //Returns Knight's move
    public OrderedPair getMove() {
        return this._pair;
    }
    //Gets next move
    public KnightMove next() {
        return values()[ordinal() + 1];
    }
    //Gets previous move
    public KnightMove previous() {
        return values()[ordinal() - 1];
    }
    //Returns number of moves
    public static int getNumMoves() {
        return values().length;
    }
}
