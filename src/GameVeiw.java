import javax.swing.*;
import java.awt.*;

public class GameVeiw extends JFrame {

    GameModel model;
    JPanel bottomPanel;
    JPanel topPanel;
    JPanel boardPanel;
    JLabel titleLabel;
    JLabel statusLabel;
    JButton startButton;
    JButton[][] buttons;

    public GameVeiw() {
        model = new GameModel();

        int row = 4;
        int col = 4;


        topPanel = new JPanel();
        bottomPanel = new JPanel();
        boardPanel = new JPanel();
        titleLabel = new JLabel("15");
        statusLabel = new JLabel();
        buttons = new JButton[row][col];
        startButton = new JButton("Nytt Spel");

        topPanel.setLayout(new FlowLayout());
        topPanel.add(titleLabel);
        topPanel.add(startButton);

        boardPanel.setLayout(new GridLayout(4, 4));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton("[" + i + "," + j + "]");
                boardPanel.add(buttons[i][j]);
            }
        }


        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(statusLabel);



        updateView();
    }

    public void updateView() {
    }

}
