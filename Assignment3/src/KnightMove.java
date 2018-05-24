public enum KnightMove {
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

    private final OrderedPair _pair;

    KnightMove(OrderedPair value) {
        this._pair = value;
    }

    public OrderedPair getMove() {
        return this._pair;
    }

    public KnightMove next() {
        return values()[ordinal() + 1];
    }

    public KnightMove previous() {
        return values()[ordinal() - 1];
    }
}
