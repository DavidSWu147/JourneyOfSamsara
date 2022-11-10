public class RandomPlayer extends JourneyOfSamsaraPlayer {
    private double probability;

    public RandomPlayer(JourneyOfSamsaraGame game) {
        super(game);
        probability = 0.5;
    }

    public RandomPlayer(JourneyOfSamsaraGame game, double prob) {
        this(game);
        probability = prob;
    }

    public boolean makeMove() {         //to go backwards is true, to go forwards is false
        return getGame().canMoveBackwards() && Math.random() < probability;
    }
}
