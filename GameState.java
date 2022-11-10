public class GameState {
    public static final int TOTAL_STATES = 20166;

    private byte playerPosition;
    private byte opponentPosition;
    private boolean isPlayerFlipped;
    private boolean isOpponentFlipped;
    private byte movementDice;

    public GameState() {
        playerPosition = 0;
        opponentPosition = 0;
        isOpponentFlipped = false;
        isOpponentFlipped = false;
        movementDice = 0;       //this is not legal
    }

    public GameState(byte pp, byte op, boolean pf, boolean of, byte md) {
        playerPosition = pp;
        opponentPosition = op;
        isPlayerFlipped = pf;
        isOpponentFlipped = of;
        movementDice = md;
    }

    public GameState(int player, int opponent, byte md) {
        playerPosition = (byte)(Math.abs(player));
        opponentPosition = (byte)(Math.abs(opponent));
        isPlayerFlipped = player < 0;
        isOpponentFlipped = opponent < 0;
        movementDice = md;
    }

    public boolean equals(Object obj) {
        if (obj instanceof GameState) {
            GameState other = (GameState)(obj);
            return playerPosition == other.playerPosition && opponentPosition == other.opponentPosition &&
                    isPlayerFlipped == other.isPlayerFlipped && isOpponentFlipped == other.isOpponentFlipped &&
                    movementDice == other.movementDice;
        } else {
            return false;
        }
    }

    public String toString() {
        return (isPlayerFlipped ? -playerPosition : playerPosition) + " " + (isOpponentFlipped ? -opponentPosition : opponentPosition) + " " + movementDice;
    }

    public int convertToIndex() {
        if (playerPosition == 0 && opponentPosition == 0) {
            return movementDice - 1;
        }
        if (playerPosition == 0) {
            return opponentPosition * 12 + movementDice - 1 + (isOpponentFlipped ? 0 : -6);
        }
        if (opponentPosition == 0) {
            return 360 + playerPosition * 12 + movementDice - 1 + (isPlayerFlipped ? 0 : -6);
        }
        return 54 + playerPosition * 648 + base9dropdown(playerPosition, opponentPosition) * 24 +
                (isPlayerFlipped ? 12 : 0) + (isOpponentFlipped ? 6 : 0) + movementDice - 1;
    }

    private int base9dropdown(int pp, int op) {
        int newop = op;
        if (op > (pp - 1) % 10 + 1) {
            newop--;
        }
        if (op > 10 + (pp - 1) % 10 + 1) {
            newop--;
        }
        if (op > 20 + (pp - 1) % 10 + 1) {
            newop--;
        }
        return newop;
    }
}
