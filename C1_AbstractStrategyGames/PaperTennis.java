// C1: Abstract Strategy Games
// Name: Joe Lin
// Date: Jan 29, 2025
// CSE 123 BK
// TA: Benoit Le

import java.util.Scanner;

/**
 * PaperTennis is a turn-based bidding game where two players compete
 * to move a ball by bidding points. The game consists of multiple rounds,
 * and the player who wins the most rounds becomes the overall winner.
 */
public class PaperTennis extends AbstractStrategyGame {
    public static final String TENNIS_BALL = "🎾";
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public static final int TIE = 0;
    public static final int GAME_NOT_OVER = -1;

    private boolean player1Turn;
    private int position;
    private int points1;
    private int points2;
    private int player1Bid;
    private int player2Bid;
    private int roundWins1;
    private int roundWins2;
    private int currentRound;

    /**
     * Initializes a new game of Paper Tennis with default values.
     * - Sets the starting position of the ball to the center.
     * - Each player begins with 50 points.
     * - The game starts with Player 1's turn.
     * - No rounds have been won initially.
     * - The game begins at round 1.
     */
    public PaperTennis() {
        this.player1Turn = true;
        this.position = 0;
        this.points1 = 50;
        this.points2 = 50;
        this.roundWins1 = 0;
        this.roundWins2 = 0;
        this.currentRound = 1;
    }

    /**
     * Provides instructions on how to play the game, including
     * how to interpret the game state, make moves, and win conditions.
     *
     * @return A string describing the game rules and mechanics.
     */
    @Override
    public String instructions() {
        return "Paper Tennis is a bidding game where two players compete to\n" +
                "push the ball to the opponent’s side by bidding points.\n\n" +
                "Game Rules:\n" +
                "- Each player starts with 50 points per round.\n" +
                "- Players bid points; the higher bid moves the ball.\n" +
                "- If bids are equal, the ball stays in place.\n" +
                "- A round ends when the ball reaches the far end or\n" +
                "  both players run out of points.\n" +
                "- The game consists of three rounds; the player who wins\n" +
                "  the most rounds is the overall winner.\n";
    }

    /**
     * Returns a string representation of the current game state,
     * displaying player scores, bids, and the ball position on the court.
     *
     * @return A formatted string representing the game board and status.
     */
    @Override
    public String toString() {
        String ui = "\n---------------------------------------------------------------------\n";
        ui += "| [Player 1]                                             [Player 2] |\n";
        String frontSpace = player1Bid < 10 ? "|    " : "|   ";
        String backSpace = points1 < 10 ? "   " : "  ";
        String court =
                frontSpace + player1Bid + " " + points1 + backSpace
                        + "[[          |        |" + "|          |          ]]  "
                        + player2Bid + " " + points2 + "   |\n";
        ui += court.substring(0, calculateBallStrIndex(position)) + TENNIS_BALL
                + court.substring(calculateBallStrIndex(position));
        ui += "---------------------------------------------------------------------";

        if (getWinner() != -1) {
            ui += "\n\n" + printScoreboard();
        }
        return ui;
    }

    /**
     * Calculates the index position of the ball within the string representation
     * of the game board, ensuring correct placement based on the ball's movement.
     *
     * @param position The ball's current position on the court.
     * @return The index position in the string where the ball should be placed.
     */
    private static int calculateBallStrIndex(int position) {
        int[] ballStrIndices = {10, 16, 27, 33, 39, 50, 56}; // Corresponding values in ui str
        int index = position + 3;

        // position < -2
        if (index < 0) return ballStrIndices[0];
        // position > 2
        if (index >= ballStrIndices.length) return ballStrIndices[ballStrIndices.length - 1];

        return ballStrIndices[index];
    }

