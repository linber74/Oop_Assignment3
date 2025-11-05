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

        setLayout( new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        add(boardPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        setTitle("15");
        pack();
        setVisible(true);
        setLocation(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        updateView();
    }

    public void updateView() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                int value = model.getValue (i, j);

                if(value == 0){
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(Color.blue);
                    buttons[i][j].setEnabled(false);
                }
                else{
                    buttons[i][j].setText(String.valueOf(value));
                    buttons[i][j].setBackground(null);
                    buttons[i][j].setEnabled(true);
                }
            }
        }
        if (model.isSolved()){
            statusLabel.setText("DU VANN!!!");
        }
        else{
            statusLabel.setText("");
        }
    }

    public boolean handleClick(int row, int col) {
        boolean moved = model.move(row, col);
        updateView();
        return moved;
    }
}
