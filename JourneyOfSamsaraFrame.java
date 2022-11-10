import java.awt.*;
import javax.swing.*;

public class JourneyOfSamsaraFrame extends JFrame{
    private JourneyOfSamsaraPanel jsp;
    private JourneyOfSamsaraGame jsg;

    public JourneyOfSamsaraFrame(JourneyOfSamsaraGame game) {
        super("Journey of Samsara");
        setLocation(0,0);
        setSize(1600, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        jsp = new JourneyOfSamsaraPanel(jsg);
        setContentPane(jsp);
        setVisible(true);

        jsg = game;
    }

    public void run() {
        jsp.repaint();
    }

    public void update() {
        jsp.setPlayer1Position(jsg.getPlayer1Position());
        jsp.setPlayer2Position(jsg.getPlayer2Position());
        jsp.setPlayer1Flipped(jsg.isPlayer1Flipped());
        jsp.setPlayer2Flipped(jsg.isPlayer2Flipped());
        if (jsg.isPlayer1MovementDiceVisible()) {
            jsp.setPlayer1MovementDice(jsg.getPlayer1MovementDice());
        } else {
            jsp.setPlayer1MovementDice((byte)(0));
        }
        if (jsg.isPlayer2MovementDiceVisible()) {
            jsp.setPlayer2MovementDice(jsg.getPlayer2MovementDice());
        } else {
            jsp.setPlayer2MovementDice((byte)(0));
        }
        if (jsg.isPlayer1ClashDiceVisible()) {
            jsp.setPlayer1ClashDice(jsg.getPlayer1ClashDice());
        } else {
            jsp.setPlayer1ClashDice((byte)(0));
        }
        if (jsg.isPlayer2ClashDiceVisible()) {
            jsp.setPlayer2ClashDice(jsg.getPlayer2ClashDice());
        } else {
            jsp.setPlayer2ClashDice((byte)(0));
        }
        jsp.repaint();
    }

    public JourneyOfSamsaraPanel getPanel() {
        return jsp;
    }
}
