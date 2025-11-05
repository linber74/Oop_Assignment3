import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameModel {
    private int[][] board = new int[4][4];
    private int emptyRow;
    private int emptyCol;

    public GameModel() {
        initBoardSolved();

    }

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

    private static final int SIZE =4;
    private static final int TILE_COUNT = 16;

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

    // Lösbarhetsalgoritm baserad på inverser och tom ruta
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

    public boolean canMove(int row, int col) {
        if(row < 0 || row >= SIZE || col < 0 || col >= SIZE) return false;
        int rowdifference = row - emptyRow;
        int coldifference = col - emptyCol;
        return (rowdifference + coldifference == 1);
    }

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

    public int getValue(int row, int col) {
        return board[row][col];
    }
}
