package view;

import controller.*;
import model.*;
import model.Player;

import java.util.Scanner;

/**
 * This class represents the view.TUI, of which two players can play Draughts!!
 */
public class TUI {

    public static void main(String[] args) {
        System.out.println("Welcome to Draughts!");
        new TUI().playGame();
    }

    /**
     * This method, gets input from the players for the game and calls the other methods.
     */
    public void playGame(){
        Scanner scanner = new Scanner(System.in);
        //getting the players name and assigning the squares
        Player player1 = null;
        Player player2 = null;
        for (int i = 1; i<=2; i++) {
            System.out.println("Enter model.Player " +  i + "'s name:");
            String playerName = scanner.nextLine();

            if (i == 1) {
                player1 = new Player(playerName, Square.BLUE);
            } else {
                player2 = new Player(playerName, Square.RED);
            }
        }
        //creating the game object
        Game game = new Game(player1,player2);
        while (!game.isGameOver()) { //while the game is not over, continue play
            System.out.println(game.getBoard()); //printing the board and the square

            System.out.println(
                    "Current score is: \n" +
                            player1.getName() + " (BLUE): " + game.calculatePlayerScore(player1) + "\n" +
                            player2.getName() + " (RED): " + game.calculatePlayerScore(player2)
            );

            Player currentPlayer = game.getTurn(); //getting the current turn of the player and takes input to make a move.
            System.out.println("It's " + currentPlayer.getName() + "'s turn" + " (" + currentPlayer.getSquareColour() + ")");
            game.doMove(currentPlayer.makeMove(game));
        }
        //when the game is finished display the winner
        if (game.getWinner() == null) {
            System.out.println("controller.Game was draw");
        } else {
            System.out.println(game.getBoard());
            System.out.println("controller.Game over. model.Player : " + game.getWinner().getName() +  " (" + game.getWinner().getSquareColour() + ")" + " won.");
            System.out.println(
                    "Score of the game finished at: \n" +
                            player1.getName() + " (BLUE): " + game.calculatePlayerScore(player1) + "\n" +
                            player2.getName() + " (RED): " + game.calculatePlayerScore(player2)
            );
        }
        //ask again if they would like to play another game.
        System.out.println("Would you like to play a new game (Y/N).");
        String playerInput = scanner.nextLine().toLowerCase();

        while(!(playerInput.equals("y") || playerInput.equals("n"))){
            System.out.println("Please enter y/n");
            playerInput = scanner.nextLine().toLowerCase();
        }

        if (!playerInput.equals("y")) {
            System.out.println("Thanks for playing, goodbye!");
        } else {
            playGame();
        }
    }
}
