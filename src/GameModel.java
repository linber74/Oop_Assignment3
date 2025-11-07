import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * GameModel hanterar all spelmekanik för 15-pusslet.
 * Den håller koll på brädets tillstånd, tomrutans position,
 * kontrollerar giltiga drag, blandar brickor och avgör om spelet är löst.
 * Används av vyn för att uppdatera GUI:t baserat på modellens data.
 */

public class GameModel {
    private final int rows =4;
    private final int cols =4;
    private int [][]board = new int[rows][cols];
    private int emptyRow;
    private int emptyCol;


    public GameModel() {
        initBoardSolved();
    }

    // Lägger ut brickorna i rätt ordning – används för att visa vinstläge direkt
    public void initBoardSolved() {
        board  = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 0}
        };
        emptyRow = 3;
        emptyCol = 3;
    }


    public static final int SIZE =4;
    private static final int TILE_COUNT = 16;

    //Blandar brädet slumpmässigt tills ett lösbart bräde hittas
    public void shuffle(){
        List<Integer> tiles = new ArrayList<>();
        for(int i = 0; i < TILE_COUNT; i++){
            tiles.add(i);
        }
        do {
            Collections.shuffle(tiles);
        }while (!isSolvable (tiles));

        int index =0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = tiles.get(index++);
                board[row][col] = value;
                if (value == 0){
                    emptyRow = row;
                    emptyCol = col;
                }
            }
        }
    }

    // Returnerar true om brickorna ligger i en lösbar ordning enligt 15-pusselregler
    // (baserat på antal inversioner och tomrutans position)
    // Källa: https://www.geeksforgeeks.org/dsa/check-instance-15-puzzle-solvable/
    public boolean isSolvable(List<Integer> tiles) {
        int inversions = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = i + 1; j < tiles.size(); j++) {
                int a = tiles.get(i);
                int b = tiles.get(j);
                if (a != 0 && b != 0 && a > b) inversions++;
            }
        }

        int zeroIndex = tiles.indexOf(0);
        int rowFromBottom = 3 - (zeroIndex / 4);
        return (rowFromBottom % 2 == 0) == (inversions % 2 == 0);
    }

    // Returnerar true om brickan ligger direkt intill tomrutan (endast horisontellt eller vertikalt)
    public boolean canMove(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) return false;
        int rowDiff = Math.abs(row - emptyRow);
        int colDiff = Math.abs(col - emptyCol);
        return (rowDiff + colDiff == 1);
    }

    // Flyttar brickan till tomrutan om draget är giltigt och uppdaterar tomrutans position
    public boolean move(int row, int col) {
        if (canMove(row, col)) {
            int temp = board[row][col];
            board[row][col] = board[emptyRow][emptyCol];
            board[emptyRow][emptyCol] = temp;

            emptyRow = row;
            emptyCol = col;

            return true;
        }
        return false;
    }

    // Kollar om alla brickor ligger i rätt ordning – används för att avgöra om spelaren har vunnit
    public boolean isSolved() {
        int expected = 1;
        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < SIZE; col++){
                if(row == SIZE - 1 && col == SIZE - 1){
                    if (board[row][col] != 0) return false;
                }
                else {
                    if (board[row][col] != expected) return false;
                    expected++;
                }
            }
        }
        return true;
    }

    // Returnerar värdet på brickan vid (row, col) – används av vyn för att uppdatera GUI:t
    public int getValue(int row, int col) {
        return board[row][col];
    }
}
