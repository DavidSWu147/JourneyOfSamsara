public abstract class JourneyOfSamsaraPlayer {
    private JourneyOfSamsaraGame jsg;

    //tbh you can just pass in the game state as parameters, you don't need the game

    public JourneyOfSamsaraPlayer(JourneyOfSamsaraGame game) {
        jsg = game;
    }

    public abstract boolean makeMove();         //to go backwards is true, to go forwards is false

    public JourneyOfSamsaraGame getGame() {
        return jsg;
    }
}
