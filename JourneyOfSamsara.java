public class JourneyOfSamsara {
    private JourneyOfSamsaraFrame jsf;
    private JourneyOfSamsaraGame jsg;

    public JourneyOfSamsara() {
        jsg = new JourneyOfSamsaraGameStandard(true); //jsg = new JourneyOfSamsaraGame();
        jsf = new JourneyOfSamsaraFrame(jsg);
    }

    public static void main(String[] args) {
        JourneyOfSamsara js = new JourneyOfSamsara();
        js.run();
    }

    public void run() {

        jsf.run();
        jsg.setPlayer1(new HumanPlayer(jsg, jsf));
        JourneyOfSamsaraPlayer newPlayer = new LearningPlayer(jsg, 10);
        jsg.setPlayer2(newPlayer);
        ((LearningPlayer)(jsg.getPlayer2())).setSensitivity(1000.0);
        ((LearningPlayer)(jsg.getPlayer2())).setEnableLearning(false);
        jsg.playGame();
        /*
        for (int generation = 1; generation <= 10; generation++) {
            System.out.println("Now on generation " + generation + "!");

            jsg.setPlayer2(jsg.getPlayer1());
            JourneyOfSamsaraPlayer newPlayer = new LearningPlayer(jsg, generation);
            jsg.setPlayer1(newPlayer);

            for (double sensitivity = 0.0; sensitivity < 10.0; sensitivity += 1.0) {
                System.out.println("Now on sensitivity " + sensitivity + "!");

                ((LearningPlayer)(jsg.getPlayer1())).setSensitivity(sensitivity);
                for (int i = 0; i < 10000000; i++) {
                    jsg.playGame();
                }
                System.out.println("Player 1 won " + jsg.getPlayer1Wins() + " games and Player 2 won " + jsg.getPlayer2Wins() + " games");
                jsg.resetWins();
            }

            ((LearningPlayer)(jsg.getPlayer1())).printGameStateStats();
            ((LearningPlayer)(jsg.getPlayer1())).setSensitivity(1000.0);
            ((LearningPlayer)(jsg.getPlayer1())).setEnableLearning(false);
        }*/

        //System.out.println("The first player won " + jsg.getFirstPlayerWins() + " games and the second player won " + jsg.getSecondPlayerWins() + " games");
    }
}
