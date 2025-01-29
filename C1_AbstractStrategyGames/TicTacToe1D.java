// **THIS IS AN EXAMPLE IMPLEMENTATION!**
// Brett Wortzman
// CSE 123
// C1: Abstract Strategy Games

import java.util.*;

// A class to represent a game of tic-tac-toe that extends the 
// AbstractStrategyGame class. 
public class TicTacToe1D extends AbstractStrategyGame {
    public static final char PLAYER_1_TOKEN = 'X';
    public static final char PLAYER_2_TOKEN = 'O';
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public static final int TIE = 0;
    public static final int GAME_IS_OVER = -1;
    public static final int GAME_NOT_OVER = -1;
    
    private char[] board;
    private boolean isXTurn;

    // Constructs a new TicTacToe game.
    public TicTacToe1D() {
        this.board = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        this.isXTurn = true;
    }

    // Returns the index of the winner of the game.
    // 1 if player 1 (X), 2 if player 2 (O), 0 if a tie occurred,
    // and -1 if the game is not over.
    public int getWinner() {
        int size = (int) Math.sqrt(board.length);

        for (int i = 0; i < size; i++) {
            // check row i
            int rowWinner = getRowWinner(i);
            if (rowWinner != GAME_NOT_OVER) {
                return rowWinner;
            }

            int colWinner = getColWinner(i);
            if (colWinner != GAME_NOT_OVER) {
                return colWinner;
            }
        }

        // check diagonals
        int diagWinner = getDiagWinner();
        if (diagWinner != GAME_NOT_OVER) return diagWinner;

        return checkTie();
    }

    // Returns the index of which player's turn it is.
    // 1 if player 1 (X), 2 if player 2 (O), -1 if the game is over
    public int getNextPlayer() {
        if (isGameOver()) {
            return GAME_IS_OVER;
        }
        
        return isXTurn ? PLAYER_1 : PLAYER_2;
    }

    // Given the input, places an X or an O where
    // the player specifies.
    // Throws an IllegalArgumentException if the provided scanner is null.
    // Throws an IllegalArgumentException if the position is
    // invalid, whether that be out of bounds or already occupied.
    // Board bounds are [1, 9] for cells.
    public void makeMove(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        char currToken = isXTurn ? PLAYER_1_TOKEN : PLAYER_2_TOKEN;

        System.out.print("Cell? ");
        int cell = input.nextInt();

        makeMove(cell, currToken);
        isXTurn = !isXTurn;
    }

    // Private helper method for makeMove.
    // Given a cell number, as well as player token,
    // places that token in the specified cell.
    // Throws an IllegalArgumentException if the position is
    // invalid, whether that be out of bounds or already occupied.
    // Board bounds are [1, 9] for cells.
    private void makeMove(int cell, char token) {
        cell--;
        if (cell < 0 || cell >= board.length) {
            throw new IllegalArgumentException("Invalid board position: " + (cell + 1));
        }

        if (!isEmpty(cell)) {
            throw new IllegalArgumentException("Space already occupied: " + (cell + 1));
        }

        board[cell] = token;
    }

    // Returns a String containing instructions to play the game.
    public String instructions() {
        String result = "";
        result += "Player 1 is X and goes first. Choose where to play by entering the number\n";
        result += "of the cell (displayed by the board). Spaces shown as a number are empty.\n";
        result += "The game ends when one player marks three spaces in a row, in which that\n";
        result += "player wins, or when the board is full, in which case the game end in a tie.";
        return result;
    }

    // Returns a String representation of the current state of the board.
    // For example, the empty board looks like:
    // "| 1 | 2 | 3 |
    //  | 4 | 5 | 6 |
    //  | 7 | 8 | 9 |"
    // Where each cell is labeled with its number.
    public String toString() {
        String result = "";
        int size = (int) Math.sqrt(board.length);
        for (int i = 0; i < size; i++) {
            result += "|";
            for (int j = 0; j < size; j++) {
                result += " " + board[size * i + j] + " |";
            }
            result += "\n";
        }

        return result;
    }

    // Returns the winner of the row.
    // Returns 1 if player 1 won, 2 if player 2 won, -1 otherwise.
    private int getRowWinner(int row) {
        int size = (int) Math.sqrt(board.length);
        int rowOffset = row * size;
        if (isEmpty(rowOffset)) {
            return GAME_NOT_OVER;
        }

        if (board[rowOffset] == board[rowOffset + 1] 
            && board[rowOffset] == board[rowOffset + 2]) {
            return getPlayer(board[rowOffset]);
        }

        return GAME_NOT_OVER;
    }

    // Returns the winner of the column.
    // Returns 1 if player 1 won, 2 if player 2 won, -1 otherwise.
    private int getColWinner(int col) {
        if (isEmpty(col)) {
            return GAME_NOT_OVER;
        }
        
        int size = (int) Math.sqrt(board.length);
        if (board[col] == board[col + size] 
            && board[col] == board[col + 2 * size]) {
            return getPlayer(board[col]);
        }
        return GAME_NOT_OVER;
    }

    // Returns the winner of either diagonal.
    // Returns 1 if player 1 won, 2 if player 2 won, -1 otherwise.
    private int getDiagWinner() {
        // 4 is the center cell
        if (isEmpty(4)) {
            return GAME_NOT_OVER;
        }

        // 0 is the top left cell, 4 is center cell, and 8 is the bottom right cell
        if (board[0] == board[4] && board[4] == board[8]) {
            return getPlayer(board[4]);
        }

        // 2 is the top right cell, 4 is center cell, and 6 is the bottom left cell
        if (board[2] == board[4] && board[4] == board[6]) {
            return getPlayer(board[4]);
        }

        return GAME_NOT_OVER;
    }

    // Returns 0 if there's a tie and -1 otherwise.
    private int checkTie() {
        // check for tie
        for (int i = 0; i < board.length; i++) {
            if (isEmpty(i)) {
                // unfilled space; game not over
                return GAME_NOT_OVER;
            }
        }

        // it's a tie!
        return TIE;
    }

    // Returns the player associated with the player token.
    // Returns 1 if player 1 (X), 2 if player 2 (O), -1 if the space is empty. 
    private int getPlayer(char token) {
        if (token == PLAYER_1_TOKEN) {
            return PLAYER_1;
        } else if (token == PLAYER_2_TOKEN) {
            return PLAYER_2;
        }

        return GAME_NOT_OVER;
    }

    // Returns true if the specified cell (0-based index) is empty. False otherwise
    private boolean isEmpty(int cell) {
        return board[cell] != PLAYER_1_TOKEN && board[cell] != PLAYER_2_TOKEN;
    }
}
