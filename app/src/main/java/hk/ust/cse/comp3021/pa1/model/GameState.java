package hk.ust.cse.comp3021.pa1.model;

import hk.ust.cse.comp3021.pa1.controller.GameBoardController;
import hk.ust.cse.comp3021.pa1.view.GameBoardView;
import org.jetbrains.annotations.NotNull;

/**
 * Class for tracking the state of multiple game components.
 */
public class GameState {

    /**
     * Number representing unlimited number of lives for a player.
     */
    public static final int UNLIMITED_LIVES = -1;

    /**
     * The game board managed by this instance.
     */
    @NotNull
    private final GameBoard gameBoard;

    /**
     * {@link MoveStack} instance of all moves performed by the player.
     */
    @NotNull
    private final MoveStack moveStack = new MoveStack();

    /**
     * The number of deaths of the player.
     */
    private int numDeaths = 0;

    /**
     * The number of moves performed by the player (excluding invalid moves).
     */
    private int numMoves = 0;

    /**
     * The number of lives the player has.
     */
    private int numLives;

    /**
     * The number of gems initially on the game board when this instance was created.
     */
    private final int initialNumOfGems;

    /**
     * Creates an instance.
     *
     * <p>
     * The player will have an unlimited number of lives by default.
     * </p>
     *
     * @param gameBoard The game board to be managed by this instance.
     */
    public GameState(@NotNull final GameBoard gameBoard) {
        // TODO(DONE)
        this.gameBoard = gameBoard;
        this.initialNumOfGems = gameBoard.getNumGems();
        this.numLives = UNLIMITED_LIVES;
    }

    /**
     * Creates an instance.
     *
     * <p>
     * Note: Do NOT throw an exception when {@code numLives} is zero. This will be used in our testing.
     * </p>
     *
     * @param gameBoard The game board to be managed by this instance.
     * @param numLives  Number of lives the player has. If the value is negative, treat as if the player has an
     *                  unlimited number of lives.
     */
    public GameState(@NotNull final GameBoard gameBoard, final int numLives) {
        // TODO(DONE)
        this.gameBoard = gameBoard;
        this.initialNumOfGems = gameBoard.getNumGems();
        this.numLives = numLives;
    }

    /**
     * Checks whether the game has been won.
     *
     * <p>
     * The game is won when there are no gems left in the game board.
     * </p>
     *
     * @return Whether the player has won the game.
     */
    public boolean hasWon() {
        // TODO(DONE)
        return this.gameBoard.getNumGems() == 0;
    }

    /**
     * Checks whether the game has been lost.
     *
     * <p>
     * The game is lost when the player has no lives remaining. For games which the player has unlimited lives, this
     * condition should never trigger.
     * </p>
     *
     * @return Whether the player has lost the game.
     */
    public boolean hasLost() {
        // TODO(DONE)
        return numLives == 0;
    }

    /**
     * Increases the player's number of lives by the specified amount.
     *
     * @param delta The number of lives to give the player.
     * @return The new number of lives of the player. If the player has unlimited number of lives, returns
     * {@link Integer#MAX_VALUE}.
     */
    public int increaseNumLives(final int delta) {
        // TODO(DONE)
        if (numLives == -1){
            return Integer.MAX_VALUE;
        }else {
            numLives += delta;
            return numLives;
        }
    }

    /**
     * Decreases the player's number of lives by the specified amount.
     *
     * @param delta The number of lives to take from the player.
     * @return The new number of lives of the player. If the player has unlimited number of lives, returns
     * {@link Integer#MAX_VALUE}.
     */
    public int decreaseNumLives(final int delta) {
        // TODO(DONE)
        if (numLives == -1){
            return Integer.MAX_VALUE;
        }else {
            numLives -= delta;
            return numLives;
        }
    }

    /**
     * Decrements the player's number of lives by one.
     *
     * @return The new number of lives of the player. If the player has unlimited number of lives, returns
     * {@link Integer#MAX_VALUE}.
     */
    public int decrementNumLives() {
        // TODO(DONE)
        if (numLives == -1){
            return Integer.MAX_VALUE;
        }else {
            numLives -= 1;
            return numLives;
        }
    }

    /**
     * Increments the number of moves taken by the player.
     *
     * @return The new number of moves taken by the player.
     */
    public int incrementNumMoves() {
        // TODO(DONE)
        numMoves++;
        return numMoves;
    }

    /**
     * Increments the number of deaths of the player.
     *
     * @return The new number of deaths of the player.
     */
    public int incrementNumDeaths() {
        // TODO(DONE)
        numDeaths++;
        return numDeaths;
    }

    /**
     * @return The current number of deaths of the player.
     */
    public int getNumDeaths() {
        // TODO(DONE)
        return numDeaths;
    }

    /**
     * @return The current number of moves taken by the player.
     */
    public int getNumMoves() {
        // TODO(DONE)
        return numMoves;
    }

    /**
     * @return Whether the player has unlimited lives.
     */
    public boolean hasUnlimitedLives() {
        // TODO(DONE)
        return numLives == -1;
    }

    /**
     * @return The number of lives a player has. If the player has an unlimited number of lives, returns
     * {@link Integer#MAX_VALUE}.
     */
    public int getNumLives() {
        // TODO(DONE)
        if (numLives == -1){
            return Integer.MAX_VALUE;
        }else {
            return numLives;
        }
    }

    /**
     * @return The number of gems that is still present on the game board.
     */
    public int getNumGems() {
        // TODO(DONE)
        return this.gameBoard.getNumGems();
    }

    /**
     * <p>
     * At any point of the game, the score should be computed using the following formula:
     * </p>
     * <ul>
     * <li>The initial score of any game board is {@code gameBoardSize}.</li>
     * <li>Each gem will be worth 10 points.</li>
     * <li>Each valid move deducts one point.</li>
     * <li>Each undo deducts two points.</li>
     * <li>Each death deducts four points (on top of the one point deducted by a valid move).</li>
     * </ul>
     *
     * @return The current score of this game.
     */
    public int getScore() {
        // TODO(DONE)
        return gameBoard.getNumCols()* gameBoard.getNumRows() + (initialNumOfGems - getNumGems())*10
                - numMoves - moveStack.getPopCount()*2 - numDeaths*4;
    }

    /**
     * @return A controller of the managed game board for mutation.
     */
    public GameBoardController getGameBoardController() {
        // TODO(DONE)
        return new GameBoardController(this.gameBoard);
    }

    /**
     * @return A read-only view of the managed game board.
     */
    public GameBoardView getGameBoardView() {
        // TODO(DONE)
        return new GameBoardView(this.gameBoard);
    }

    /**
     * @return The instance of the managed {@link GameBoard}.
     */
    @NotNull
    public GameBoard getGameBoard() {
        // TODO(DONE)
        return this.gameBoard;
    }

    /**
     * @return The instance of the managed {@link MoveStack}.
     */
    @NotNull
    public MoveStack getMoveStack() {
        // TODO(DONE)
        return this.moveStack;
    }
}
