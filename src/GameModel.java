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
}
