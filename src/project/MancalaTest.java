package project;

/**
 * Tests out the mancala game!
 */
public class MancalaTest {

    /**
     * main method
     * 
     * @param args not used
     */
    public static void main(String[] args) {
        MancalaModel model = new MancalaModel();
        MancalaController controller = new MancalaController(model);

        MenuFrame startframe = new MenuFrame(model, controller);

    }
}
