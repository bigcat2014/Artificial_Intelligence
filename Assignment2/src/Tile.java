/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
public class Tile {
    private int _value;

    public Tile(int value){ this._value = value; }

    public Integer getValue(){ return this._value; }

    @Override
    public boolean equals(Object o){
        if(o == this) { return true; }
        if(!(o instanceof Tile)) { return false; }

        Tile tile = (Tile) o;

        return this.equals(tile);
    }
    public boolean equals(Tile tile){
        return this.getValue().equals(tile.getValue());
    }
    @Override
    public int hashCode(){
        int result = 17;

        result = 31 * result + _value;
        return result;
    }

    @Override
    public String toString(){ return getValue().toString(); }
}
