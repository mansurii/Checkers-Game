package controller;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * THis class has all the necessary methods for the checkers game.
 */
public class Game {
    private final Board board; //the board
    private final Player player1; //the player1
    private final Player player2; //player2
    private Player playersTurn; //variable to keep track of the curent player that has the turn
    /*
    variable that has all the possible king moves for blue,
    which are basically the ones at the other side of the board
     */
    private final int[] possibleKingsForBlue;
    private final int[] possibleKingsForRed; //same as for the blue

    /**
     * Constructor for the game. It creates a board object and initializes the players,
     * and all the moves for the kings.
     * @param player1 the player1 object
     * @param player2 the player2 object
     */
    public Game(Player player1, Player player2) {
        this.board = new Board(8, 8);
        this.player1 = player1;
        this.player2 = player2;
        this.playersTurn = player1;
        this.possibleKingsForBlue = new int[]{56, 57, 58, 59, 60, 61, 62, 63};
        this.possibleKingsForRed = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    }

    /**
     * This method returns all the valid moves for a square. It goes through the board, and find for each index
     * the valid moves. If there are no captures, it just returns for each index, the adjacent diagonal free move.
     * @param square the square to check the valid moves.
     * @return all the valid moves for that square in list.
     */
    public List<Move> getAllValidMoves(Square square) {
        List<Move> validMoves = new ArrayList<>(); //the list to return
        /*
        gets the index of all the squares that has the same colour
         */
        List<Integer> playerSquaresOnBoard = this.getSquaresOnBoard(square);

        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; //the diagonal directions to check

        for (int index : playerSquaresOnBoard) { //for each square on the board (of the same colour)
            int currentRow = index / 8;
            int currentCol = index % 8;

            boolean isKing = getBoard().getContent(currentRow,currentCol).isKing(); //checking if that index is a king

            for (int[] direction : directions) { //for every diagonal direction
                int newRow = currentRow;
                int newCol = currentCol;
                List<Integer> indexesToFlip = new ArrayList<>();

                if (isKing) { //if it is king we have to go through the matrix until the move is in bounds
                    newRow += direction[0];
                    newCol += direction[1];
                    while (board.isInBounds(newRow, newCol) && board.getContent(newRow, newCol) == Square.EMPTY) {
                        newRow += direction[0];
                        newCol += direction[1];
                    }

                    if (board.isInBounds(newRow, newCol) && board.getContent(newRow, newCol).equals(square.getOtherPlayerSquare())) {
                        while (board.isInBounds(newRow, newCol) && board.getContent(newRow, newCol).equals(square.getOtherPlayerSquare())) {
                            indexesToFlip.add(newRow * 8 + newCol);
                            newRow += direction[0];
                            newCol += direction[1];
                        }
                        if (board.isInBounds(newRow, newCol) && board.getContent(newRow, newCol) == Square.EMPTY) {
                            KingMove moveToAdd = new KingMove(currentRow, currentCol, newRow, newCol, square);
                            //adding all the moves to be flipped with this king move.
                            moveToAdd.setIndexesToFlip(indexesToFlip.stream().mapToInt(i -> i).toArray());
                            validMoves.add(moveToAdd);
                        }
                    }
                } else { //square is not a king move, so only one position is allowed from the move to move.
                    int enemyRow = currentRow + direction[0]; //getting where the other players square is
                    int enemyCol = currentCol + direction[1];
                    int landingRow = enemyRow + direction[0]; //and where our new move will land
                    int landingCol = enemyCol + direction[1];
                    /*
                    getting the squares of the enemy including both kings and normal, that's why I use list
                     */
                    List<Square> enemyColors;
                    if (board.getContent(currentRow, currentCol) == Square.RED || board.getContent(currentRow, currentCol) == Square.KINGRED) {
                        enemyColors = Arrays.asList(Square.BLUE, Square.KINGBLUE);
                    } else {
                        enemyColors = Arrays.asList(Square.RED, Square.KINGRED);
                    }
                    //checking if the move is in bounds
                    if (board.isInBounds(enemyRow, enemyCol) && board.isInBounds(landingRow, landingCol)) {
                        Square enemySquare = board.getContent(enemyRow, enemyCol); //getting the enemy square
                        //if the previous lsit contains that enemy square and the content of the index to land is empty.
                        if (enemyColors.contains(enemySquare) && board.getContent(landingRow, landingCol).equals(Square.EMPTY)) {
                            int indexOfEnemy = (enemyRow*8) + enemyCol; //calculating the index
                            //creating the move
                            Move moveToAdd = new Move(currentRow, currentCol, landingRow, landingCol, square);
                            moveToAdd.setIndexToFlip(indexOfEnemy); //adding the index to flip to the move
                            validMoves.add(moveToAdd); //adding the move to the list
                        }
                    }
                }
            }
        }

        if  (validMoves.isEmpty()) { //this is triggered when there are no captures
            for (int index : playerSquaresOnBoard) { //going again through every possible move
                int currentRow = index / 8;
                int currentCol = index % 8;

                boolean isKing = getBoard().getContent(currentRow, currentCol).isKing();

                for (int[] direction : directions) {
                    int newRow = currentRow + direction[0];
                    int newCol = currentCol + direction[1];

                    if (isKing) {
                        while (board.isInBounds(newRow, newCol) && board.getContent(newRow, newCol) == Square.EMPTY) {
                            KingMove nonCaptureKingMove = new KingMove(currentRow, currentCol, newRow, newCol, square);
                            //setting it to 0, when there are no squares to flip.
                            nonCaptureKingMove.setIndexesToFlip(new int[0]);
                            validMoves.add(nonCaptureKingMove);

                            newRow += direction[0]; //moving one adjacent diagonal.
                            newCol += direction[1];
                        }
                        //going to the next index, because if its king move, no need to check for normal square.
                        continue;
                    }
                    //checking if the move is in bounds, and it is empty just directly place it
                    if (board.isInBounds(newRow, newCol) && board.getContent(newRow, newCol) == Square.EMPTY) {
                        //if it is, create the move and add it to the list
                        Move nonCaptureMove = new Move(currentRow, currentCol, newRow, newCol, square);
                        validMoves.add(nonCaptureMove);
                        //if the move is in bounds and the content of the next index is the index of the other player
                    } else if (board.isInBounds(newRow, newCol) && board.getContent(newRow, newCol) == square.getOtherPlayerSquare()) {
                        int landingRow = newRow + direction[0];
                        int landingCol = newCol + direction[1];
                        if (board.isInBounds(landingRow, landingCol) && board.getContent(landingRow, landingCol) == Square.EMPTY) {
                            int indexOfEnemy = newRow * 8 + newCol;
                            Move captureMove = new Move(currentRow, currentCol, landingRow, landingCol, square);
                            captureMove.setIndexToFlip(indexOfEnemy);
                            validMoves.add(captureMove);
                        }
                    }
                }
            }
        }

        return validMoves;
    }

