package model;

import java.util.Arrays;

/**
 * This class represents a king move which includes the additional array for all the indexes to be flipped for that move.
 */
public class KingMove extends Move {

    //the instance variable to keep track of all the squares on board that this move will flip
    private int [] indexesToFlip;

    /**
     * Constructor to create the king move. Since it has the same parameters it just calls the super constructor.
     * @param fromRow the row of which this move will go from
     * @param fromColumn the column of which this move will go from
     * @param toRow the row of which this move will land
     * @param toColumn the column of which this move will land
     * @param square the colour of the square of that move
     */
    public KingMove(int fromRow, int fromColumn, int toRow, int toColumn, Square square) {
        super(fromRow, fromColumn, toRow, toColumn, square);
    }

    /**
     * Setter for the variable.
     * @param indexesToFlip the indexes to be flipped.
     */
    public void setIndexesToFlip(int [] indexesToFlip) {
        this.indexesToFlip = indexesToFlip;
    }

    /**
     * Return the indexes to be flipped
     * @return indexes to be flipped.
     */
    public int [] getIndexestoFlip() {
        return indexesToFlip;
    }

    /**
     * String representation of this object.
     * @return string representation of this object.
     */
    @Override
    public String toString() {
        return "model.KingMove{" +
                "indexestoFlip=" + Arrays.toString(indexesToFlip) +
                "model.Move{" +
                "fromRow=" + super.getFromRow() +
                ", fromColumn=" + super.getFromColumn() +
                ", toRow=" + super.getToRow() +
                ", toColumn=" + super.getToColumn() +
                ", square=" + super.getSquare() +
                ", indexToFlip=" + super.getIndexToFlip() +
                '}' ;
    }
}
