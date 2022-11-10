public class SimpletonPlayer extends JourneyOfSamsaraPlayer{
    public SimpletonPlayer(JourneyOfSamsaraGame game) {
        super(game);
    }

    public boolean makeMove() {         //to go backwards is true, to go forwards is false
        return false;
    }
}
