import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GameView bygger upp GUI:t för 15-pusslet.
 * Den visar brickorna, hanterar knapptryckningar och uppdaterar gränssnittet
 * baserat på information från modellen. Här kopplas användarens interaktion
 * till spelmekaniken.
 */

public class GameView extends JFrame {

    GameModel model;
    JPanel bottomPanel;
    JPanel topPanel;
    JPanel boardPanel;
    JLabel statusLabel;
    JButton startButton;
    JButton[][] buttons;

    /**
     * Skapar GUI:t för spelet: bygger upp rutnätet med knappar,
     * kopplar varje knapp till en lyssnare, och visar startläget från modellen.
     */
    public GameView() {
        model = new GameModel();

        int row = 4;
        int col = 4;


        topPanel = new JPanel();
        bottomPanel = new JPanel();
        boardPanel = new JPanel();
        statusLabel = new JLabel();
        buttons = new JButton[row][col];
        startButton = new JButton("Nytt Spel");

        //Sätter ActionListener till "Nytt Spel"
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        topPanel.setLayout(new FlowLayout());
        topPanel.add(startButton);

        // Skapar ett rutnät med knappar för varje bricka i spelet.
        // Varje knapp får en lyssnare som skickar sin position till modellen vid klick.
        boardPanel.setLayout(new GridLayout(row, col));
        for (int i = 0; i < GameModel.SIZE; i++) {
            for (int j = 0; j < GameModel.SIZE; j++) {
                buttons[i][j] = new JButton("[" + i + "," + j + "]"); //Temporär text för debug
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
        statusLabel.setText("Så ser en vinst bräda ut");
    }

    // Hindrar vinstmeddelande från att visas innan spelaren gjort något
    private boolean hasPlayed = false;

    // Uppdaterar GUI:t så att varje knapp visar rätt brickvärde och färg
    // Anropas efter varje drag eller när spelet startas om
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

    // Hanterar klick på en bricka – försöker flytta den och uppdaterar GUI:t
    // Visar vinstmeddelande om spelet är löst efter draget
    public boolean handleClick(int row, int col) {
        if (model.isSolved()) return false;

        hasPlayed = true;
        boolean moved = model.move(row, col);
        updateView();
        return moved;

    }

    // Startar ett nytt spel genom att blanda brickorna och uppdatera GUI:t
    public void resetGame() {
        hasPlayed = false;
        model.shuffle();
        updateView();
    }

    // Lyssnar på knapptryckningar och skickar rad/kolumn till handleClick()
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
