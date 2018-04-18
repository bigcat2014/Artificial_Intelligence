import java.util.Random;

public class CarAgent {
    private Environment env;
    private OrderedPair finalLocation;


    public CarAgent(Environment env){ this.env = env; }

    public OrderedPair getFinalLocation(){ return this.finalLocation; }

    public int runRandom(){
        Random rand = new Random();
        int dirChoice;
        Percept percepts;

        while (true){
            percepts = env.getPercepts();

            if (percepts.getAccidentReached()){ this.finalLocation = percepts.getLocation(); return this.env.stop(); }

            if(percepts.getBump()) { env.bumpRecovery(); }

            dirChoice = rand.nextInt(3);
            switch(dirChoice){
                case 0:
                    env.goStraight();
                    break;
                case 1:
                    env.turnLeft();
                    break;
                case 2:
                    env.turnRight();
                    break;
            }
        }
    }

    public int runPlanned(){
        Percept percepts = env.getPercepts();
        if(percepts.getAccidentReached()) { this.finalLocation = percepts.getLocation(); return this.env.stop(); }
        OrderedPair currPosition;
        boolean bump = percepts.getBump();
        int rotations = 3;
        int mapSize = 0;

        while (true) {
            for (int i = 0; i < rotations; i++) {
                if (mapSize == 0) {
                    while(!bump) {
                        env.goStraight();

                        percepts = env.getPercepts();
                        if(percepts.getAccidentReached()) { this.finalLocation = percepts.getLocation(); return this.env.stop(); }
                        bump = percepts.getBump();
                    }
                    env.bumpRecovery();

                    percepts = env.getPercepts();
                    if(percepts.getAccidentReached()) { this.finalLocation = percepts.getLocation(); return this.env.stop(); }
                    currPosition = percepts.getLocation();
                    mapSize = currPosition.getX();
                } else {
                    for (int j = 0; j < mapSize - 1; j++) {
                        env.goStraight();

                        percepts = env.getPercepts();
                        if (percepts.getAccidentReached()) { this.finalLocation = percepts.getLocation(); return this.env.stop(); }
                        currPosition = percepts.getLocation();
                        if(percepts.getBump()) { env.bumpRecovery(); }
                    }
                }
                env.turnRight();

                percepts = env.getPercepts();
                if (percepts.getAccidentReached()) { this.finalLocation = percepts.getLocation(); return this.env.stop(); }
                currPosition = percepts.getLocation();
                bump = percepts.getBump();
                if(percepts.getBump()) { env.bumpRecovery(); }
            }
            rotations = 2;
            mapSize -= 1;
        }
    }
}
