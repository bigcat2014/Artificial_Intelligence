public class BlackPlayer extends Player {
    public BlackPlayer(String name){
        super(name);
        for (int x = 0; Game.BOARD_SIZE > x; x++) {
            this.board[x][Game.BOARD_SIZE - 2] = new Pawn(ChessPiece.Team.WHITE);
        }
        this.board[1][Game.BOARD_SIZE - 1] = new Knight(ChessPiece.Team.WHITE);

        for (int x = 0; x < Game.BOARD_SIZE; x++) {
            this.board[x][1] = new Pawn(ChessPiece.Team.BLACK);
        }
        this.board[Game.BOARD_SIZE - 2][0] = new Knight(ChessPiece.Team.BLACK);
    }

    public Move getMove() {
        // TODO: Implement alpha - beta algorithm
        int v = Min(this.board, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return new Move(0, 0, 0, 0);
    }
}
