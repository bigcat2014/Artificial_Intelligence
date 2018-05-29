import java.util.ArrayList;

class Game {
    //Variables
    static final int BOARD_SIZE = 8;
    private ChessPiece[][] board;
    private ArrayList<ChessPiece> capturedPieces;
    private ChessPiece.Team winner;
    private ArrayList<Move> movesList;
    private ChessPiece.Team turn = ChessPiece.Team.BLACK;

    //Constructor
    Game() {
        this.board = newBoard();
        this.capturedPieces = new ArrayList<ChessPiece>();
        this.movesList = new ArrayList<Move>();
    }

    //Creating Board
    ChessPiece[][] newBoard() {
        ChessPiece[][] newBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];
        //Loop to create white team components
        for (int x = 0; x < BOARD_SIZE; x++) {
            newBoard[x][BOARD_SIZE - 2] = new Pawn(ChessPiece.Team.WHITE);
        }
        newBoard[1][BOARD_SIZE - 1] = new Knight(ChessPiece.Team.WHITE);
        //Loop to create black team components
        for (int x = 0; x < BOARD_SIZE; x++) {
            newBoard[x][1] = new Pawn(ChessPiece.Team.BLACK);
        }
        newBoard[BOARD_SIZE - 2][0] = new Knight(ChessPiece.Team.BLACK);

