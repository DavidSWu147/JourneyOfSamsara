public class HumanPlayer extends JourneyOfSamsaraPlayer {
    private JourneyOfSamsaraFrame jsf;

    public HumanPlayer(JourneyOfSamsaraGame game, JourneyOfSamsaraFrame frame) {
        super(game);
        jsf = frame;
    }

    @Override
    public boolean makeMove() {
        while (! jsf.getPanel().isDecisionReady()) {
            System.out.print("");
        }
        jsf.getPanel().setDecisionReady(false);
        return getGame().canMoveBackwards() && jsf.getPanel().getPlayerDecision();
    }

    public void update() {
        jsf.update();
    }
}
