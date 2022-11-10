public class BackwardsPlayer extends JourneyOfSamsaraPlayer{
    public BackwardsPlayer(JourneyOfSamsaraGame game) {
        super(game);
    }

    public boolean makeMove() {         //to go backwards is true, to go forwards is false
        return getGame().canMoveBackwards();
    }
}
