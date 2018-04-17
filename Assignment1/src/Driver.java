import java.util.Scanner;

public class Driver {
    private static boolean DEBUG = false;

    private static Environment env = new Environment();
    private static CarAgent car = new CarAgent(env);


    public enum RunMode {
        RANDOM(true),
        PLANNED(false);

        private final boolean mode;

        RunMode(boolean mode) {
            this.mode = mode;
        }

        private boolean getMode() {
            return mode;
        }
    }

    public static void main(String[] args) {
        RunMode mode = RunMode.RANDOM;

        if (args.length > 0) {
            DEBUG = args[0].toLowerCase().equals("-d");
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Which Run mode would you like to use? r/p [r]:");
        System.out.print(">> ");
        String modeString = scan.nextLine();
        if(modeString != null) {
            switch (modeString.toLowerCase()) {
                case "p":
                    mode = RunMode.PLANNED;
                    break;
                default:
                    mode = RunMode.RANDOM;
                    break;
            }
        }

        if (DEBUG) { System.out.println("Run mode selected: " + mode); }
        if (DEBUG) { System.out.println("Accident site: " + env.getAccidentSite()); }

        switch(mode){
            case PLANNED:
                car.runPlanned();
                break;
            case RANDOM:
                car.runRandom();
        }

        if (DEBUG) { env.printPath(); }

        System.out.println("Done");
    }
}
