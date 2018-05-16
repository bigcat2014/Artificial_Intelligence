public class WhitePlayer extends Player{
    public WhitePlayer(String name){
        super(name);
        for (int i = 0; i < Game.BOARD_SIZE; i++){
            pieces.add(new Pawn(i, Game.BOARD_SIZE - 2));
        }
        pieces.add(new Knight(1, Game.BOARD_SIZE - 2));
    }
}
