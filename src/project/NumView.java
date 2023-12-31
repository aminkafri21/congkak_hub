package project;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class NumView implements MancalaView {

    private JPanel p1Pits;
    private JPanel p2Pits;
    private GameButton p1Mancala;
    private GameButton p2Mancala;
    private JPanel boardview;
    private JFrame frame;
    private JLabel currentturn;

    /**
     * constructor for NumView
     *
     * @param controller the controller of the Mancala game
     */
    public NumView(MancalaController controller) {
        boardview = new JPanel();
        p1Pits = new JPanel();
        p2Pits = new JPanel();
        p1Mancala = new GameButton(this, null, "", 100, 150, 1, 6);
        p2Mancala = new GameButton(this, null, "", 100, 150, 2, 6);

        boardview.setLayout(new BorderLayout());
        p1Pits.setLayout(new FlowLayout());
        p2Pits.setLayout(new FlowLayout());

        // fill up pits with buttons
        for (int i = 0; i < MancalaModel.PIT_SIZE; i++) {
            GameButton p1butt = new GameButton(this, controller, "", 80, 50, 1, i);
            GameButton p2butt = new GameButton(this, controller, "", 80, 50, 2, 5 - i);
            p1Pits.add(p1butt);
            p2Pits.add(p2butt);
        }

        // p1Mancala.setText();
        // p2Mancala.setText(starting_stones + "");

        p1Pits.setPreferredSize(new Dimension(500, 60));
        p2Pits.setPreferredSize(new Dimension(500, 60));

        boardview.add(p1Mancala, BorderLayout.EAST);
        boardview.add(p2Mancala, BorderLayout.WEST);
        boardview.add(p1Pits, BorderLayout.SOUTH);
        boardview.add(p2Pits, BorderLayout.NORTH);

        // make labels
        JComponent labels = new JPanel();
        labels.setLayout(new BorderLayout());
        JLabel p1pits = new JLabel("P1 Pits", JLabel.CENTER);
        p1pits.setLocation(200, 50);
        JLabel p2pits = new JLabel("P2 Pits", JLabel.CENTER);
        JLabel p1mancala = new JLabel("P1 Mancala");
        JLabel p2mancala = new JLabel("P2 Mancala");
        labels.add(p1pits, BorderLayout.SOUTH);
        labels.add(p2pits, BorderLayout.NORTH);
        labels.add(p1mancala, BorderLayout.EAST);
        labels.add(p2mancala, BorderLayout.WEST);
        currentturn = new JLabel();
        currentturn.setHorizontalAlignment(SwingConstants.CENTER);
        labels.add(currentturn, BorderLayout.CENTER);

        boardview.add(labels, BorderLayout.CENTER);

        frame = new JFrame();
        frame.setSize(700, 300);
        frame.setResizable(false);
        frame.setTitle("Simple View");
        frame.add(boardview);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        MancalaModel model = (MancalaModel) event.getSource();

        int[] p1board = model.getp1board();
        int[] p2board = model.getp2board();

        Component[] b1 = p1Pits.getComponents();
        Component[] b2 = p2Pits.getComponents();

        for (int i = 0; (i < b1.length) && (i < b2.length); i++) {
            JButton jb1 = (JButton) b1[i];
            updateButton(jb1, p1board[i]);

            JButton jb2 = (JButton) b2[i];
            // remember, p2 starts counting in reverse
            // as if they were on opposite side of table
            updateButton(jb2, p2board[(MancalaModel.PIT_SIZE - 1 - i)]);
        }

        updateButton(p1Mancala, p1board[MancalaModel.PIT_SIZE]);
        updateButton(p2Mancala, p2board[MancalaModel.PIT_SIZE]);

        if (model.isDone()) {
            if (p1board[p1board.length - 1] > p2board[p2board.length - 1]) {
                JOptionPane.showMessageDialog(boardview, "Player 1"
                        + " Wins!", "Congratulations!", 1);
            }

            else if (p1board[p1board.length - 1] > p2board[p2board.length - 1]) {
                JOptionPane.showMessageDialog(boardview, "Everyone Loses!", "Draw!", 1);
            }

            else {
                JOptionPane.showMessageDialog(boardview, "Player 2"
                        + " Wins!", "Congratulations!", 1);
            }
        }

        if (model.getCurrentPlayer() == 1)
            currentturn.setText("Currently: P1's turn.");
        else
            currentturn.setText("Currently: P2's turn.");
    }

    /**
     * method to update the View
     *
     * @param button the button
     */
    @Override
    public void updateButton(JButton button, int data) {
        button.setText(data + "");
    }

    /**
     * Gets the color of the text for a pit
     * 
     * @return the color of the text color
     */
    @Override
    public String getPitTextColor() {
        return "#000000";
    }

    /**
     * Closes the view
     */
    @Override
    public void close() {
        frame.dispose();
    }

}
