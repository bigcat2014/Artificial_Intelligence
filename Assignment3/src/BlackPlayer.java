public class BlackPlayer extends Player {
    public BlackPlayer(String name){
        super(name);
        for (int i = 0; i < Game.BOARD_SIZE; i++){
            pieces.add(new Pawn(i, 1));
        }
        pieces.add(new Knight(Game.BOARD_SIZE - 2, 1));
    }
}
