import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame {

    GameModel model;
    JPanel bottomPanel;
    JPanel topPanel;
    JPanel boardPanel;
    JLabel titleLabel;
    JLabel statusLabel;
    JButton startButton;
    JButton[][] buttons;

    public GameView() {
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

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        topPanel.setLayout(new FlowLayout());
        topPanel.add(startButton);

        boardPanel.setLayout(new GridLayout(4, 4));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton("[" + i + "," + j + "]");
                buttons[i][j].addActionListener(new TileButtonListener(i ,j));
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
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        updateView();
    }

    private boolean hasPlayed = false;

    public void updateView() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                int value = model.getValue (i, j);

                if(value == 0){
                    buttons[i][j].setText("");
                    buttons[i][j].setBackground(Color.WHITE);
                    buttons[i][j].setEnabled(false);
                }
                else{
                    buttons[i][j].setText(String.valueOf(value));
                    buttons[i][j].setBackground(null);
                    buttons[i][j].setEnabled(true);
                }
            }
        }
        if (hasPlayed && model.isSolved()){
            statusLabel.setText("DU VANN!!!");
        }
        else{
            statusLabel.setText("");
        }
    }

    public boolean handleClick(int row, int col) {
        if (model.isSolved()) return false;

        hasPlayed = true;
        boolean moved = model.move(row, col);
        updateView();
        return moved;

    }

    public void resetGame() {
        hasPlayed = false;
        model.shuffle();
        updateView();
    }

    private class TileButtonListener implements ActionListener {
        int row;
        int col;
        public TileButtonListener(int row, int col) {
            this.row = row;
            this.col = col;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            handleClick(row, col);
        }
    }
}
