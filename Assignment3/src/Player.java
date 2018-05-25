import java.util.ArrayList;

public class Player {
    //Variables
    private String name;
    private ArrayList<ChessPiece> capturedPieces;
    private ChessPiece.Team winner;
    private ArrayList<Move> movesList;

    protected ChessPiece.Team turn = ChessPiece.Team.WHITE;
    protected ChessPiece[][] board;
    protected final Move INVALID_MOVE = new Move(1, 1, 1, 1);
    protected final int MAX_DEPTH = 6;
    protected int depth = 0;

    //Constructor with name parameters
    public Player(String name) {
        this.name = name;
        this.board = newBoard();
        this.capturedPieces = new ArrayList<ChessPiece>();
        this.movesList = new ArrayList<Move>();
    }

    //New board Object
    ChessPiece[][] newBoard() {
        ChessPiece[][] newBoard = new ChessPiece[Game.BOARD_SIZE][Game.BOARD_SIZE];

        for (int x = 0; x < Game.BOARD_SIZE; x++) {
            newBoard[x][Game.BOARD_SIZE - 2] = new Pawn(ChessPiece.Team.WHITE);
        }
        newBoard[1][Game.BOARD_SIZE - 1] = new Knight(ChessPiece.Team.WHITE);

        for (int x = 0; x < Game.BOARD_SIZE; x++) {
            newBoard[x][1] = new Pawn(ChessPiece.Team.BLACK);
        }
        newBoard[Game.BOARD_SIZE - 2][0] = new Knight(ChessPiece.Team.BLACK);

        return newBoard;
    }

    //Populates board with Pieces
    ChessPiece[][] newBoard(ChessPiece[][] currentBoard) {
        ChessPiece[][] newBoard = new ChessPiece[Game.BOARD_SIZE][Game.BOARD_SIZE];

        for (int y = 0; y < Game.BOARD_SIZE; y++) {
            for (int x = 0; x < Game.BOARD_SIZE; x++) {
                if (currentBoard[x][y] instanceof Pawn) {
                    newBoard[x][y] = new Pawn((Pawn) currentBoard[x][y]);
                } else if (currentBoard[x][y] instanceof Knight) {
                    newBoard[x][y] = new Knight((Knight) currentBoard[x][y]);
                } else {
                    newBoard[x][y] = null;
                }
            }
        }

        return newBoard;
    }

    //Checks if game is over
//    boolean isGameOver() {
//        return isGameOver(this.board);
//    }
    boolean isGameOver(ChessPiece[][] board) {
        boolean gameOver = false;
        for (ChessPiece piece : this.capturedPieces) {
            if (piece instanceof Knight) {
                this.winner = piece.getTeam() == ChessPiece.Team.WHITE ? ChessPiece.Team.BLACK : ChessPiece.Team.WHITE;
                return true;
            }
        }
        for (int i = 0; i < Game.BOARD_SIZE; i++) {
            if (this.board[i][Game.BOARD_SIZE - 1] instanceof Pawn) {
                gameOver = board[i][Game.BOARD_SIZE - 1].getTeam() == ChessPiece.Team.BLACK;
                if (gameOver) {
                    winner = board[i][Game.BOARD_SIZE - 1].getTeam();
                    break;
                }
            }
        }
        if (!gameOver) {
            for (int i = 0; i < Game.BOARD_SIZE; i++) {
                if (this.board[i][0] instanceof Pawn) {
                    gameOver = board[i][0].getTeam() == ChessPiece.Team.WHITE;
                    if (gameOver) {
                        winner = board[i][0].getTeam();
                        break;
                    }
                }
            }
        }
        return gameOver;
    }

    protected int Utility(ChessPiece[][] board, ChessPiece.Team team) {
        return UtilityEngine.Utility(board, this.winner, isGameOver(board), team);
    }

//    boolean isIllegalMove(Move currentMove) {
//        return MoveValidation.isIllegalMove(this.board, currentMove, this.turn);
//    }

