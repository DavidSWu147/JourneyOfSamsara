public class GameStateStats {
    public static final int TOTAL_STATES = 20166;

    private GameState gameState;
    private int forwardsWin;
    private int forwardsLoss;
    private int backwardsWin;
    private int backwardsLoss;

    public GameStateStats(GameState gs, int fw, int fl, int bw, int bl) {
        gameState = gs;
        forwardsWin = fw;
        forwardsLoss = fl;
        backwardsWin = bw;
        backwardsLoss = bl;
    }

    public GameStateStats(int player, int opponent, byte md, int fw, int fl, int bw, int bl) {
        gameState = new GameState(player, opponent, md);
        forwardsWin = fw;
        forwardsLoss = fl;
        backwardsWin = bw;
        backwardsLoss = bl;
    }

    public String toString() {
        return gameState.toString() + " " + forwardsWin + " " + forwardsLoss + " " + backwardsWin + " " + backwardsLoss;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getForwardsWin() {
        return forwardsWin;
    }

    public void setForwardsWin(int forwardsWin) {
        this.forwardsWin = forwardsWin;
    }

    public int getForwardsLoss() {
        return forwardsLoss;
    }

    public void setForwardsLoss(int forwardsLoss) {
        this.forwardsLoss = forwardsLoss;
    }

    public int getBackwardsWin() {
        return backwardsWin;
    }

    public void setBackwardsWin(int backwardsWin) {
        this.backwardsWin = backwardsWin;
    }

    public int getBackwardsLoss() {
        return backwardsLoss;
    }

    public void setBackwardsLoss(int backwardsLoss) {
        this.backwardsLoss = backwardsLoss;
    }
}
