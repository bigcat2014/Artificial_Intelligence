/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 1
 */

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
        System.out.println("Random or Planned run mode? r/p [r]:");
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

        int totalTime = 0;
        switch(mode){
            case PLANNED:
                totalTime = car.runPlanned();
                break;
            case RANDOM:
                totalTime = car.runRandom();
        }

        if (DEBUG) { env.printPath(); }
        System.out.println("Done");
        System.out.println("Accident found at: " + car.getFinalLocation());
        System.out.println("Total time: " + totalTime);
    }
}
