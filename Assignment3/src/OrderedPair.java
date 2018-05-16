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

    public void incX(int x){ this._x += x; }
    public void incY(int y){ this._y += y; }

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

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + this._x;
        result = 31 * result + this._y;
        return result;
    }

    public String toString(){ return "(" + getX() + ", " + getY() + ")"; }
}