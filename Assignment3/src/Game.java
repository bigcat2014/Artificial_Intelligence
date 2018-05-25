import java.util.ArrayList;

class Game {
    static final int BOARD_SIZE = 8;
    private ChessPiece[][] board;
    private ArrayList<ChessPiece> capturedPieces;
    private ChessPiece.Team winner;
    private ArrayList<Move> movesList;
    private ChessPiece.Team turn = ChessPiece.Team.WHITE;

    Game() {
        this.board = newBoard();
        this.capturedPieces = new ArrayList<ChessPiece>();
        this.movesList = new ArrayList<Move>();
    }

    ChessPiece[][] newBoard() {
        ChessPiece[][] newBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

        for (int x = 0; x < BOARD_SIZE; x++) {
            newBoard[x][BOARD_SIZE - 2] = new Pawn(ChessPiece.Team.WHITE);
        }
        newBoard[1][BOARD_SIZE - 1] = new Knight(ChessPiece.Team.WHITE);

        for (int x = 0; x < BOARD_SIZE; x++) {
            newBoard[x][1] = new Pawn(ChessPiece.Team.BLACK);
        }
        newBoard[BOARD_SIZE - 2][0] = new Knight(ChessPiece.Team.BLACK);

        return newBoard;
    }

    void update(Move currentMove, String turn) {
        this.turn = this.turn == ChessPiece.Team.WHITE ? ChessPiece.Team.BLACK : ChessPiece.Team.WHITE;
        int x1 = currentMove.getX1() - 1;
        int y1 = currentMove.getY1() - 1;

        if (!this.board[x1][y1].getTeam().toString().equalsIgnoreCase(turn.substring(0, 1))) {
            return;
        }
        MovePiece(currentMove);
        if (this.board[x1][y1] != null) {
            this.capturedPieces.add(this.board[x1][y1]);
            this.board[x1][y1] = null;
        }
    }

    boolean isIllegalMove(Move currentMove) {
        return MoveValidation.isIllegalMove(this.board, currentMove, this.turn);
    }

    boolean isGameOver() {
        boolean gameOver = false;
        for (ChessPiece piece : this.capturedPieces) {
            if (piece instanceof Knight) {
                this.winner = piece.getTeam() == ChessPiece.Team.WHITE ? ChessPiece.Team.BLACK : ChessPiece.Team.WHITE;
                return true;
            }
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (this.board[i][BOARD_SIZE - 1] instanceof Pawn) {
                gameOver = this.board[i][BOARD_SIZE - 1].getTeam() == ChessPiece.Team.BLACK;
                if (gameOver) {
                    this.winner = this.board[i][BOARD_SIZE - 1].getTeam();
                    break;
                }
            }
        }
        if (!gameOver) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (this.board[i][0] instanceof Pawn) {
                    gameOver = this.board[i][0].getTeam() == ChessPiece.Team.WHITE;
                    if (gameOver) {
                        this.winner = this.board[i][0].getTeam();
                        break;
                    }
                }
            }
        }
        return gameOver;
    }

    ChessPiece.Team getWinner() {
        return this.winner;
    }

    Move getWinningMove() {
        return this.movesList.remove(this.movesList.size() - 1);
    }

    void printBoard() {
        StringBuilder boardString = new StringBuilder();
        for (int y = BOARD_SIZE - 1; y >= 0; y--) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (this.board[x][y] == null) {
                    boardString.append(" - ");
                } else {
                    boardString.append(this.board[x][y].toString());
                    boardString.append(this.board[x][y].getTeam().toString());
                    boardString.append(" ");
                }
                boardString.append(" ");
            }
            boardString.append("\n");
        }

        System.out.println(boardString.toString());
    }

    private void MovePiece(Move move) {
        this.movesList.add(move);
        int x1 = move.getX1() - 1;
        int y1 = move.getY1() - 1;
        int x2 = move.getX2() - 1;
        int y2 = move.getY2() - 1;
        ChessPiece temp = this.board[x1][y1];
        this.board[x1][y1] = this.board[x2][y2];
        this.board[x2][y2] = temp;
        this.board[x2][y2].move();
    }
}
