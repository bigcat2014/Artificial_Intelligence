/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 1
 */

import java.util.LinkedList;
import java.util.Random;

public class Environment {
    private final int mapSize;

    private OrderedPair currPosition;
    private Direction currDirection;
    private SignalColor eastWestSignal;
    private int currTime;

    private LinkedList<OrderedPair> positions;
    private LinkedList<Direction> directions;
    private LinkedList<MoveType> moves;
    private LinkedList<Integer> times;

    private OrderedPair accidentSite;

    private boolean bumped;
    private boolean siteReached;

    public enum SignalColor{
        RED_LIGHT,
        GREEN_LIGHT
    }
    public enum Direction{
        NORTH(){
            @Override
            public Direction previous(){ return values()[3]; }
        },
        EAST(),
        SOUTH(),
        WEST(){
            @Override
            public Direction next(){ return values()[0]; }
        };

        public Direction next(){ return values()[ordinal() + 1]; }
        public Direction previous(){ return values()[ordinal() - 1]; }
    }
    public enum MoveType{
        BUMP("Bump"),
        WAIT("Wait"),
        STRAIGHT("Go Straight"),
        TURN_LEFT("Turn Left"),
        TURN_RIGHT("Turn Right");

        private final String _value;
        MoveType(String value){ this._value = value; }
        public String getValue(){ return this._value; }
    }
    public enum MoveTime {
        START_TIME(0),
        BUMP_TIME(10),
        WAIT_TIME(1),
        STRAIGHT_TIME(3),
        LEFT_TIME(4),
        RIGHT_TIME(4);

        private final int _time;
        MoveTime(int time) { this._time = time; }
        public int getTime(){ return this._time; }
    }

    public Environment(){
        this.mapSize = 4;

        this.currPosition = new OrderedPair();
        this.currDirection = Direction.EAST;
        this.eastWestSignal = SignalColor.GREEN_LIGHT;
        this.currTime = MoveTime.START_TIME.getTime();


        this.positions = new LinkedList<>();
        this.directions = new LinkedList<>();
        this.moves = new LinkedList<>();
        this.times = new LinkedList<>();

        this.positions.add(new OrderedPair(this.currPosition));
        this.directions.add(this.currDirection);
        this.times.add(this.currTime);

        Random rand = new Random();
        this.accidentSite = new OrderedPair(rand.nextInt(this.mapSize), rand.nextInt(this.mapSize));

        this.bumped = false;
        this.siteReached = false;
    }

    public OrderedPair getAccidentSite(){ return this.accidentSite; }

    public Percept getPercepts(){
        return new Percept(new OrderedPair(this.currPosition), this.bumped, this.eastWestSignal, this.siteReached);
    }

    public void goStraight(){
        this.eastWestSignal = currTime % 2 == 0 ? SignalColor.GREEN_LIGHT : SignalColor.RED_LIGHT;

        switch (this.currDirection){
            case NORTH:
                if (this.eastWestSignal == SignalColor.GREEN_LIGHT) { waitOnARedLight(); }
                this.currPosition.decY();
                break;
            case EAST:
                if (this.eastWestSignal == SignalColor.RED_LIGHT) { waitOnARedLight(); }
                this.currPosition.incX();
                break;
            case SOUTH:
                if (this.eastWestSignal == SignalColor.GREEN_LIGHT) { waitOnARedLight(); }
                this.currPosition.incY();
                break;
            case WEST:
                if (this.eastWestSignal == SignalColor.RED_LIGHT) { waitOnARedLight(); }
                this.currPosition.decX();
                break;
        }

        this.positions.add(new OrderedPair(this.currPosition));
        this.directions.add(this.currDirection);
        this.moves.add(MoveType.STRAIGHT);
        this.currTime += MoveTime.STRAIGHT_TIME.getTime();
        this.times.add(MoveTime.STRAIGHT_TIME.getTime());

        this.siteReached = this.currPosition.equals(this.accidentSite);
        this.bumped =   this.currPosition.getX() < 0 ||
                        this.currPosition.getX() == this.mapSize ||
                        this.currPosition.getY() < 0 ||
                        this.currPosition.getY() == this.mapSize;
    }