    /**
     * This method is placing the move to the board.
     * @param move the move to be placed
     */
    public void doMove(Move move) {
        if (move instanceof KingMove kingMove) { //first checking if the move is a model.KingMove
            doCapture(kingMove); //call the capture method to capture all the opponents squares..
            this.changeTurns(); //change the turns
            return;
        }
        //if it's not a king move
        //if index to flip is -1 this means that it is an adjacent move with no captures
        if (move.getIndexToFlip() == -1) {
            board.removeSquareFromBoard(move.getFromRow(), move.getFromColumn()); //removing the move from the board
            if (checkKing(move.getToIndex(), move.getSquare())) { //if the move becomes a king
                //place a king square
                board.setSquareOnBoard(move.getToRow(), move.getToColumn(), move.getSquare().getKingSquare());
            } else { //place a normal square
                board.setSquareOnBoard(move.getToRow(), move.getToColumn(), move.getSquare());
            }
        } else { // there are moves to be captured.
            doCapture(move);
        }
        this.changeTurns(); //change the turns
    }

    /**
     * This method captures the disks on board, and removes them.
     * @param move the move to make
     */
    public void doCapture(Move move) {
        board.removeSquareFromBoard(move.getFromRow(), move.getFromColumn()); //removing the index of which the row is moving
        if (move instanceof KingMove kingMove) { //if its king move
            for (int index : kingMove.getIndexestoFlip()) { //remove all the pieces
                int row = index / 8;
                int column = index % 8;
                board.removeSquareFromBoard(row, column);
            }
            //add the piece of the king to the landing index.
            board.setSquareOnBoard(move.getToRow(), move.getToColumn(), kingMove.getSquare().getKingSquare());
        } else if (move.getIndexToFlip() != -1) { //if its king move and just moving one adjacent cell.
            board.removeSquareFromBoard(move.getIndexToFlipRow(), move.getIndexToFlipColumn());
        }

        if (checkKing(move.getToIndex(), move.getSquare())) { //if it becomes king
            //place a king square of the same colour.
            board.setSquareOnBoard(move.getToRow(), move.getToColumn(), move.getSquare().getKingSquare());
        } else if (! (move instanceof KingMove)){ //and if it's a normal move
            //setting on the board the same colour as the one the move has.
            board.setSquareOnBoard(move.getToRow(), move.getToColumn(), move.getSquare());
        }
        //this gets all the additional captures if available
        List<Move> getAdditionalCaptures = getAllValidMoves(move.getSquare());
        boolean allMovesAreNonCaptures = getAdditionalCaptures.stream()
                .allMatch(m -> m.getIndexToFlip() == -1); //this boolean statement, checks if there are additional captures
        if (allMovesAreNonCaptures || getAdditionalCaptures.isEmpty()) {
            return; //if there are no, the method ends
        }
        //otherwise it is selecting a random next capture and calls this method again.
        System.out.println("You have one more capture!");
        Move nextCapture = getAdditionalCaptures.get(0);
        System.out.println("Selected the additional capture at index: " + nextCapture.getToIndex());
        doCapture(nextCapture);
    }

