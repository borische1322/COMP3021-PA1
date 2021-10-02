package hk.ust.cse.comp3021.pa1.controller;

import hk.ust.cse.comp3021.pa1.model.Direction;
import hk.ust.cse.comp3021.pa1.model.GameState;
import hk.ust.cse.comp3021.pa1.model.MoveResult;
import org.jetbrains.annotations.NotNull;

/**
 * Controller for {@link hk.ust.cse.comp3021.pa1.InertiaTextGame}.
 *
 * <p>
 * All game state mutations should be performed by this class.
 * </p>
 */
public class GameController {

    @NotNull
    private final GameState gameState;

    /**
     * Creates an instance.
     *
     * @param gameState The instance of {@link GameState} to control.
     */
    public GameController(@NotNull final GameState gameState) {
        // TODO(DONE)
        this.gameState = gameState;
    }

    /**
     * Processes a Move action performed by the player.
     *
     * @param direction The direction the player wants to move to.
     * @return An instance of {@link MoveResult} indicating the result of the action.
     */
    public MoveResult processMove(@NotNull final Direction direction) {
        // TODO(DONE)
        //System.out.println(direction);
        MoveResult move = gameState.getGameBoardController().makeMove(direction);
        if (move instanceof MoveResult.Valid ){
            if (move instanceof MoveResult.Valid.Alive c) {
                gameState.increaseNumLives(c.collectedExtraLives.size());
                //for (int i = 0; i < ((MoveResult.Valid.Alive) move).collectedGems.size(); i++){
                //    gameState.getGameBoard().getEntityCell(((MoveResult.Valid.Alive) move).collectedGems.get(i)).setEntity(null);
                //}
                //for (int i = 0; i < ((MoveResult.Valid.Alive) move).collectedExtraLives.size(); i++){
                 //   gameState.getGameBoard().getEntityCell(((MoveResult.Valid.Alive) move).collectedExtraLives.get(i)).setEntity(null);
                //}
                //gameState.getGameBoard().getEntityCell(((MoveResult.Valid.Alive) move).newPosition).setEntity(gameState.getGameBoard().getPlayer());
                gameState.getMoveStack().push(move);
            }
            gameState.incrementNumMoves();
            if (move instanceof MoveResult.Valid.Dead){
                gameState.incrementNumDeaths();
                gameState.decrementNumLives();
            }
        }
        return move;
    }

    /**
     * Processes an Undo action performed by the player.
     *
     * @return {@code false} if there are no steps to undo.
     */
    public boolean processUndo() {
        // TODO(DONE)
        if (gameState.getNumMoves() == 0){
            return false;
        }else{
            MoveResult m = gameState.getMoveStack().pop();
            gameState.getGameBoardController().undoMove(m);
            gameState.decreaseNumLives(((MoveResult.Valid.Alive) m).collectedExtraLives.size());
            return true;
        }
    }
}