    /**
     * Determines the winner of the game. The game ends after three rounds,
     * and the player with the most round wins is declared the winner.
     * If rounds are tied, the game is a draw.
     *
     * @return The player index (1 or 2) who won, 0 for a tie, or -1 if the game is still ongoing.
     */
    @Override
    public int getWinner() {

        // ROUND IN PROGRESS: skip
        if (!roundIsOver()) return GAME_NOT_OVER;

        // <3 ROUND: Start next round
        if (currentRound < 3) {
            resetRound();
            return GAME_NOT_OVER;
        } else {
            // >=3 ROUND: End game, return winner
            return (roundWins1 == roundWins2) ? TIE
                    : (roundWins1 > roundWins2) ? PLAYER_1 : PLAYER_2;
        }
    }

    /**
     * Updates the round score based on the ball's position when a round ends.
     * Points are awarded differently based on how far the ball has traveled.
     */
    private void updateScore() {
        // END OF ROUND: Award points
        if (roundIsOver() && position > 0) {
            roundWins1 += (position > 2) ? 2 : 1;
        } else if (roundIsOver() && position < 0) {
            roundWins2 += (position < -2) ? 2 : 1;
        }
    }

    /**
     * Checks if the current round is over. A round ends when the ball reaches
     * one side of the court beyond a threshold or both players run out of points.
     *
     * @return True if the round has ended, false otherwise.
     */
    private boolean roundIsOver() {
        return position < -2 || position > 2 || (points1 == 0 && points2 == 0);
    }

    /**
     * Determines which player will take the next turn.
     * If the game is over, returns -1.
     *
     * @return The index of the next player (1 or 2), or -1 if the game is over.
     */
    @Override
    public int getNextPlayer() {
        return player1Turn ? PLAYER_1 : PLAYER_2;
    }

    /**
     * Processes a player's move by accepting their bid, ensuring it is valid,
     * and updating their score. The round's outcome is determined when both
     * players have submitted their bids.
     *
     * @param input A Scanner object to read player input.
     * @throws IllegalArgumentException if a player bids more points than they have / is negative
     *          or when the input Scanner is null
     */
    @Override
    public void makeMove(Scanner input) {
        if (input == null) { throw new IllegalArgumentException(); }
        System.out.print("Please enter your bid: ");
        int bid = input.nextInt();

        if (bid < 0 || (player1Turn && bid > points1 )
                || (!player1Turn && bid > points2)) {
            throw new IllegalArgumentException();
        }

        if (player1Turn) {
            points1 -= bid;
            player1Bid = bid;
        } else {
            points2 -= bid;
            player2Bid = bid;
            int matchWinner = (player1Bid == player2Bid) ? TIE
                    : (player1Bid > player2Bid) ? PLAYER_1 : PLAYER_2;
            moveBall(matchWinner);
        }
        updateScore();
        player1Turn = !player1Turn;
    }

    /**
     * Moves the ball based on the result of the bidding round.
     * The ball moves towards the player who bid higher. If the bids
     * are equal, the ball stays in place.
     *
     * @param matchWinner The player who won the bidding round (1 or 2), or 0 for a tie.
     */
    private void moveBall(int matchWinner) {
        if (matchWinner == PLAYER_1) {
            position++;
            if (position == 0) {
                position++;
            }
        } else if (matchWinner == PLAYER_2) {
            position--;
            if (position == 0) {
                position--;
            }
        }
    }

    /**
     * Resets the game state for a new round, restoring players' points
     * and repositioning the ball. Displays the scoreboard before starting
     * the next round.
     */
    private void resetRound() {
        System.out.println();
        System.out.println(printScoreboard());
        System.out.println();
        currentRound++;
        System.out.println("============= ROUND " + currentRound + " =============");
        position = 0;
        points1 = 50;
        points2 = 50;
        player1Bid = 0;
        player2Bid = 0;
        player1Turn = true;
    }

    /**
     * Prints the current scoreboard, showing the number of rounds won
     * by each player and the current round number.
     *
     * @return A formatted string displaying the current game score.
     */
    private String printScoreboard() {
        String result = "======== Scoreboard [Round " + currentRound + "/3] ========\n";
        result += "Player 1 Score: " + roundWins1 + "\n";
        result += "Player 2 Score: " + roundWins2 + "\n";
        result += "========================================";
        return result;
    }
}