    // Gets the successors of the current board
    ArrayList<Move> Successors(ChessPiece[][] board, ChessPiece.Team team) {
        ArrayList<Move> successors = new ArrayList<Move>();
        int x2;
        int y2;
        Move move;
        int modifier = team == ChessPiece.Team.WHITE ? -1 : 1;
        if (team == ChessPiece.Team.WHITE) {
            for (int y = 0; y < Game.BOARD_SIZE; y++) {
                for (int x = 0; x < Game.BOARD_SIZE; x++) {
                    if (board[x][y] instanceof Pawn) {
                        PawnMove currMove = PawnMove.FORWARD;
                        for (int i = 0; i < PawnMove.getNumMoves(); i++) {
                            OrderedPair moveVals = currMove.getMove();
                            if (board[x][y].getTeam() == team) {
                                x2 = x + (-1 * moveVals.getX()) + 1;
                                y2 = y + (-1 * moveVals.getY()) + 1;
                                move = new Move(x + 1, y + 1, x2, y2);
                                if (!MoveValidation.isIllegalMove(board, move, team, true)) {
                                    successors.add(move);
                                }
                                currMove = currMove.next();
                            }
                        }
                    } else if (board[x][y] instanceof Knight) {
                        KnightMove currMove = KnightMove.LEFT_UP;
                        for (int i = 0; i < KnightMove.getNumMoves(); i++) {
                            OrderedPair moveVals = currMove.getMove();
                            if (board[x][y].getTeam() == team) {
                                x2 = x + (-1 * moveVals.getX()) + 1;
                                y2 = y + (-1 * moveVals.getY()) + 1;
                                move = new Move(x + 1, y + 1, x2, y2);
                                if (!MoveValidation.isIllegalMove(board, move, team, true)) {
                                    successors.add(move);
                                }
                            }
                            currMove = currMove.next();
                        }
                    }
                }
            }
        } else {
            for (int y = Game.BOARD_SIZE - 1; y >= 0; y--) {
                for (int x = Game.BOARD_SIZE - 1; x >= 0; x--) {
                    if (board[x][y] instanceof Pawn) {
                        PawnMove currMove = PawnMove.FORWARD;
                        for (int i = 0; i < PawnMove.getNumMoves(); i++) {
                            OrderedPair moveVals = currMove.getMove();
                            if (board[x][y].getTeam() == team) {
                                x2 = x + moveVals.getX() + 1;
                                y2 = y + moveVals.getY() + 1;
                                move = new Move(x + 1, y + 1, x2, y2);
                                if (!MoveValidation.isIllegalMove(board, move, team, true)) {
                                    successors.add(move);
                                }
                                currMove = currMove.next();
                            }
                        }
                    } else if (board[x][y] instanceof Knight) {
                        KnightMove currMove = KnightMove.LEFT_UP;
                        for (int i = 0; i < KnightMove.getNumMoves(); i++) {
                            OrderedPair moveVals = currMove.getMove();
                            if (board[x][y].getTeam() == team) {
                                x2 = x + moveVals.getX() + 1;
                                y2 = y + moveVals.getY() + 1;
                                move = new Move(x + 1, y + 1, x2, y2);
                                if (!MoveValidation.isIllegalMove(board, move, team, true)) {
                                    successors.add(move);
                                }
                            }
                            currMove = currMove.next();
                        }
                    }
                }
            }
        }
        return successors;
    }

    //Updates move on Board
    void update(Move currentMove) {
        this.turn = this.turn == ChessPiece.Team.WHITE ? ChessPiece.Team.BLACK : ChessPiece.Team.WHITE;
        int x1 = currentMove.getX1() - 1;
        int y1 = currentMove.getY1() - 1;

        MovePiece(this.board, currentMove);
        if (this.board[x1][y1] != null) {
            this.capturedPieces.add(this.board[x1][y1]);
            this.board[x1][y1] = null;
        }
    }

    //Moves piece along board
    protected void MovePiece(ChessPiece[][] board, Move move) {
        this.movesList.add(move);
        int x1 = move.getX1() - 1;
        int y1 = move.getY1() - 1;
        int x2 = move.getX2() - 1;
        int y2 = move.getY2() - 1;
        ChessPiece temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
        board[x2][y2].move();
    }
}