    /**
     * This method checks if on the next index to be played, it is a king move.
     * @param index the index of the move to land.
     * @param square the square colour
     * @return true if the colour becomes a king, false otherwise.
     */
    public boolean checkKing(int index, Square square) {
        if (square.equals(Square.BLUE)) { //checks for blue
            for (int i : possibleKingsForBlue) { //for each move in the kings move
                if (i == index) { //if it is that index return true.
                    return true;
                }
            }
        } else { //checks for red
            for (int i : possibleKingsForRed) { //the same as above for blue
                if (i == index) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method gets all the squares on board of a specific square colour, and returns them in index positions.
     * @param square the square colour to loop on the board.
     * @return a list with the indexes.
     */
    public List<Integer> getSquaresOnBoard(Square square) {
        List<Integer> squares = new ArrayList<>();
        for(int i = 0; i< board.getRows(); i++) { //going through the whole board
            for(int j = 0; j<board.getCols(); j++) {
                //if the content is equal to the same colour as the parameter, or if it's a king square of that colour.
                if (board.getContent(i,j).equals(square) || board.getContent(i,j).equals(square.getKingSquare())) {
                    int indexPosition = (i * 8) + j; //calculate the index position which is the row*8+column
                    squares.add(indexPosition); //add it to the list
                }
            }
        }
        return squares;
    }

    /**
     * This method calculates the players score. (How many disks on the board this player has).
     * @param player the player to count the disks on board.
     * @return an integer, the pieces of the board of that player's square.
     */
    public int calculatePlayerScore(Player player) {
        int score = 0;
        for (int row = 0; row < board.getRows(); row++) { //going through the whole board
            for (int col = 0; col < board.getCols(); col++) {
                //getting the square of the player and checking if it's the same colour as the one on the board
                if (player.getSquareColour() == Square.BLUE || player.getSquareColour() == Square.KINGBLUE) {
                    if (board.getContent(row, col).equals(Square.BLUE) || board.getContent(row, col).equals(Square.KINGBLUE)) {
                        score++; //if it is make the score +1
                    }
                } else { //the same for red
                    if (board.getContent(row, col).equals(Square.RED) || board.getContent(row, col).equals(Square.KINGRED)) {
                        score++;
                    }
                }

            }
        }
        return score; //return the pieces on board.
    }

    /**
     * This method is used when we want to change turns on the game.
     */
    public void changeTurns() {
        if (playersTurn.equals(player1)) {
            playersTurn = player2;
        } else {
            playersTurn = player1;
        }
    }

    /**
     * This method gets the winner of the game. It calculates the score of each player and check if the valid moves for
     * that player is empty.
     * @return a model.Player object of the winner, or null if the game is draw.
     */
    public Player getWinner() {
        int piecesPlayer1 = calculatePlayerScore(player1); //getting the pieces for player1
        int piecesPlayer2 = calculatePlayerScore(player2); //getting the pieces for player2

        if (piecesPlayer1 == 0) { //if player 1 has 0 pieces
            return player2; //return player 2
        } else if (piecesPlayer2 == 0) { //if player2 has 0 pieces
            return player1; //return player 1 as a winner
        }
        //variables to check if they can make another move.
        boolean player1CanMove = !getAllValidMoves(player1.getSquareColour()).isEmpty();
        boolean player2CanMove = !getAllValidMoves(player2.getSquareColour()).isEmpty();

        if (!player1CanMove && player2CanMove) { //check here is they can make a move.
            return player2;
        } else if (!player2CanMove && player1CanMove) {
            return player1;
        }
        return null;
    }

    /**
     * This method checks if the game is over by calculating the players squares.
     * @return true if there are no move left, so the game is over, false otherwise.
     */
    public boolean isGameOver() {
        //calculating the players square
        int piecesPlayer1 = calculatePlayerScore(player1);
        int piecesPlayer2 = calculatePlayerScore(player2);

        //if they don't have any piece on board.
        if (piecesPlayer1 == 0 || piecesPlayer2 == 0) {
            return true;
        }

        boolean player1CanMove = !getAllValidMoves(player1.getSquareColour()).isEmpty();
        boolean player2CanMove = !getAllValidMoves(player2.getSquareColour()).isEmpty();

        return !player1CanMove || !player2CanMove; //if both can not move the game is over
    }

    /**
     * This method gets the current player that has the turn.
     * @return the model.Player that has the turn.
     */
    public Player getTurn() {
        return playersTurn;
    }

    /**
     * This method returns the board object of the game.
     * @return the current board object that the game is being played on.
     */
    public Board getBoard() {
        return board;
    }
}
