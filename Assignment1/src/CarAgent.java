import java.util.Random;

public class CarAgent {
    private Environment env;


    public CarAgent(Environment env){
        this.env = env;
    }

    public int runRandom(){
        Random rand = new Random();
        int dirChoice;
        Percept percepts;

        while (true){
            percepts = env.getPercepts();

            if (percepts.getAccidentReached()){ break; }

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
        return env.stop();
    }

    public int runPlanned(){
        Percept percepts;
        OrderedPair currPosition;
        Environment.SignalColor currSignal;
        boolean bump;

        do {
            percepts = env.getPercepts();
            currPosition = percepts.getLocation();
            currSignal = percepts.getSignalColor();
            bump = percepts.getBump();



        } while (!percepts.getAccidentReached());

        return 0;
    }
}
