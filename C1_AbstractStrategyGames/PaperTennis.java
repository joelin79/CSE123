import java.util.Scanner;

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

    public PaperTennis(){
        this.player1Turn = true;
        this.position = 0;
        this.points1 = 50;
        this.points2 = 50;
        this.roundWins1 = 0;
        this.roundWins2 = 0;
        this.currentRound = 1;
    }

    /**
     * Constructs and returns a String describing how to play the game. Should include
     * any relevant details on how to interpret the game state as returned by toString(),
     * how to make moves, the game end condition, and how to win.
     */
    @Override
    public String instructions() {
        return "";
    }

    /**
     * Constructs and returns a String representation of the current game state.
     * This representation should contain all information that should be known to
     * players at any point in the game, including board state (if any) and scores (if any).
     */
    @Override
    public String toString() {
        String result = "\n---------------------------------------------------------\n";
        result += "[Player 1]                                              [Player 2]\n";
        String frontSpace = player1Bid < 10 ? "    " : "   ";
        String backSpace = points1 < 10 ? "   " : "  ";
        String court = frontSpace + player1Bid + " " + points1 + backSpace + "|          |          |" +
                "|          |          |  " + player2Bid + " " + points2+"\n";
        result += court.substring(0, calculateBallStrIndex(position)) + TENNIS_BALL
                + court.substring(calculateBallStrIndex(position));
        result += "---------------------------------------------------------";
        return result;
    }

    private static int calculateBallStrIndex(int position) {
        int[] ballStrIndices = {10, 16, 27, 33, 39, 50, 56}; // Corresponding values
        int index = position + 3; // Shift index to match array position

        if (index < 0) return ballStrIndices[0]; // position < -2
        if (index >= ballStrIndices.length) return ballStrIndices[ballStrIndices.length - 1]; // position > 2

        return ballStrIndices[index];
    }

    /**
     * Returns the index of the player who has won the game,
     * or -1 if the game is not over.
     */
    @Override
    public int getWinner() {
        if (!gameover()) return GAME_NOT_OVER;

        if( gameover() && position > 0){
            roundWins1 += (position > 2) ? 2 : 1;
        }
        if ( gameover() && position < 0){
            roundWins2 += (position < -2) ? 2 : 1;
        }

        if (roundWins1 >= 3) return PLAYER_1;
        if (roundWins2 >= 3) return PLAYER_2;

        if(currentRound < 3){
            currentRound++;
            resetRound();
            return GAME_NOT_OVER;
        } else {
            return (roundWins1 == roundWins2) ? TIE : (roundWins1 > roundWins2) ? PLAYER_1 : PLAYER_2;
        }
    }

    private boolean gameover(){
        return position < -2 || position > 2 || (points1 == 0 && points2 == 0) || currentRound > 3;
    }

    /**
     * Returns the index of the player who will take the next turn.
     * If the game is over, returns -1.
     */
    @Override
    public int getNextPlayer() {
        return player1Turn ? PLAYER_1 : PLAYER_2;
    }

    /**
     * Takes input from the parameter to specify the move the player
     * with the next turn wishes to make, then executes that move.
     * If any part of the move is illegal, throws an IllegalArgumentException.
     * input should be non-null.
     *
     * @param input
     */
    @Override
    public void makeMove(Scanner input) {
        System.out.print("Please enter your bid: ");
        int bid = input.nextInt();

        if( (player1Turn && bid > points1)
          || (!player1Turn && bid > points2)){
            throw new IllegalArgumentException();
        }

        if(player1Turn){
            points1 -= bid;
            player1Bid = bid;
        } else {
            points2 -= bid;
            player2Bid = bid;
            int matchWinner = (player1Bid == player2Bid) ? TIE : (player1Bid > player2Bid) ? PLAYER_1 : PLAYER_2;
            moveBall(matchWinner);
        }

        player1Turn = !player1Turn;
    }

    private void moveBall(int matchWinner){
        if(matchWinner == PLAYER_1){
            position++;
            if (position == 0){ position++; }
        } else if (matchWinner == PLAYER_2){
            position--;
            if (position == 0){ position--; }
        }
    }

    private void resetRound() {
        System.out.println();
        System.out.println();
        System.out.println("======== ROUND " + currentRound +" ["+roundWins1+":"+roundWins2+"] ========");
        position = 0;
        points1 = 50;
        points2 = 50;
        player1Bid = 0;
        player2Bid = 0;
        player1Turn = true;
    }



}