        return newBoard;
    }

    //Updates board with move made by users
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

    //Checks if move attempting to be made is legal
    boolean isIllegalMove(Move currentMove) {
        return MoveValidation.isIllegalMove(this.board, currentMove, this.turn, true);// == ChessPiece.Team.WHITE ? ChessPiece.Team.BLACK :ChessPiece.Team.WHITE);
    }

    //Checks if the game is over
    boolean isGameOver() {
        boolean gameOver = false;
        for (ChessPiece piece : this.capturedPieces) {
            if (piece instanceof Knight) {
                this.winner = piece.getTeam() == ChessPiece.Team.WHITE ? ChessPiece.Team.BLACK : ChessPiece.Team.WHITE;
                return true;
            }
        }
//        if (isCheckMate()){
//            return true;
//        }
        if (capturedPieces.size() == BOARD_SIZE * 2) {
            this.winner = ChessPiece.Team.DRAW;
            return true;
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

//    private boolean isCheckMate(){
//        boolean whiteCheckMate = true;
//        boolean blackCheckMate = true;
//        for (int y = 0; y < BOARD_SIZE; y++){
//            for (int x = 0; x < BOARD_SIZE; x++){
//                if (this.board[x][y] instanceof Knight && this.board[x][y].getTeam() == ChessPiece.Team.WHITE) {
//                    if (pieceAttacked(x, y, ChessPiece.Team.WHITE)) {
//                        boolean whiteCheck = true;
//                        KnightMove currMove = KnightMove.LEFT_UP;
//                        int x2;
//                        int y2;
//                        for (int i = 0; i < KnightMove.getNumMoves(); i++) {
//                            OrderedPair knightMoveVals = currMove.getMove();
//                            x2 = x - knightMoveVals.getX();
//                            y2 = y - knightMoveVals.getY();
//                            if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), ChessPiece.Team.WHITE)) {
//                                whiteCheck = pieceAttacked(x2, y2, ChessPiece.Team.WHITE);
//                                whiteCheckMate &= whiteCheck;
//                            }
//                            currMove = currMove.next();
//                        }
//                    }
//                } else {
//                    if (pieceAttacked(x, y, ChessPiece.Team.BLACK)){
//                        boolean blackCheck = true;
//                        KnightMove currMove = KnightMove.LEFT_UP;
//                        int x2;
//                        int y2;
//                        for (int i = 0; i < KnightMove.getNumMoves(); i++) {
//                            OrderedPair knightMoveVals = currMove.getMove();
//                            x2 = x + knightMoveVals.getX();
//                            y2 = y + knightMoveVals.getY();
//                            if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), ChessPiece.Team.BLACK)) {
//                                blackCheck = pieceAttacked(x2, y2, ChessPiece.Team.BLACK);
//                                blackCheckMate &= blackCheck;
//                            }
//                            currMove = currMove.next();
//                        }
//                    }
//                }
//            }
//        }
//        if (blackCheckMate && whiteCheckMate){
//            this.winner = ChessPiece.Team.DRAW;
//        } else if (whiteCheckMate){
//            this.winner = ChessPiece.Team.BLACK;
//        } else if (blackCheckMate){
//            this.winner = ChessPiece.Team.WHITE;
//        }
//        return blackCheckMate || whiteCheckMate;
//    }
//
//    private boolean pieceAttacked(int x, int y, ChessPiece.Team team){
//        int x2;
//        int y2;
//        boolean attacked = false;
//        int modifier = team == ChessPiece.Team.WHITE ? -1 : 1;
//
//        OrderedPair pawnMoveVals = PawnMove.CAPTURE_RIGHT.getMove();
//        x2 = x + (modifier * pawnMoveVals.getX());
//        y2 = y + (modifier * pawnMoveVals.getY());
//        if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
//            if (board[x2][y2] instanceof Pawn && board[x2][y2].getTeam() != team) {
//                attacked = true;
//            }
//        }
//
//        pawnMoveVals = PawnMove.CAPTURE_LEFT.getMove();
//        x2 = x + (modifier * pawnMoveVals.getX());
//        y2 = y + (modifier * pawnMoveVals.getY());
//        if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
//            if (board[x2][y2] instanceof Pawn && board[x2][y2].getTeam() != team) {
//                attacked = true;
//            }
//        }
//
//        KnightMove currMove = KnightMove.LEFT_UP;
//        for (int i = 0; i < KnightMove.getNumMoves(); i++) {
//            OrderedPair knightMoveVals = currMove.getMove();
//            x2 = x + knightMoveVals.getX();
//            y2 = y + knightMoveVals.getY();
//            if (!MoveValidation.isIllegalMove(board, new Move(x + 1, y + 1, x2 + 1, y2 + 1), team)) {
//                if (board[x2][y2] instanceof Knight && board[x2][y2].getTeam() != team) {
//                    attacked = true;
//                    break;
//                }
//            }
//            currMove = currMove.next();
//        }
//        return attacked;
//    }

    ChessPiece.Team getWinner() {
        return this.winner;
    }

    //Return the move which won the game
    Move getWinningMove() {
        return this.movesList.remove(this.movesList.size() - 1);
    }

    //Print the board
    void printBoard() {
        StringBuilder boardString = new StringBuilder();
        StringBuilder blackCaptured = new StringBuilder();
        StringBuilder whiteCaptured = new StringBuilder();
        for (int y = BOARD_SIZE - 1; y >= 0; y--) {
            boardString.append(y + 1);
            boardString.append(' ');
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
        boardString.append("  ");
        for (int i = 0; i < BOARD_SIZE; i++) {
            boardString.append(" ");
            boardString.append(i + 1);
            boardString.append("  ");
        }
        boardString.append("\n\n");

        whiteCaptured.append("Captured white pieces: ");
        blackCaptured.append("Captured black pieces: ");
        for (ChessPiece piece : this.capturedPieces) {
            if (piece.getTeam() == ChessPiece.Team.WHITE) {
                whiteCaptured.append(piece.toString());
                whiteCaptured.append(piece.getTeam().toString());
                whiteCaptured.append(", ");
            } else {
                blackCaptured.append(piece.toString());
                blackCaptured.append(piece.getTeam().toString());
                blackCaptured.append(", ");
            }
        }
        boardString.append(whiteCaptured);
        boardString.append("\n");
        boardString.append(blackCaptured);
        boardString.append("\n");

        System.out.println(boardString.toString());
    }

    //Move piece on board
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
