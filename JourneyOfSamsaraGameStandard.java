public class JourneyOfSamsaraGameStandard extends JourneyOfSamsaraGame{
    private final boolean PRINT_TO_CONSOLE = false;

    private JourneyOfSamsaraPlayer player1;
    private JourneyOfSamsaraPlayer player2;

    private byte player1Position;    //0~31, summit is 31
    private byte player2Position;
    private boolean isPlayer1Flipped;
    private boolean isPlayer2Flipped;
    private boolean isPlayer1Turn;  //at the start of the game it is neither player's turn
    private boolean isPlayer2Turn;

    private byte player1MovementDice;       //1~6, 0 means uninitialized
    private byte player2MovementDice;
    private byte player1ClashDice;          //1~6, 0 means uninitialized
    private byte player2ClashDice;

    private boolean isGameInProgress;
    private boolean hasPlayer1Won;
    private boolean hasPlayer2Won;

    //for GUI use
    private boolean isPlayer1MovementDiceVisible;
    private boolean isPlayer2MovementDiceVisible;
    private boolean isPlayer1ClashDiceVisible;
    private boolean isPlayer2ClashDiceVisible;

    private int player1Wins;
    private int player2Wins;
    private int firstPlayerWins;
    private int secondPlayerWins;
    private boolean reversedPlayers;

    public JourneyOfSamsaraGameStandard() {     //defaults to two SimpletonPlayers
        player1 = new SimpletonPlayer(this);
        player2 = new SimpletonPlayer(this);
        player1Wins = 0;
        player2Wins = 0;
        firstPlayerWins = 0;
        secondPlayerWins = 0;
        reversedPlayers = false;
        initializeGame();
    }

    public JourneyOfSamsaraGameStandard(boolean customPlayers) {
        this();
        if (customPlayers) {
            player1 = new RandomPlayer(this);
            player2 = new SimpletonPlayer(this);
        }
    }

    private void initializeGame() {
        player1Position = 0;
        player2Position = 0;
        isPlayer1Flipped = false;
        isPlayer2Flipped = false;
        isPlayer1Turn = false;
        isPlayer2Turn = false;

        player1MovementDice = 0;
        player2MovementDice = 0;
        player1ClashDice = 0;
        player2ClashDice = 0;

        isGameInProgress = false;
        hasPlayer1Won = false;
        hasPlayer2Won = false;

        isPlayer1MovementDiceVisible = false;
        isPlayer2MovementDiceVisible = false;
        isPlayer1ClashDiceVisible = false;
        isPlayer2ClashDiceVisible = false;
    }

    public void playGame() {
        initializeGame();
        startingRolloff();
        while (isGameInProgress) {
            takeTurn();
        }

        if (hasPlayer1Won) {
            player1Wins++;
            if (reversedPlayers) {
                secondPlayerWins++;
            } else {
                firstPlayerWins++;
            }
            if (player1 instanceof LearningPlayer) {
                ((LearningPlayer)(player1)).updateGameStateStats(true);
            }
            if (player2 instanceof LearningPlayer) {
                ((LearningPlayer)(player2)).updateGameStateStats(false);
            }
        } else if (hasPlayer2Won) {
            player2Wins++;
            if (reversedPlayers) {
                firstPlayerWins++;
            } else {
                secondPlayerWins++;
            }
            if (player1 instanceof LearningPlayer) {
                ((LearningPlayer)(player1)).updateGameStateStats(false);
            }
            if (player2 instanceof LearningPlayer) {
                ((LearningPlayer)(player2)).updateGameStateStats(true);
            }
        }
    }

    private void startingRolloff() {
        if (isGameInProgress) {
            throw new IllegalStateException("Cannot rolloff, game is in progress!");
        }

        if (PRINT_TO_CONSOLE) {
            System.out.println("Now rolling off to determine who will start!");
        }
        do {
            player1ClashDice = (byte) (Math.random() * 6 + 1);
            isPlayer1ClashDiceVisible = true;
            player2ClashDice = (byte) (Math.random() * 6 + 1);
            isPlayer2ClashDiceVisible = true;
            if (PRINT_TO_CONSOLE) {
                System.out.println("Player 1 rolled " + player1ClashDice + ", Player 2 rolled " + player2ClashDice);
            }
            updateGUI();
        } while (player1ClashDice == player2ClashDice);

        if (player1ClashDice > player2ClashDice) {
            isPlayer1Turn = true;
            if (PRINT_TO_CONSOLE) {
                System.out.println("Looks like Player 1 will start!");
            }
            reversedPlayers = false;
        } else {
            isPlayer2Turn = true;
            if (PRINT_TO_CONSOLE) {
                System.out.println("Looks like Player 2 will start!");
            }
            reversedPlayers = true;
        }
        isGameInProgress = true;
        if (PRINT_TO_CONSOLE) {
            System.out.println("The game has begun!");
        }
        isPlayer1ClashDiceVisible = false;
        isPlayer2ClashDiceVisible = false;
    }

    private void takeTurn() {
        if (!isGameInProgress) {
            throw new IllegalStateException("Cannot take turn, game is not in progress!");
        }
        if (isPlayer1Turn == isPlayer2Turn) {
            throw new IllegalStateException("It must be one, but not both players' turn!");
        }

        if (isPlayer1Turn) {
            player1MovementDice = (byte) (Math.random() * 6 + 1);
            isPlayer1MovementDiceVisible = true;
            if (PRINT_TO_CONSOLE) {
                System.out.println("Player 1 rolled " + player1MovementDice);
            }
            updateGUI();

            resolveMove(player1.makeMove());
            if (checkVictory()) {
                return;
            }
            if (PRINT_TO_CONSOLE) {
                System.out.println("Current Game State: (" + (isPlayer1Flipped ? -player1Position : player1Position) + ", " + (isPlayer2Flipped ? -player2Position : player2Position) + ")");
            }
            if (resolveClash()) {
                if (PRINT_TO_CONSOLE) {
                    System.out.println("Current Game State: (" + (isPlayer1Flipped ? -player1Position : player1Position) + ", " + (isPlayer2Flipped ? -player2Position : player2Position) + ")");
                }
            }
            updateGUI();
            isPlayer1MovementDiceVisible = false;
        } else {
            player2MovementDice = (byte) (Math.random() * 6 + 1);
            isPlayer2MovementDiceVisible = true;
            if (PRINT_TO_CONSOLE) {
                System.out.println("Player 2 rolled " + player2MovementDice);
            }
            updateGUI();

            resolveMove(player2.makeMove());
            if (checkVictory()) {
                return;
            }
            if (PRINT_TO_CONSOLE) {
                System.out.println("Current Game State: (" + (isPlayer1Flipped ? -player1Position : player1Position) + ", " + (isPlayer2Flipped ? -player2Position : player2Position) + ")");
            }
            if (resolveClash()) {
                if (PRINT_TO_CONSOLE) {
                    System.out.println("Current Game State: (" + (isPlayer1Flipped ? -player1Position : player1Position) + ", " + (isPlayer2Flipped ? -player2Position : player2Position) + ")");
                }
            }
            updateGUI();
            isPlayer2MovementDiceVisible = false;
        }
        isPlayer1Turn = !isPlayer1Turn;
        isPlayer2Turn = !isPlayer2Turn;
    }

    private void resolveMove(boolean isMovingBackwards) {
        if (!isGameInProgress) {
            throw new IllegalStateException("Cannot resolve move, game is not in progress!");
        }
        if (isPlayer1Turn == isPlayer2Turn) {
            throw new IllegalStateException("It must be one, but not both players' turn!");
        }

        if (isPlayer1Turn) {
            if (isMovingBackwards) {
                if (!canMoveBackwardsStatic(player1Position, player1MovementDice, isPlayer1Flipped)) {
                    throw new AssertionError("Sum Ding Wong");
                }
                if (!isPlayer1Flipped && player1MovementDice == player1Position % 10) {
                    player1Position -= player1MovementDice;
                    isPlayer1Flipped = player1Position != 0;
                } else if (player1Position % 10 == 0) {             //BUG from missing this clause, causing 10 to move 1 backwards to 19 etc.
                    player1Position -= player1MovementDice;
                } else {
                    player1Position = (byte) (player1Position / 10 * 10 + (player1Position - player1MovementDice + 9) % 10 + 1);
                }
            } else {
                if (player1MovementDice == 6) {
                    /*if (player1Position % 10 == 0) {
                        player1Position += 10;
                        isPlayer1Flipped = false;
                    } else*/ if (player1Position != 0) {
                        isPlayer1Flipped = true;
                    }
                } else {
                    if (player1Position % 10 == 0) {
                        player1Position += player1MovementDice;
                        isPlayer1Flipped = false;
                    } else {
                        if (player1Position % 10 + player1MovementDice > 10) {
                            isPlayer1Flipped = true;
                        }
                        player1Position = (byte) (player1Position / 10 * 10 + (player1Position + player1MovementDice + 9) % 10 + 1);
                    }
                }
                if (player1Position >= 32) {
                    player1Position = 31;
                }
            }
        } else {
            if (isMovingBackwards) {
                if (!canMoveBackwardsStatic(player2Position, player2MovementDice, isPlayer2Flipped)) {
                    throw new AssertionError("Sum Ding Wong");
                }
                if (!isPlayer2Flipped && player2MovementDice == player2Position % 10) {
                    player2Position -= player2MovementDice;
                    isPlayer2Flipped = player2Position != 0;
                } else if (player2Position % 10 == 0) {             //BUG from missing this clause, causing 10 to move 1 backwards to 19 etc.
                    player2Position -= player2MovementDice;
                } else {
                    player2Position = (byte) (player2Position / 10 * 10 + (player2Position - player2MovementDice + 9) % 10 + 1);
                }
            } else {
                if (player2MovementDice == 6) {
                    /*if (player2Position % 10 == 0) {
                        player2Position += 10;
                        isPlayer2Flipped = false;
                    } else*/ if (player2Position != 0) {
                        isPlayer2Flipped = true;
                    }
                } else {
                    if (player2Position % 10 == 0) {
                        player2Position += player2MovementDice;
                        isPlayer2Flipped = false;
                    } else {
                        if (player2Position % 10 + player2MovementDice > 10) {
                            isPlayer2Flipped = true;
                        }
                        player2Position = (byte) (player2Position / 10 * 10 + (player2Position + player2MovementDice + 9) % 10 + 1);
                    }
                }
                if (player2Position >= 32) {
                    player2Position = 31;
                }
            }
        }
        updateGUI();
    }

    private boolean checkVictory() {
        if (!isGameInProgress) {
            throw new IllegalStateException("Cannot check victory, game is not in progress!");
        }
        if (isPlayer1Turn == isPlayer2Turn) {
            throw new IllegalStateException("It must be one, but not both players' turn!");
        }

        if (isPlayer1Turn) {
            if (player1Position == 31) {
                hasPlayer1Won = true;
                isGameInProgress = false;
                if (PRINT_TO_CONSOLE) {
                    System.out.println("Player 1 reached 31 and won!");
                }
                return true;
            }
        } else {
            if (player2Position == 31) {
                hasPlayer2Won = true;
                isGameInProgress = false;
                if (PRINT_TO_CONSOLE) {
                    System.out.println("Player 2 reached 31 and won!");
                }
                return true;
            }
        }
        return false;
    }

    private boolean resolveClash() {
        if (!isGameInProgress) {
            throw new IllegalStateException("Cannot resolve clash, game is not in progress!");
        }
        if (isPlayer1Turn == isPlayer2Turn) {
            throw new IllegalStateException("It must be one, but not both players' turn!");
        }

        if (player1Position != 0 && player2Position != 0 && player1Position % 10 == player2Position % 10) {
            if (PRINT_TO_CONSOLE) {
                System.out.println("CLASH!");
            }
            if (isPlayer1Turn) {
                player1ClashDice = (byte) (Math.random() * 6 + 1);
                isPlayer1ClashDiceVisible = true;
                player2ClashDice = (byte) (Math.random() * 6 + 1);
                isPlayer2ClashDiceVisible = true;
                if (PRINT_TO_CONSOLE) {
                    System.out.println("Player 1 rolled " + player1ClashDice + ", Player 2 rolled " + player2ClashDice);
                }
                updateGUI();
                if (player1ClashDice >= player2ClashDice) {             //mover wins ties, for 7/12 probability of win
                    player2Position = 0;
                    isPlayer2Flipped = false;
                    if (PRINT_TO_CONSOLE) {
                        System.out.println("Player 1 won the clash!");
                    }
                } else {
                    player1Position = 0;
                    isPlayer1Flipped = false;
                    if (PRINT_TO_CONSOLE) {
                        System.out.println("Player 2 won the clash!");
                    }
                }
            } else {
                player2ClashDice = (byte) (Math.random() * 6 + 1);
                isPlayer2ClashDiceVisible = true;
                player1ClashDice = (byte) (Math.random() * 6 + 1);
                isPlayer1ClashDiceVisible = true;
                if (PRINT_TO_CONSOLE) {
                    System.out.println("Player 2 rolled " + player2ClashDice + ", Player 1 rolled " + player1ClashDice);
                }
                updateGUI();
                if (player2ClashDice >= player1ClashDice) {             //mover wins ties, for 7/12 probability of win
                    player1Position = 0;
                    isPlayer1Flipped = false;
                    if (PRINT_TO_CONSOLE) {
                        System.out.println("Player 2 won the clash!");
                    }
                } else {
                    player2Position = 0;
                    isPlayer2Flipped = false;
                    if (PRINT_TO_CONSOLE) {
                        System.out.println("Player 1 won the clash!");
                    }
                }
            }
            updateGUI();
            isPlayer1ClashDiceVisible = false;
            isPlayer2ClashDiceVisible = false;
            return true;
        }
        updateGUI();
        isPlayer1ClashDiceVisible = false;
        isPlayer2ClashDiceVisible = false;
        return false;
    }

    public static boolean canMoveBackwardsStatic(byte position, byte movementDice, boolean isFlipped) {
        if (movementDice < 1 || 6 < movementDice) {
            throw new IllegalArgumentException("Invalid dice!");
        }

        if (position == 0 || movementDice == 5 || movementDice == 6) {
            return false;
        } else if (isFlipped) {
            return true;
        } else {
            return movementDice <= (position - 1) % 10 + 1;
        }
    }

    public boolean canMoveBackwards() {
        if (!isGameInProgress) {
            throw new IllegalStateException("Cannot check moving backwards, game is not in progress!");
        }
        if (isPlayer1Turn == isPlayer2Turn) {
            throw new IllegalStateException("It must be one, but not both players' turn!");
        }

        if (isPlayer1Turn) {
            return canMoveBackwardsStatic(player1Position, player1MovementDice, isPlayer1Flipped);
        } else {
            return canMoveBackwardsStatic(player2Position, player2MovementDice, isPlayer2Flipped);
        }
    }

    public JourneyOfSamsaraPlayer getPlayer1() {
        return player1;
    }

    public JourneyOfSamsaraPlayer getPlayer2() {
        return player2;
    }

    public void setPlayer1(JourneyOfSamsaraPlayer player) {
        player1 = player;
    }

    public void setPlayer2(JourneyOfSamsaraPlayer player) {
        player2 = player;
    }

    public int getPlayer1Wins() {
        return player1Wins;
    }

    public int getPlayer2Wins() {
        return player2Wins;
    }

    public int getFirstPlayerWins() {
        return firstPlayerWins;
    }

    public int getSecondPlayerWins() {
        return secondPlayerWins;
    }

    public GameState extractCurrentGameState() {
        if (!isGameInProgress) {
            throw new IllegalStateException("Cannot extract game state, game is not in progress!");
        }
        if (isPlayer1Turn == isPlayer2Turn) {
            throw new IllegalStateException("It must be one, but not both players' turn!");
        }

        if (isPlayer1Turn) {
            return new GameState((isPlayer1Flipped ? -player1Position : player1Position), (isPlayer2Flipped ? -player2Position : player2Position), player1MovementDice);
        } else {
            return new GameState((isPlayer2Flipped ? -player2Position : player2Position), (isPlayer1Flipped ? -player1Position : player1Position), player2MovementDice);
        }
    }

    public void resetWins() {
        player1Wins = 0;
        player2Wins = 0;
        firstPlayerWins = 0;
        secondPlayerWins = 0;
        reversedPlayers = false;
    }

    public byte getPlayer1Position() {
        return player1Position;
    }

    public byte getPlayer2Position() {
        return player2Position;
    }

    public boolean isPlayer1Flipped() {
        return isPlayer1Flipped;
    }

    public boolean isPlayer2Flipped() {
        return isPlayer2Flipped;
    }

    public byte getPlayer1MovementDice() {
        return player1MovementDice;
    }

    public byte getPlayer2MovementDice() {
        return player2MovementDice;
    }

    public byte getPlayer1ClashDice() {
        return player1ClashDice;
    }

    public byte getPlayer2ClashDice() {
        return player2ClashDice;
    }

    public boolean isPlayer1MovementDiceVisible() {
        return isPlayer1MovementDiceVisible;
    }

    public boolean isPlayer2MovementDiceVisible() {
        return isPlayer2MovementDiceVisible;
    }

    public boolean isPlayer1ClashDiceVisible() {
        return isPlayer1ClashDiceVisible;
    }

    public boolean isPlayer2ClashDiceVisible() {
        return isPlayer2ClashDiceVisible;
    }

    public void updateGUI() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {

        }
        if (player1 instanceof HumanPlayer) {
            ((HumanPlayer)(player1)).update();
        } else if (player2 instanceof HumanPlayer) {
            ((HumanPlayer)(player2)).update();
        }
    }
}
