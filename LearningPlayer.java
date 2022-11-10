import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;

public class LearningPlayer extends JourneyOfSamsaraPlayer {
    private int generation;
    private double sensitivity;
    private boolean enableLearning;

    private GameStateStats[] dictionary;
    private ArrayList<Integer> visitedGameStates;

    public LearningPlayer(JourneyOfSamsaraGame game) {
        super(game);
        generation = 1;
        sensitivity = 0.0;
        enableLearning = true;
        dictionary = new GameStateStats[GameState.TOTAL_STATES];
        readGameStateStats();
        visitedGameStates = new ArrayList<>();
    }

    public LearningPlayer(JourneyOfSamsaraGame game, int gen) {
        this(game);
        generation = gen;
    }

    private void readGameStateStats() {
        File f = null;
        Scanner sc = null;
        try {
            f = new File("/Users/david1/IdeaProjects/JourneyOfSamsara/src/gamestates" + generation + ".txt");
            sc = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find gamestates" + generation + ".txt");
            System.exit(1);
        }
        for (int i = 0; i < GameState.TOTAL_STATES; i++) {
            int player = sc.nextInt();
            int opponent = sc.nextInt();
            byte movementDice = sc.nextByte();
            int forwardsWin = sc.nextInt();
            int forwardsLoss = sc.nextInt();
            int backwardsWin = sc.nextInt();
            int backwardsLoss = sc.nextInt();
            dictionary[i] = new GameStateStats(player, opponent, movementDice, forwardsWin, forwardsLoss, backwardsWin, backwardsLoss);
        }
    }

    public boolean makeMove() {         //to go backwards is true, to go forwards is false
        double backwardsChance = 0.0;

        GameState gameState = getGame().extractCurrentGameState();
        int i = gameState.convertToIndex();

        backwardsChance = 0.5 + sensitivity * ((double)(dictionary[i].getBackwardsWin()) /
                (double)(dictionary[i].getBackwardsWin() + dictionary[i].getBackwardsLoss()) - (double)(dictionary[i].getForwardsWin()) /
                (double)(dictionary[i].getForwardsWin() + dictionary[i].getForwardsLoss()));
        if (enableLearning) {
            visitedGameStates.add(i);
        }      //big improvement from brute force O(n) linear search to O(1) conversion to index and lookup


        if (getGame().canMoveBackwards()) {
            boolean isMovingBackwards = Math.random() < backwardsChance;
            if (enableLearning) {
                if (isMovingBackwards) {
                    //multiply the state just added by -1
                    visitedGameStates.add(-visitedGameStates.remove(visitedGameStates.size() - 1));
                }
            }
            return isMovingBackwards;
        } else {
            return false;
        }
    }

    public void updateGameStateStats(boolean gameWon) {
        if (!enableLearning) {
            return;
        }

        if (gameWon) {
            for (int i = 0; i < visitedGameStates.size(); i++) {
                if (visitedGameStates.get(i) < 0) {         //at that game state chose to move backwards
                    dictionary[-visitedGameStates.get(i)].setBackwardsWin(dictionary[-visitedGameStates.get(i)].getBackwardsWin() + 1);
                } else {
                    dictionary[visitedGameStates.get(i)].setForwardsWin(dictionary[visitedGameStates.get(i)].getForwardsWin() + 1);
                }
            }
        } else {
            for (int i = 0; i < visitedGameStates.size(); i++) {
                if (visitedGameStates.get(i) < 0) {         //at that game state chose to move backwards
                    dictionary[-visitedGameStates.get(i)].setBackwardsLoss(dictionary[-visitedGameStates.get(i)].getBackwardsLoss() + 1);
                } else {
                    dictionary[visitedGameStates.get(i)].setForwardsLoss(dictionary[visitedGameStates.get(i)].getForwardsLoss() + 1);
                }
            }
        }
        visitedGameStates.clear();
    }

    public void printGameStateStats() {
        File f = null;
        PrintWriter pw = null;
        try {
            f = new File("/Users/david1/IdeaProjects/JourneyOfSamsara/src/gamestates" + generation + ".txt");
            pw = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            System.err.println("Could not find gamestates" + generation + ".txt");
            System.exit(1);
        }
        for (int i = 0; i < GameState.TOTAL_STATES; i++) {
            pw.println(dictionary[i]);
        }
        pw.close();
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public boolean isEnableLearning() {
        return enableLearning;
    }

    public void setEnableLearning(boolean learning) {
        enableLearning = learning;
    }
}

//Negative numbers mean either flipped, or going backwards
//Note that the state with index 0, which is (0, 0, 1), does not allow moving backwards