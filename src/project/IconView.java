package project;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

/**
 * this class is to create an icon view for mancala game
 *
 * @author lamlu, Matthew Somers
 *
 */
public class IconView implements MancalaView {

    private JPanel pits;// the panel to contain both p1Pits and p2Pits
    private JPanel pit1;
    private JPanel pit2;
    private JLabel turn;
    private JButton p1Mancala;
    private JButton p2Mancala;
    private JPanel boardview;
    private JFrame frame;
    private String messageStart = "    Current Turn: ";

    /**
     * constructor for IconView
     *
     * @param controller the controller of the Mancala game
     */
    public IconView(MancalaController controller) {

        boardview = new JPanel();
        pits = new JPanel(new BorderLayout());
        pit1 = new JPanel(new GridLayout(0, 6));
        pit1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        pit2 = new JPanel(new GridLayout(0, 6));
        pit2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        p1Mancala = new GameButton(this, null, "images/greenmacala.png", 0, 0, 1, 6);
        p2Mancala = new GameButton(this, null, "images/bluemacala.png", 0, 0, 2, 6);
        turn = new JLabel(messageStart);
        turn.setHorizontalAlignment(SwingConstants.CENTER);

        boardview.setLayout(new BorderLayout());

        // fill up pits with buttons
        // pit1
        for (int i = 0; i < MancalaModel.PIT_SIZE; i++) {
            JButton p1butt = new GameButton(this, controller, "images/greenround.png", 80, 80, 1, i);
            pit1.add(p1butt);
        }

        // pit2
        for (int i = 0; i < MancalaModel.PIT_SIZE; i++) {
            JButton p2butt = new GameButton(this, controller, "images/blueround.png", 80, 80, 2, i);
            pit2.add(p2butt);
        }

        // p1Mancala.setText();
        // p2Mancala.setText(starting_stones + "");

        p1Mancala.setPreferredSize(new Dimension(100, 120));
        p2Mancala.setPreferredSize(new Dimension(100, 120));

        pits.add(pit1, BorderLayout.SOUTH);
        pits.add(pit2, BorderLayout.NORTH);
        pits.add(turn, BorderLayout.CENTER);

        boardview.add(p1Mancala, BorderLayout.LINE_END);

        boardview.add(pits, BorderLayout.CENTER);
        boardview.add(p2Mancala, BorderLayout.LINE_START);

        frame = new JFrame();
        frame.setSize(700, 300);
        frame.setResizable(false);
        frame.setTitle("Icon View");
        frame.add(boardview);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * the method to be called when the state of the game is changed
     *
     * @param event the model that called for this view to change
     */
    @Override
    public void stateChanged(ChangeEvent event) {
        MancalaModel model = (MancalaModel) event.getSource();
        int[] p1board = model.getp1board();
        int[] p2board = model.getp2board();
        Component[] pit1Components = pit1.getComponents();
        Component[] pit2Components = pit2.getComponents();
        if (model.getCurrentPlayer() == 2)
            turn.setText(messageStart + "Blue");
        else
            turn.setText(messageStart + "Green");

        // pits 1
        for (int i = 0; i < 6; i++) {
            JButton jb1 = (JButton) pit1Components[i];
            updateButton(jb1, p1board[i]);
        }

        // pits 2
        for (int i = 0; i < 6; i++) {
            JButton jb2 = (JButton) pit2Components[i];
            updateButton(jb2, p2board[i]);
        }
        updateButton(p1Mancala, p1board[MancalaModel.PIT_SIZE]);
        updateButton(p2Mancala, p2board[MancalaModel.PIT_SIZE]);

        if (model.isDone()) {
            if (p1board[p1board.length - 1] > p2board[p2board.length - 1]) {
                JOptionPane.showMessageDialog(pits, "Green"
                        + " Wins!", "Congratulations!", 1);
            }

            else if (p1board[p1board.length - 1] > p2board[p2board.length - 1]) {
                JOptionPane.showMessageDialog(boardview, "Everyone Loses!", "Draw!", 1);
            }

            else {
                JOptionPane.showMessageDialog(boardview, "Blue"
                        + " Wins!", "Congratulations!", 1);
            }
        }
    }

    /**
     * method to update the View
     *
     * @param button the button to be updated
     * @param data   the data to be represented on the button
     */
    @Override
    public void updateButton(JButton button, int data) {
        String s = "";
        int count = 0;
        for (int i = 0; i < data; i++) {
            s += "o";
            count++;
            if (count >= 10) {
                s = "O" + "x" + count;
            }
        }
        String htmlString = "<html> <font color=" + getPitTextColor() + ">" + s + "</font>";
        button.setText(htmlString);

        button.setBorder(null);
    }

    /**
     * Gets the color for text
     * 
     * @return the color of the text
     */
    @Override
    public String getPitTextColor() {
        return "#FFFF00";
    }

    /**
     * Closes the frame
     */
    @Override
    public void close() {
        frame.dispose();
    }
}
