public enum PawnMove {
    //List of possible moves made by Pawn
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
    //Ordered Pair variables
    private final OrderedPair _pair;
    //Constructor with value parameter
    PawnMove(OrderedPair value) {
        this._pair = value;
    }
    //Gets move of the pair of coordinates fo pawn
    public OrderedPair getMove() {
        return this._pair;
    }
    //Return next moves possible
    public PawnMove next() {
        return values()[ordinal() + 1];
    }
    //Returns previous moves possible
    public PawnMove previous() {
        return values()[ordinal() - 1];
    }
    //Returns number of moves
    public static int getNumMoves() {
        return values().length;
    }
}
