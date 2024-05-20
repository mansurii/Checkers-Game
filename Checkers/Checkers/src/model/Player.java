package model;

import controller.Game;
import model.Move;
import model.Square;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This object represents a player in the game.
 */
public class Player {
    private final String name;
    private final Square colour;
    private final Scanner scanner;

    /**
     * Constructor to initialize the object.
     * @param name the name of the player
     * @param colour the colour of the player
     */
    public Player(String name, Square colour){
        this.name = name;
        this.colour = colour;
        this.scanner = new Scanner(System.in); //create the scanner instance to get input form the user
    }

    /**
     * Returns the name of the player.
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the square colour of the player
     * @return the square of the player
     */
    public Square getSquareColour() {
        return colour;
    }

    /**
     *This method gets the valid moves, for that game, and generates the valid move for that player.
     * @param game the current game that this player is playing
     * @return the move that the player has selected.
     */
    public Move makeMove(Game game) {
        //getting the valid moves for that player
        List<Move> validMovesForThePlayer = game.getAllValidMoves(getSquareColour());
        //using a set to remove the duplicate moves and getting all the move indexes.
        Set<Integer> indexesThatCanBePlayed = validMovesForThePlayer.stream().map(Move::getFromIndex).collect(Collectors.toSet());

        Move moveToMake; //the move to return
        int moveFromIndex; //the index to move from

        //asking first which of the available indexes they would like to move
        while(true) {
            System.out.println(getName() + " Which square would you like to move? " + indexesThatCanBePlayed);
            String squareToMove = scanner.nextLine();
            int moveToInt;
            try {
                moveToInt = Integer.parseInt(squareToMove);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
                continue;
            }
            if (indexesThatCanBePlayed.contains(moveToInt)) {
                moveFromIndex = moveToInt;
                break;
            } else {
                System.out.println("model.Move is not in the available moves! " + indexesThatCanBePlayed);
            }
        }
        //getting the set with the valid moves that that has the moveFromIndex selected before
        Set<Integer> validIndexes = validMovesForThePlayer.stream().filter(move -> move.getFromIndex() == moveFromIndex).map(Move::getToIndex).collect(Collectors.toSet());

        //getting the input of where does the player wants to place that move
        while (true) {
            System.out.println("Where do you want to place that square? " + validIndexes);
            String input = scanner.nextLine();

            int enterNum;
            try {
                enterNum = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid input!");
                continue;
            }

            if (enterNum >= 0 && enterNum < 64) {
                for (Move move1 : validMovesForThePlayer) {
                    if (move1.getToIndex() == enterNum && move1.getFromIndex() == moveFromIndex) {
                        moveToMake = move1;
                        return moveToMake; //returning that move
                    }
                }
                System.out.println("model.Move is not valid!");
            } else {
                System.out.println("Invalid index, please try again!");
            }
        }
    }

    /**
     * Returns a string representation of that object.
     * @return the string representation of the player object.
     */
    @Override
    public String toString(){
        return "Name: " + getName() + "\nColor: " + getSquareColour();
    }
}
