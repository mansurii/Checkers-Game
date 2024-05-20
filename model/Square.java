package model;

/**
 * Enumerator type class, to represent the squares on the board.
 * They can be blue, red, king red, king blue and empty.
 */
public enum Square {
    BLUE, RED, KINGBLUE, KINGRED, EMPTY;

    /**
     * This method gets the opponent square colour.
     * @return the opponent colour of the square.
     */
    public Square getOtherPlayerSquare() {
        if (this == Square.BLUE) {
            return Square.RED;
        } else if (this == Square.RED) {
            return Square.BLUE;
        }
        return Square.EMPTY;
    }

    /**
     * This method checks if the instance of that move object is a king or not.
     * @return true if it's a king, false otherwise.
     */
    public boolean isKing() {
        return this == Square.KINGBLUE || this == Square.KINGRED;
    }

    /**
     * This method gets the king square of a normal square colour. For example if you have blue, your king
     * will be king blue, and the same goes for the red square.
     * @return the king square of a normal square.
     */
    public Square getKingSquare() {
        if (this == Square.BLUE || this == Square.KINGBLUE) {
            return Square.KINGBLUE;
        } else if (this == Square.RED || this == Square.KINGRED) {
            return Square.KINGRED;
        }
        return null;
    }
}
