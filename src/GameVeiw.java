import javax.swing.*;
import java.awt.*;

public class GameVeiw extends JFrame {

    GameModel model;
    JPanel boardPanel;
    JLabel titleLabel;
    JLabel statusLabel;
    JButton[][] buttons = new JButton[4][4];

    public GameVeiw() {
        model = new GameModel();

        boardPanel = new JPanel();
        titleLabel = new JLabel("15");
        statusLabel = new JLabel();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton();
            }
        }

        boardPanel.setLayout(new GridLayout(4, 4));

        updateView();
    }

    public void updateView() {}

}
