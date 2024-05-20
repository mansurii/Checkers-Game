package model;

import model.Square;

/**
 * This class represents a move of the game.
 */
public class Move {
    //instance variables
    private final int fromRow;
    private final int fromColumn;
    private final int toRow;
    private final int toColumn;
    private final Square square;
    private int indexToFlip; //the index to flip of that move

    /**
     *
     * @param fromRow the row of the move that is going to be transferred
     * @param fromColumn the column of the move that is going to be transferred
     * @param toRow the row of the move that is going to be landed
     * @param toColumn the column of the move that is going to be landed
     * @param square the square colour of that move
     */
    public Move(int fromRow, int fromColumn, int toRow, int toColumn, Square square) {
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
        this.square = square;
        this.indexToFlip = -1; //this is default set to -1, if that move is not going to flip any disk.
    }

    /**
     * Sets the index to flip of that move representing a capturing move
     * @param indexToFlip the index of the move to flip
     */
    public void setIndexToFlip(int indexToFlip) {
        this.indexToFlip = indexToFlip;
    }

    /**
     * This method gets the index that this move will flip.
     * @return the index of the square that is going to be flipped to the other colour.
     */
    public int getIndexToFlip() {
        return indexToFlip;
    }

    /**
     * This method gets the row that this move will go from
     * @return the row that this move will go from
     */
    public int getFromRow() {
        return fromRow;
    }

    /**
     * This method gets from column that this move will go from
     * @return the column that this move will go from
     */
    public int getFromColumn() {
        return fromColumn;
    }

    /**
     * This method gets the row that this move will go to
     * @return the row that this move will go to
     */
    public int getToRow() {
        return toRow;
    }

    /**
     * This method gets the column that this move will go to
     * @return the column that this move will go to
     */
    public int getToColumn() {
        return toColumn;
    }

    /**
     * This method returns the row of the index to flip
     * @return the row of the index to flip
     */
    public int getIndexToFlipRow() {
        return indexToFlip / 8;
    }

    /**
     * This method returns the column of the index to flip
     * @return the column of the index to flip
     */
    public int getIndexToFlipColumn() {
        return indexToFlip % 8;
    }

    /**
     * This method gets square colour of the move
     * @return the square colour of the move
     */
    public Square getSquare() {
        return square;
    }

    /**
     * This method gets the index of the landing square
     * @return the square index of which this move will land
     */
    public int getToIndex() {
        return getToRow() * 8 + getToColumn();
    }

    /**
     * This method gets the index of the beginning square
     * @return the square index of which this move will start from
     */
    public int getFromIndex() {
        return getFromRow() * 8 + getFromColumn();
    }

    /**
     * This method returns a string representation of this object
     * @return a string representation of this subclass, including the attributes from the parent class
     */
    @Override
    public String toString() {
        return "model.Move{" +
                "fromRow=" + fromRow +
                ", fromColumn=" + fromColumn +
                ", toRow=" + toRow +
                ", toColumn=" + toColumn +
                ", square=" + square +
                ", indexToFlip=" + indexToFlip +
                '}';
    }
}