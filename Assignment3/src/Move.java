// Move is moving the men at (x1,y1) to the position (x2,y2)
public class Move {
    private int x1, y1;
    private int x2, y2;

    public Move(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    int getX1() { return x1; }
    int getY1() { return y1; }
    int getX2() { return x2; }
    int getY2() { return y2; }

    public void printMove() { System.out.printf("Move from (%d, %d) to (%d, %d)\n", this.x1, this.y1, this.x2, this.y2); }
}