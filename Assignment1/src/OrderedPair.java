/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 1
 */

public class OrderedPair{
    private int _x;
    private int _y;

    OrderedPair(){
        this._x = 0;
        this._y = 0;
    }
    OrderedPair(int x, int y){
        this._x = x;
        this._y = y;
    }
    OrderedPair(OrderedPair pair){
        this._x = pair.getX();
        this._y = pair.getY();
    }

    public int getX(){ return this._x; }
    public int getY(){ return this._y; }

    public void incX(){ this._x++; }
    public void incY(){ this._y++; }

    public void decX(){ this._x--; }
    public void decY(){ this._y--; }

    @Override
    public boolean equals(Object o){
        if(o == this) { return true; }
        if(!(o instanceof OrderedPair)) { return false; }

        OrderedPair pair = (OrderedPair) o;

        return this.equals(pair);
    }
    public boolean equals(OrderedPair pair){
        return getX() == pair.getX() && getY() == pair.getY();
    }

    public String toString(){ return "(" + getX() + ", " + getY() + ")"; }
}