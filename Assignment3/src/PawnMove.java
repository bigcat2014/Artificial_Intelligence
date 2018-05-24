public enum PawnMove {

    FORWARD(new OrderedPair(0, 1)) {
        @Override
        public PawnMove previous() {
            return values()[3];
        }
    },
    FORWARD_2(new OrderedPair(0, 2)),
    CAPTURE_LEFT(new OrderedPair(-1, 1)),
    CAPTURE_RIGHT(new OrderedPair(1, 1)) {
        @Override
        public PawnMove next() {
            return values()[0];
        }
    };

    private final OrderedPair _pair;

    PawnMove(OrderedPair value) {
        this._pair = value;
    }

    public OrderedPair getMove() {
        return this._pair;
    }

    public PawnMove next() {
        return values()[ordinal() + 1];
    }

    public PawnMove previous() {
        return values()[ordinal() - 1];
    }
}
