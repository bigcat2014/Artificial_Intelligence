public class WhitePlayer extends Player{
    public WhitePlayer(String name){
        super(name);
        for (int i = 0; i < Game.BOARD_SIZE; i++){
            pieces.add(new Pawn(ChessPiece.Team.WHITE));
        }
        pieces.add(new Knight(ChessPiece.Team.WHITE));
    }
}
