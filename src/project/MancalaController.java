package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tells buttons what to do for a mancala board.
 */
public class MancalaController implements ActionListener {

    private MancalaModel model;

    /**
     * Simple constructor.
     * 
     * @param model The model to be interacted with as per MVC.
     */
    public MancalaController(MancalaModel model) {
        this.model = model;
    }

    /**
     * The main utility that makes a move and updates button.
     * 
     * @param e The source button that was clicked.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        GameButton gb = (GameButton) e.getSource();
        model.makeMove(gb.getPlayerId(), gb.getButtonId());

        if (model.isLegalMove()) {
            String htmlString = "<html> <font color=" + gb.getView().getPitTextColor() + ">Undo?</font>";
            gb.setText(htmlString);
            if (gb.getView().getClass().equals(LuxuryView.class) ||
                    gb.getView().getClass().equals(IconView.class))
                gb.setBorder(null);

        }

    }
}
