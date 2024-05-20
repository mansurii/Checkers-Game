package model;

/**
 * Thus class represents the 8x8 board in the Checkers game.
 */
public class Board  {
    private final int rows;
    private final int cols;
    private final Square[][] board; //array of square to keep track of the square colours placed on the board

    private static final String SPACE = "     ";
    private static final String ROW_LINE = "---+---+---+---+---+---+---+---+";
    private static final String[] NUMBERED_BOARD = {" 00 | 01| 02| 03| 04| 05| 06| 07", "---+---+---+---+---+---+---+---+", " 08 | 09| 10| 11| 12| 13| 14| 15", "---+---+---+---+---+---+---+---+", " 16 | 17| 18| 19| 20| 21| 22| 23 ", "---+---+---+---+---+---+---+---+", " 24 | 25| 26| 27| 28| 29| 30| 31 ", "---+---+---+---+---+---+---+---+", " 32 | 33| 34| 35| 36| 37| 38| 39 ", "---+---+---+---+---+---+---+---+", " 40 | 41| 42| 43| 44| 45| 46| 47 ", "---+---+---+---+---+---+---+---+", " 48 | 49| 50| 51| 52| 53| 54| 55 ", "---+---+---+---+---+---+---+---+"," 56 | 57| 58| 59| 60| 61| 62| 63 "};

    /**
     * Constructor to initialize the board. It's putting an empty square at the whole board at first.
     * Then it calls the method to place squares according to players colour.
     * @param rows the rows of the board to be created
     * @param cols the columns of the board to be created
     */
    public Board (final int rows, final int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new Square[rows][cols];
        for(int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.board[i][j] = Square.EMPTY;

            }
        }
        setStartingPositionsDown();
        setStartingPositionsUp();
    }

    /**
     * This method sets at the top three rows the blue squares for player1.
     */
    private void setStartingPositionsUp() {
        for(int i = 0; i <= rows-6; i++) { //for the first 3 rows (0..2)
            for (int j = 0; j < cols; j++) {
                if (i == 1 && j % 2 == 0) { //for when i am in the second row  (counting from top)
                    this.board[i][j] = Square.BLUE;
                } else if (i != 1 && j % 2 != 0){ //for the first and the third row
                    this.board[i][j] = Square.BLUE;
                }
            }
        }
    }

    /**
     * This method sets at the bottom three rows the blue squares for player1.
     */
    private void setStartingPositionsDown() {
        for(int i = rows-3; i <= rows-1; i++) { //for the last three rows (5..7)
            for (int j = 0; j < cols; j++) {
                if (i == 6 && j % 2 != 0) {
                    this.board[i][j] = Square.RED;
                } else if (i != 6 && j % 2 == 0){
                    this.board[i][j] = Square.RED;
                }
            }
        }
    }

    /**
     * This method is called when we want to place a square on the board.
     * @param row the row to place the square on.
     * @param col the col to place the square on.
     * @param colour the colour of the square.
     */
    public void setSquareOnBoard(int row, int col, Square colour){
        if (getContent(row, col).equals(Square.EMPTY)) {
            board[row][col] = colour;
        }
    }
    /**
     * This method is called when we want to remove a square from the board.
     * @param row the row to remove the square from.
     * @param col the col to remove the square from.
     */
    public void removeSquareFromBoard(int row, int col) {
        board[row][col] = Square.EMPTY;
    }

    /**
     * Method to get the content of a field (square) from the board.
     * @param row the row of the board.
     * @param col the column of the board.
     * @return the square colour.
     */
    public Square getContent(int row, int col) {
        return board[row][col];
    }

    /**
     * Method to get the board on the terminal with the squares.
     * @return a string representation of the board.
     */
    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            boardString.append(buildRowString(row));
            boardString.append(SPACE).append(NUMBERED_BOARD[row * 2]);

            if (row < rows - 1) {
                boardString.append("\n").append(ROW_LINE).append(SPACE).append(NUMBERED_BOARD[row * 2 + 1]).append("\n");
            }
        }
        return boardString.toString();
    }

    /**
     * Helper method to build a row of the board.
     * @param row the row to build
     * @return a string representation of the row of the board.
     */
    private String buildRowString(int row) {
        StringBuilder rowString = new StringBuilder();
        for (int col = 0; col < getCols(); col++) {
            rowString.append(buildCellString(getContent(row, col)));

            if (col < getCols() - 1) {
                rowString.append("|");
            }
        }
        return rowString.toString();
    }

    /**
     * Helper method to build a column of the board.
     * @param square the column to build
     * @return a string representation of the column of the board.
     */
    private String buildCellString(Square square) {
        if (square.equals(Square.RED)) {
            return " \u001B[31m" + square.toString().charAt(0) + "\u001B[0m ";
        } else if (square.equals(Square.BLUE)) {
            return " \u001B[36m" + square.toString().charAt(0) + "\u001B[0m ";
        } else if (square.equals(Square.KINGBLUE)) {
            return " \u001B[36m" + square.toString().charAt(0)+ "\u001B[0m ";
        } else if (square.equals(Square.KINGRED)) {
            return " \u001B[31m" + square.toString().charAt(0) + "\u001B[0m ";
        } else {
            return "   ";
        }
    }

    /**
     * Method to check if a move to be made is in bounds of the board.
     * @param row the row entered.
     * @param col the column entered.
     * @return if the move is in bounds return true, false otherwise.
     */
    public boolean isInBounds(int row, int col){
        return row>=0 && row<rows && col>=0 && col<cols;
    }

    /**
     * Gets the rows of the board.
     * @return the rows of the board.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the columns of the board.
     * @return the columns of the board.
     */
    public int getCols() {
        return cols;
    }
}
