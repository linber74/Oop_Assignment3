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
            tiles.add(1);
        }
        do {
            Collections.shuffle(tiles);
        }while (!isSolvable ());

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

    public boolean isSolvable(){

    }
}
