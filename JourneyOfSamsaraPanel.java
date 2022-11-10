import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class JourneyOfSamsaraPanel extends JPanel implements MouseListener, KeyListener, FocusListener{
    private JourneyOfSamsaraGame jsg;

    private byte player1Position;
    private byte player2Position;
    private boolean isPlayer1Flipped;
    private boolean isPlayer2Flipped;

    private byte player1MovementDice;       //1~6, 0 means uninitialized
    private byte player2MovementDice;
    private byte player1ClashDice;          //1~6, 0 means uninitialized
    private byte player2ClashDice;

    private boolean isDecisionReady;
    private boolean playerDecision;

    public JourneyOfSamsaraPanel(JourneyOfSamsaraGame game) {
        jsg = game;

        setBackground(Color.WHITE);

        player1Position = 0;
        player2Position = 0;
        isPlayer1Flipped = false;
        isPlayer2Flipped = false;

        player1MovementDice = 0;
        player2MovementDice = 0;
        player1ClashDice = 0;
        player2ClashDice = 0;

        isDecisionReady = false;
        playerDecision = false;

        addMouseListener(this);
        addKeyListener(this);
        addFocusListener(this);
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(500, 50, 600, 600);
        g.drawOval(575, 125, 450, 450);
        g.drawOval(650, 200, 300, 300);
        g.drawOval(725, 275, 150, 150);

        g.drawOval(725, 675, 150, 150);

        g.drawLine(500, 350, 725, 350);
        g.drawLine(557, 174, 739, 306);
        g.drawLine(707, 65, 777, 279);
        g.drawLine(893, 65, 823, 279);
        g.drawLine(1043, 174, 861, 306);
        g.drawLine(1100, 350, 875, 350);
        g.drawLine(1043, 526, 861, 394);
        g.drawLine(893, 635, 823, 421);
        g.drawLine(707, 635, 777, 421);
        g.drawLine(557, 526, 739, 394);

        g.setFont(new Font("Sans Serif", Font.BOLD, 36));

        int[][] coordinates =  {{789, 765}, {632, 577}, {539, 441}, {539, 285}, {632, 153},
                                {788, 105}, {940, 153}, {1037, 285}, {1037, 441}, {940, 577},
                                {777, 625}, {665, 515}, {596, 418}, {596, 308}, {665, 215},
                                {777, 180}, {885, 215}, {952, 308}, {952, 418}, {886, 515},
                                {777, 550}, {708, 458}, {668, 395}, {668, 331}, {708, 270},
                                {777, 255}, {844, 270}, {882, 331}, {882, 395}, {845, 458}, {777, 475}};

        for (int i = 0; i < coordinates.length; i++) {
            if (i == player1Position) {
                if (i == player2Position) {
                    g.setColor(new Color(128, 0, 255));
                } else if (isPlayer1Flipped) {
                    g.setColor(new Color(128, 128, 255));
                } else {
                    g.setColor(Color.BLUE);
                }
            } else if (i == player2Position) {
                if (isPlayer2Flipped) {
                    g.setColor(new Color(255, 128, 128));
                } else {
                    g.setColor(Color.RED);
                }
            }
            g.drawString("" + i, coordinates[i][0], coordinates[i][1]);
            g.setColor(Color.BLACK);
        }
        if (31 == player1Position) {
            g.setColor(Color.BLUE);
        } else if (31 == player2Position) {
            g.setColor(Color.RED);
        }
        g.fillPolygon(new int[]{800, 814, 848, 824, 829, 800, 771, 776, 753, 785}, new int[]{300, 330, 335, 358, 390, 375, 390, 358, 335, 330}, 10);
        g.setColor(Color.BLACK);
        /*
        g.drawString("0");

        g.drawString("5");
        g.drawString("10");
        g.drawString("15");
        g.drawString("20");
        g.drawString("25");
        g.drawString("30");

        g.drawString("21");
        g.drawString("24");
        g.drawString("26");
        g.drawString("29");

        g.drawString("22");
        g.drawString("23");
        g.drawString("27");
        g.drawString("28");

        g.drawString("11");
        g.drawString("14");
        g.drawString("16");
        g.drawString("19");

        g.drawString("12");
        g.drawString("13");
        g.drawString("17");
        g.drawString("18");

        g.drawString("1");
        g.drawString("4");
        g.drawString("6");
        g.drawString("9");

        g.drawString("2");
        g.drawString("3");
        g.drawString("7");
        g.drawString("8");*/



        drawPlayer1MovementDice(g);
        drawPlayer1ClashDice(g);
        drawPlayer2MovementDice(g);
        drawPlayer2ClashDice(g);

        g.drawString("SPACEBAR to go forwards", 180, 864);
        g.drawString("BACKSPACE to go backwards", 880, 864);
    }

    public void drawPlayer1MovementDice(Graphics g) {
        g.drawRect(50, 50, 300, 300);
        g.setColor(new Color(128, 0, 255));
        switch (player1MovementDice) {
            case 5:
                g.fillOval(75, 75, 50, 50);
                g.fillOval(275, 275, 50, 50);
            case 3:
                g.fillOval(275, 75, 50, 50);
                g.fillOval(75, 275, 50, 50);
            case 1:
                g.fillOval(175, 175, 50, 50);
                break;
            case 6:
                g.fillOval(75, 175, 50, 50);
                g.fillOval(275, 175, 50, 50);
            case 4:
                g.fillOval(275, 75, 50, 50);
                g.fillOval(75, 275, 50, 50);
            case 2:
                g.fillOval(75, 75, 50, 50);
                g.fillOval(275, 275, 50, 50);
                break;
            default:
                break;
        }
        g.setColor(Color.BLACK);
    }

    public void drawPlayer2MovementDice(Graphics g) {
        g.drawRect(1250, 50, 300, 300);
        g.setColor(new Color(128, 0, 255));
        switch (player2MovementDice) {
            case 5:
                g.fillOval(1275, 75, 50, 50);
                g.fillOval(1475, 275, 50, 50);
            case 3:
                g.fillOval(1475, 75, 50, 50);
                g.fillOval(1275, 275, 50, 50);
            case 1:
                g.fillOval(1375, 175, 50, 50);
                break;
            case 6:
                g.fillOval(1275, 175, 50, 50);
                g.fillOval(1475, 175, 50, 50);
            case 4:
                g.fillOval(1475, 75, 50, 50);
                g.fillOval(1275, 275, 50, 50);
            case 2:
                g.fillOval(1275, 75, 50, 50);
                g.fillOval(1475, 275, 50, 50);
                break;
            default:
                break;
        }
        g.setColor(Color.BLACK);
    }

    public void drawPlayer1ClashDice(Graphics g) {
        g.drawRect(50, 400, 300, 300);
        g.setColor(Color.BLUE);
        switch (player1ClashDice) {
            case 5:
                g.fillOval(75, 425, 50, 50);
                g.fillOval(275, 625, 50, 50);
            case 3:
                g.fillOval(275, 425, 50, 50);
                g.fillOval(75, 625, 50, 50);
            case 1:
                g.fillOval(175, 525, 50, 50);
                break;
            case 6:
                g.fillOval(75, 525, 50, 50);
                g.fillOval(275, 525, 50, 50);
            case 4:
                g.fillOval(275, 425, 50, 50);
                g.fillOval(75, 625, 50, 50);
            case 2:
                g.fillOval(75, 425, 50, 50);
                g.fillOval(275, 625, 50, 50);
                break;
            default:
                break;
        }
        g.setColor(Color.BLACK);
    }

    public void drawPlayer2ClashDice(Graphics g) {
        g.drawRect(1250, 400, 300, 300);
        g.setColor(Color.RED);
        switch (player2ClashDice) {
            case 5:
                g.fillOval(1275, 425, 50, 50);
                g.fillOval(1475, 625, 50, 50);
            case 3:
                g.fillOval(1475, 425, 50, 50);
                g.fillOval(1275, 625, 50, 50);
            case 1:
                g.fillOval(1375, 525, 50, 50);
                break;
            case 6:
                g.fillOval(1275, 525, 50, 50);
                g.fillOval(1475, 525, 50, 50);
            case 4:
                g.fillOval(1475, 425, 50, 50);
                g.fillOval(1275, 625, 50, 50);
            case 2:
                g.fillOval(1275, 425, 50, 50);
                g.fillOval(1475, 625, 50, 50);
                break;
            default:
                break;
        }
        g.setColor(Color.BLACK);
    }

    public byte getPlayer1Position() {
        return player1Position;
    }

    public void setPlayer1Position(byte player1Position) {
        this.player1Position = player1Position;
    }

    public byte getPlayer2Position() {
        return player2Position;
    }

    public void setPlayer2Position(byte player2Position) {
        this.player2Position = player2Position;
    }

    public boolean isPlayer1Flipped() {
        return isPlayer1Flipped;
    }

    public void setPlayer1Flipped(boolean player1Flipped) {
        isPlayer1Flipped = player1Flipped;
    }

    public boolean isPlayer2Flipped() {
        return isPlayer2Flipped;
    }

    public void setPlayer2Flipped(boolean player2Flipped) {
        isPlayer2Flipped = player2Flipped;
    }

    public byte getPlayer1MovementDice() {
        return player1MovementDice;
    }

    public void setPlayer1MovementDice(byte player1MovementDice) {
        this.player1MovementDice = player1MovementDice;
    }

    public byte getPlayer2MovementDice() {
        return player2MovementDice;
    }

    public void setPlayer2MovementDice(byte player2MovementDice) {
        this.player2MovementDice = player2MovementDice;
    }

    public byte getPlayer1ClashDice() {
        return player1ClashDice;
    }

    public void setPlayer1ClashDice(byte player1ClashDice) {
        this.player1ClashDice = player1ClashDice;
    }

    public byte getPlayer2ClashDice() {
        return player2ClashDice;
    }

    public void setPlayer2ClashDice(byte player2ClashDice) {
        this.player2ClashDice = player2ClashDice;
    }

    public boolean isDecisionReady() {
        return isDecisionReady;
    }

    public void setDecisionReady(boolean ready) {
        isDecisionReady = ready;
    }

    public boolean getPlayerDecision() {
        return playerDecision;
    }

    @Override
    public void focusGained(FocusEvent evt) {

    }

    @Override
    public void focusLost(FocusEvent evt) {

    }

    public void keyPressed(KeyEvent evt) {
        switch (KeyEvent.getKeyText(evt.getKeyCode())) {
            case "Backspace": case "Delete": case "⌫":
                isDecisionReady = true;
                playerDecision = true;
                break;
            case "Space": case "␣":
                isDecisionReady = true;
                playerDecision = false;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {

    }

    @Override
    public void keyTyped(KeyEvent evt) {

    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent evt) {

    }

    @Override
    public void mouseReleased(MouseEvent evt) {

    }

    @Override
    public void mouseEntered(MouseEvent evt) {

    }

    @Override
    public void mouseExited(MouseEvent evt) {

    }
}
