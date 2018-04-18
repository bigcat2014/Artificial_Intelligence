/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 1
 */

public class Percept {
    private OrderedPair _location;
    private boolean _bump;
    private Environment.SignalColor _signalColor;
    private boolean _reachedAccidentSite;

    public Percept(OrderedPair location, boolean bump, Environment.SignalColor signalColor, boolean _reachedAccidentSite){
        this._location = location;
        this._bump = bump;
        this._signalColor = signalColor;
        this._reachedAccidentSite = _reachedAccidentSite;
    }

    public OrderedPair getLocation(){ return this._location; }
    public boolean getBump(){ return this._bump; }
    public Environment.SignalColor getSignalColor(){ return this._signalColor; }
    public boolean getAccidentReached(){ return this._reachedAccidentSite; }
}
