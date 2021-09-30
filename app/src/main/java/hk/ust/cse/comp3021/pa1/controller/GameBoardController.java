package hk.ust.cse.comp3021.pa1.controller;

import hk.ust.cse.comp3021.pa1.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Controller for {@link GameBoard}.
 *
 * <p>
 * This class is responsible for providing high-level operations to mutate a {@link GameBoard}. This should be the only
 * class which mutates the game board; Other classes should use this class to mutate the game board.
 * </p>
 */
public class GameBoardController {

    @NotNull
    private final GameBoard gameBoard;

    /**
     * Creates an instance.
     *
     * @param gameBoard The instance of {@link GameBoard} to control.
     */
    public GameBoardController(@NotNull final GameBoard gameBoard) {
        // TODO(DONE)
        this.gameBoard = gameBoard;
    }

    /**
     * Moves the player in the given direction.
     *
     * <p>
     * You should ensure that the game board is only mutated if the move is valid and results in the player still being
     * alive. If the player dies after moving or the move is invalid, the game board should remain in the same state as
     * before this method was called.
     * </p>
     *
     * @param direction Direction to move the player in.
     * @return An instance of {@link MoveResult} representing the result of this action.
     */
    @NotNull
    public MoveResult makeMove(@NotNull final Direction direction) {
        // TODO(DONE)
        int delta = 0;
        assert gameBoard.getPlayer().getOwner() != null;
        var playerPosition = gameBoard.getPlayer().getOwner().getPosition();
        var playerRow = playerPosition.row();
        var playerCol = playerPosition.col();
        int offsetR = 0;
        int offsetC = 0;
        if ((offsetR = direction.getOffset().dRow()) != 0){
            playerRow += offsetR;
            delta ++;
            while(playerRow >= 0 && playerRow < gameBoard.getNumRows()){
                if (gameBoard.getCell(playerRow,playerCol) instanceof Wall){
                    if (delta == 1){
                        return new MoveResult.Invalid(playerPosition);
                    }else{
                        gameBoard.getPlayer().setOwner(gameBoard.getEntityCell(playerRow + delta*offsetR, playerCol));
                        return new MoveResult.Valid.Alive(playerPosition.offsetBy(delta * offsetR, 0), playerPosition);
                    }
                } else if (gameBoard.getCell(playerRow,playerCol) instanceof EntityCell c){
                    if(c.getEntity() instanceof Mine){
                        return new MoveResult.Valid.Dead(playerPosition, playerPosition.offsetBy(delta * offsetR, 0));
                    }else if (c instanceof StopCell){
                        gameBoard.getPlayer().setOwner(gameBoard.getEntityCell(playerRow + delta*offsetR, playerCol));
                        return new MoveResult.Valid.Alive(playerPosition.offsetBy(delta * offsetR, 0), playerPosition);
                    }
                }
                delta++;
                playerRow += offsetR;
            }
        }else if ((offsetC = direction.getOffset().dCol()) != 0){
            playerCol += offsetC;
            delta ++;
            while(playerCol >= 0 && playerCol < gameBoard.getNumCols()){
                if (gameBoard.getCell(playerRow,playerCol) instanceof Wall){
                    if (delta == 1){
                        return new MoveResult.Invalid(playerPosition);
                    }else{
                        gameBoard.getPlayer().setOwner(gameBoard.getEntityCell(playerRow, playerCol + delta * offsetC));
                        return new MoveResult.Valid.Alive(playerPosition.offsetBy(0, delta * offsetC), playerPosition);
                    }
                } else if (gameBoard.getCell(playerRow,playerCol) instanceof EntityCell c){
                    if(c.getEntity() instanceof Mine){
                        return new MoveResult.Valid.Dead(playerPosition, playerPosition.offsetBy(0, delta * offsetC));
                    }else if (c instanceof StopCell){
                        gameBoard.getPlayer().setOwner(gameBoard.getEntityCell(playerRow, playerCol + delta * offsetC));
                        return new MoveResult.Valid.Alive(playerPosition.offsetBy(0, delta * offsetC), playerPosition);
                    }
                }
                delta++;
                playerCol += offsetC;
            }
        }
        return new MoveResult.Invalid(playerPosition);
    }

    /**
     * Undoes a move by reverting all changes performed by the specified move.
     *
     * <p>
     * Hint: Undoing a move is effectively the same as reversing everything you have done to make a move.
     * </p>
     *
     * @param prevMove The {@link MoveResult} object to revert.
     */
    public void undoMove(@NotNull final MoveResult prevMove) {
        // TODO(DONE)
        if (prevMove instanceof MoveResult.Valid c){
            Objects.requireNonNull(((EntityCell) gameBoard.getCell(((MoveResult.Valid) prevMove).newPosition)).getEntity()).setOwner(gameBoard.getEntityCell(c.origPosition));
        }
    }
}