    public void turnLeft(){
        this.currDirection = this.currDirection.previous();
        this.eastWestSignal = currTime % 2 == 0 ? SignalColor.GREEN_LIGHT : SignalColor.RED_LIGHT;

        switch (this.currDirection){
            case NORTH:
                if (this.eastWestSignal == SignalColor.GREEN_LIGHT) { waitOnARedLight(); }
                this.currPosition.decY();
                break;
            case EAST:
                if (this.eastWestSignal == SignalColor.RED_LIGHT) { waitOnARedLight(); }
                this.currPosition.incX();
                break;
            case SOUTH:
                if (this.eastWestSignal == SignalColor.GREEN_LIGHT) { waitOnARedLight(); }
                this.currPosition.incY();
                break;
            case WEST:
                if (this.eastWestSignal == SignalColor.RED_LIGHT) { waitOnARedLight(); }
                this.currPosition.decX();
                break;
        }

        this.positions.add(new OrderedPair(this.currPosition));
        this.directions.add(this.currDirection);
        this.moves.add(MoveType.TURN_LEFT);
        this.currTime += MoveTime.LEFT_TIME.getTime();
        this.times.add(MoveTime.LEFT_TIME.getTime());

        this.siteReached = this.currPosition.equals(this.accidentSite);
        this.bumped =   this.currPosition.getX() < 0 ||
                        this.currPosition.getX() == this.mapSize ||
                        this.currPosition.getY() < 0 ||
                        this.currPosition.getY() == this.mapSize;
    }

    public void turnRight(){
        this.currDirection = this.currDirection.next();
        this.eastWestSignal = currTime % 2 == 0 ? SignalColor.GREEN_LIGHT : SignalColor.RED_LIGHT;

        switch (this.currDirection){
            case NORTH:
                if (this.eastWestSignal == SignalColor.GREEN_LIGHT) { waitOnARedLight(); }
                this.currPosition.decY();
                break;
            case EAST:
                if (this.eastWestSignal == SignalColor.RED_LIGHT) { waitOnARedLight(); }
                this.currPosition.incX();
                break;
            case SOUTH:
                if (this.eastWestSignal == SignalColor.GREEN_LIGHT) { waitOnARedLight(); }
                this.currPosition.incY();
                break;
            case WEST:
                if (this.eastWestSignal == SignalColor.RED_LIGHT) { waitOnARedLight(); }
                this.currPosition.decX();
                break;
        }

        this.positions.add(new OrderedPair(this.currPosition));
        this.directions.add(this.currDirection);
        this.moves.add(MoveType.TURN_RIGHT);
        this.currTime += MoveTime.RIGHT_TIME.getTime();
        this.times.add(MoveTime.RIGHT_TIME.getTime());

        this.siteReached = this.currPosition.equals(this.accidentSite);
        this.bumped =   this.currPosition.getX() < 0 ||
                        this.currPosition.getX() == this.mapSize ||
                        this.currPosition.getY() < 0 ||
                        this.currPosition.getY() == this.mapSize;
    }

    public void waitOnARedLight(){
        this.currPosition = new OrderedPair(this.positions.peekLast());
        this.positions.add(new OrderedPair(this.currPosition));

        Direction tempDir = this.currDirection;
        this.currDirection = this.directions.peekLast();
        this.directions.add(this.currDirection);
        this.currDirection = tempDir;

        this.moves.add(MoveType.WAIT);

        this.currTime += MoveTime.WAIT_TIME.getTime();
        this.times.add(MoveTime.WAIT_TIME.getTime());
    }

    public int stop() { return this.currTime; }

    public void bumpRecovery(){
        this.positions.removeLast();
        this.currPosition = new OrderedPair(this.positions.peekLast());
        this.positions.add(new OrderedPair(this.currPosition));

        this.directions.removeLast();
        this.currDirection = this.directions.peekLast();
        this.directions.add(this.currDirection);

        this.moves.removeLast();
        this.moves.add(MoveType.BUMP);

        this.currTime -= this.times.removeLast();
        this.currTime += MoveTime.BUMP_TIME.getTime();
        this.times.add(MoveTime.BUMP_TIME.getTime());

        this.bumped = false;
    }

    public void printPath(){
        int currTime = 0;
        Direction dir;
        MoveType move;
        String lightDirection;

        System.out.println("The path of the car:");
        System.out.println("Time\tCar Location\tCar Direction\tGreen Light Direction\tMove Chosen\n");

        while (!this.moves.isEmpty()){
            currTime += this.times.remove();
            dir = this.directions.remove();
            move = this.moves.remove();

            if (currTime % 2 == 1) { lightDirection = "N-S"; }
            else{ lightDirection = "E-W"; }

            System.out.print(currTime + "\t\t");
            System.out.print(this.positions.remove() + "\t\t\t");
            System.out.print(dir + "\t\t\t");
            System.out.print(lightDirection + "\t\t\t\t\t\t");

            System.out.println(move.getValue());
        }

        currTime += this.times.remove();
        dir = this.directions.remove();

        if (currTime % 2 == 1) { lightDirection = "N-S"; }
        else{ lightDirection = "E-W"; }

        System.out.print(currTime + "\t\t");
        System.out.print(this.positions.remove() + "\t\t\t");
        System.out.print(dir + "\t\t\t");
        System.out.print(lightDirection + "\t\t\t\t\t\t");
    }
}
